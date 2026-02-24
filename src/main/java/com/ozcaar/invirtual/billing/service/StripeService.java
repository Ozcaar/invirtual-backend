package com.ozcaar.invirtual.billing.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ozcaar.invirtual.common.exception.global.GlobalBadRequestException;
import com.ozcaar.invirtual.order.service.OrderService;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class StripeService {

    @Value("${stripe.webhook-secret}")
    private String endpointSecret;

    private final OrderService orderService;

    // Validate payment and activate plan for user
    public void handleStripeWebhook(String payload, String sigHeader) {

        final Event event;
        try {
            event = Webhook.constructEvent(payload, sigHeader, endpointSecret);
        } catch (SignatureVerificationException e) {
            log.warn("Stripe signature verification failed: {}", e.getMessage());
            throw new GlobalBadRequestException("Firma de Stripe inválida.");
        } catch (Exception e) {
            log.warn("Stripe webhook error: {}", e.getMessage(), e);
            throw new GlobalBadRequestException("Webhook de Stripe inválido.");
        }

        if ("checkout.session.completed".equals(event.getType())) {
            Session session = (Session) event.getDataObjectDeserializer()
                    .getObject()
                    .orElse(null);

            if (session == null)
                throw new GlobalBadRequestException("Session nula.");

            if (!"paid".equals(session.getPaymentStatus())) {
                throw new GlobalBadRequestException("Pago no completado.");
            }

            if (session != null) {

                // String appUserId = session.getMetadata().get("app_user_id");

                String stripePriceId = session.getMetadata().get("stripePriceId");
                orderService.createOrderFromStripe(session, stripePriceId);
            }
        } else {
            // Ignore other event types for now
            // log.info("Evento de Stripe recibido pero no manejado: {}", event.getType());
        }
    }
}
