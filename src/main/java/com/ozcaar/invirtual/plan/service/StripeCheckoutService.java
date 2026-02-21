package com.ozcaar.invirtual.plan.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import com.ozcaar.invirtual.billing.config.StripeCheckoutProperties;
import com.ozcaar.invirtual.billing.enums.BillingTypeEnum;
import com.ozcaar.invirtual.billing.enums.PlanKeyEnum;
import com.ozcaar.invirtual.common.exception.global.BadGatewayException;
import com.ozcaar.invirtual.common.exception.global.GlobalBadRequestException;
import com.ozcaar.invirtual.plan.enums.EnvironmentEnum;
import com.ozcaar.invirtual.plan.service.PriceResolverService.ResolvedCheckout;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class StripeCheckoutService {

    private final StripeCheckoutProperties props;
    private final PriceResolverService resolver;

    public Session createCheckoutSession(
            PlanKeyEnum planCode,
            String currency,
            String promoCode,
            String supabaseId) {
        requirePlan(planCode);

        String finalCurrency = normalizeCurrency(currency);
        EnvironmentEnum env = props.getEnvironment();

        // 1) Resolve price (with overrides) and discount (by promo code or auto-applied)
        ResolvedCheckout resolved = resolver.resolve(
                planCode.name(),
                env,
                finalCurrency,
                BillingTypeEnum.ONE_TIME,
                promoCode);

        try {
            // 2) Build Stripe Checkout Session params
            var builder = baseCheckoutBuilder(resolved.stripePriceIdToCharge())
                    .putAllMetadata(buildMetadata(planCode, env, finalCurrency, resolved, supabaseId));

            // 3) Apply discount if present
            applyDiscountIfPresent(builder, resolved);

            return Session.create(builder.build());
        } catch (StripeException e) {
            log.error("Unexpected error creating Stripe checkout session: {}", e.getMessage(), e);
            throw new BadGatewayException("Error creando la sesión de pago en Stripe.");
        }
    }

    private void requirePlan(PlanKeyEnum planCode) {
        if (planCode == null)
            throw new GlobalBadRequestException("planCode es requerido.");
    }

    private String normalizeCurrency(String currency) {
        var cur = (currency == null) ? "" : currency.trim();
        return cur.isEmpty() ? props.getDefaultCurrency() : cur.toUpperCase();
    }

    private SessionCreateParams.Builder baseCheckoutBuilder(String stripePriceId) {
        return SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl(props.getSuccessUrl())
                .setCancelUrl(props.getCancelUrl())
                .setAllowPromotionCodes(true)
                .addLineItem(SessionCreateParams.LineItem.builder()
                        .setPrice(stripePriceId)
                        .setQuantity(1L)
                        .build());
    }

    private Map<String, String> buildMetadata(
            PlanKeyEnum planCode,
            EnvironmentEnum env,
            String currency,
            ResolvedCheckout resolved,
            String supabaseId) {
        Map<String, String> md = new HashMap<>();
        md.put("planCode", planCode.name());
        md.put("environment", env.name());
        md.put("currency", currency);
        md.put("basePlanPriceId", resolved.basePrice().getId().toString());
        md.put("supabaseId", supabaseId);
        return md;
    }

    private void applyDiscountIfPresent(SessionCreateParams.Builder builder, ResolvedCheckout resolved) {
        resolved.discountToApply().ifPresent(d -> {
            var promo = safe(d.getStripePromotionCodeId());
            var coupon = safe(d.getStripeCouponId());

            if (!promo.isEmpty()) {
                builder.addDiscount(SessionCreateParams.Discount.builder()
                        .setPromotionCode(promo)
                        .build());
                return;
            }

            if (!coupon.isEmpty()) {
                builder.addDiscount(SessionCreateParams.Discount.builder()
                        .setCoupon(coupon)
                        .build());
            }
        });
    }

    private String safe(String s) {
        return s == null ? "" : s.trim();
    }
}