package com.ozcaar.invirtual.order.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ozcaar.invirtual.order.model.OrderModel;

public interface OrderRepository extends JpaRepository<OrderModel, UUID> {

    boolean existsByStripeSessionId(String stripeSessionId);

    Optional<OrderModel> findByStripeSessionId(String stripeSessionId);
}
