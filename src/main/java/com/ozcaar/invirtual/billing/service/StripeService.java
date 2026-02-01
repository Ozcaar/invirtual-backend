package com.ozcaar.invirtual.billing.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ozcaar.invirtual.billing.enums.PlanKeyEnum;
import com.ozcaar.invirtual.common.exception.global.GlobalBadRequestException;
import com.stripe.model.Event;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;

@Service
public class StripeService {

    @Value("${stripe.webhook-secret}")
    private String endpointSecret;

    public void handleStripeWebhook(String payload, String sigHeader) {

        Event event;
        try {
            event = Webhook.constructEvent(payload, sigHeader, endpointSecret);
        } catch (Exception e) {
            throw new GlobalBadRequestException("Firma de Stripe inválida.");
        }

        if ("checkout.session.completed".equals(event.getType())) {
            Session session = (Session) event.getDataObjectDeserializer()
                    .getObject()
                    .orElse(null);

            if (session != null) {
                String appUserId = session.getMetadata().get("app_user_id");

                createEntitlementForUser();
            }
        }
    }

    private void createEntitlementForUser() {
        // TODO: aquí activas plan/entitlement en tu DB
        // billingService.activateSubscription(appUserId, session.getCustomer(),
        // session.getSubscription());
    }

    // PRICE CATALOG

    private final Map<PlanKeyEnum, String> priceByPlan = Map.of(
            PlanKeyEnum.INVITATION_BASIC, "price_1SvXeqFQQRv1RAmFppnCShLF"
    // PlanKeyEnum.INVITATION_PREMIUM, "price_xxx_premium"
    );

    public String priceIdFor(PlanKeyEnum plan) {
        var priceId = priceByPlan.get(plan);
        if (priceId == null)
            throw new GlobalBadRequestException("Plan no encontrado.");
        return priceId;
    }

}
