package com.ozcaar.invirtual.billing.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ozcaar.invirtual.billing.dto.create.CheckoutSessionCreate;
import com.ozcaar.invirtual.billing.enums.PlanKeyEnum;
import com.ozcaar.invirtual.common.exception.global.NotFoundException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BillingService {

    // Environment variables
    @Value("${app.frontend-url}")
    private String frontendUrl;

    private final StripeService stripeService;

    public String createCheckoutSession(String supabaseId, CheckoutSessionCreate req)
            throws Exception {

        PlanKeyEnum plan;
        try {
            plan = PlanKeyEnum.valueOf(req.getPlan());
        } catch (IllegalArgumentException e) {
            throw new NotFoundException("Plan no encontrado.");
        }

        String priceId = stripeService.priceIdFor(plan);

        String successUrl = (frontendUrl + "/billing/success?session_id={CHECKOUT_SESSION_ID}");
        String cancelUrl = (frontendUrl + "/billing/cancel");

        SessionCreateParams params = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl(successUrl)
                .setCancelUrl(cancelUrl)
                .addLineItem(
                        SessionCreateParams.LineItem.builder()
                                .setQuantity(1L)
                                .setPrice(priceId)
                                .build())
                .putMetadata("app_user_id", supabaseId)
                .build();

        Session session = Session.create(params);

        return session.getUrl();
    }
}
