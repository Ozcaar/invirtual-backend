package com.ozcaar.invirtual.plan.service;

import org.springframework.stereotype.Service;

import com.ozcaar.invirtual.billing.enums.BillingTypeEnum;
import com.ozcaar.invirtual.common.exception.global.NotFoundException;
import com.ozcaar.invirtual.plan.enums.EnvironmentEnum;
import com.ozcaar.invirtual.plan.model.PlanDiscountModel;
import com.ozcaar.invirtual.plan.model.PlanPriceModel;
import com.ozcaar.invirtual.plan.model.PlanPriceOverrideModel;
import com.ozcaar.invirtual.plan.repository.PlanDiscountRepository;
import com.ozcaar.invirtual.plan.repository.PlanPriceOverrideRepository;
import com.ozcaar.invirtual.plan.repository.PlanPriceRepository;

import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PriceResolverService {

    public record ResolvedCheckout(
            PlanPriceModel basePrice,
            String stripePriceIdToCharge,
            Optional<PlanDiscountModel> discountToApply) {
    }

    private final PlanPriceRepository planPriceRepository;
    private final PlanPriceOverrideRepository overrideRepository;
    private final PlanDiscountRepository discountRepository;

    public ResolvedCheckout resolve(String planCode, EnvironmentEnum env, String currency, BillingTypeEnum billingType,
            String promoCode) {

        PlanPriceModel base = planPriceRepository
                .findFirstByPlan_CodeAndPlan_ActiveTrueAndActiveTrueAndEnvironmentAndCurrencyAndBillingType(
                        planCode, env, currency, billingType)
                .orElseThrow(() -> new NotFoundException(
                        "No existe un precio activo para el plan " + planCode + " en " + currency + " (" + env + ")."));

        // 1) Override by date/priority
        String priceIdToCharge = resolveOverridePriceIdOrBase(base);

        // 2) Promo Code (if provided) or auto-applied (if not provided)
        Optional<PlanDiscountModel> discount = resolveDiscount(base.getId(), promoCode);

        return new ResolvedCheckout(base, priceIdToCharge, discount);
    }

    private String resolveOverridePriceIdOrBase(PlanPriceModel base) {
        LocalDateTime now = LocalDateTime.now();

        return overrideRepository.findAllByPlanPrice_IdAndActiveTrueOrderByPriorityDescIdDesc(base.getId())
                .stream()
                .filter(o -> isWithinWindow(now, o.getStartsAt(), o.getEndsAt()))
                .findFirst()
                .map(PlanPriceOverrideModel::getStripePriceIdOverride)
                .orElse(base.getStripePriceId());
    }

    private Optional<PlanDiscountModel> resolveDiscount(java.util.UUID planPriceId, String promoCode) {
        LocalDateTime now = LocalDateTime.now();

        if (promoCode != null && !promoCode.isBlank()) {
            return discountRepository
                    .findFirstByPlanPrice_IdAndActiveTrueAndPublicCodeIgnoreCase(planPriceId, promoCode.trim())
                    .filter(d -> isWithinWindow(now, d.getStartsAt(), d.getEndsAt()));
        }

        // Auto-apply discounts (without code): choose the one with the highest priority
        // that is active in the window
        return discountRepository
                .findAllByPlanPrice_IdAndActiveTrueAndPublicCodeIsNullOrderByPriorityDescIdDesc(planPriceId)
                .stream()
                .filter(d -> isWithinWindow(now, d.getStartsAt(), d.getEndsAt()))
                .findFirst();
    }

    private boolean isWithinWindow(LocalDateTime now, LocalDateTime startsAt, LocalDateTime endsAt) {
        if (startsAt != null && now.isBefore(startsAt))
            return false;
        if (endsAt != null && now.isAfter(endsAt))
            return false;
        return true;
    }
}