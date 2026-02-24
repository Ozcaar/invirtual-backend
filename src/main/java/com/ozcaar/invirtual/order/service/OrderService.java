package com.ozcaar.invirtual.order.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ozcaar.invirtual.entitlement.service.EntitlementService;
import com.ozcaar.invirtual.order.model.OrderModel;
import com.stripe.model.checkout.Session;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final EntitlementService entitlementService;

    public boolean existsByStripeSessionId(String sessionId) {
        return orderRepository.existsByStripeSessionId(sessionId);
    }

    @Transactional
    public void createOrderFromStripe(Session session,
            String stripePriceId) {

        String sessionId = session.getId();

        // 0) Idempotency: dont repeat already processed session
        if (orderRepository.existsByStripeSessionId(sessionId)) {
            return;
        }

        // 1) Validate payment status
        String paymentStatus = session.getPaymentStatus();
        if (!"paid".equalsIgnoreCase(paymentStatus)) {
            return;
        }

        // 2) Metadata: get supabaseId and planCode
        String supabaseId = session.getMetadata() != null ? session.getMetadata().get("supabaseId") : null;
        if (supabaseId == null || supabaseId.isBlank()) {
            throw new RuntimeException("Webhook sin supabaseId en metadata");
        }

        // 3) Create Order
        OrderModel order = new OrderModel();
        order.setStripeSessionId(session.getId());
        order.setStripePaymentIntentId(session.getPaymentIntent());
        order.setStripePriceId(stripePriceId);
        order.setSupabaseId(supabaseId);
        order.setAmountTotal(session.getAmountTotal());
        order.setCurrency(session.getCurrency());
        order.setStatus(session.getPaymentStatus());

        orderRepository.save(order);

        // 4) Create Entitlement (NOT consumed here, just created)
        entitlementService.grantFromOrder(order);
    }
}