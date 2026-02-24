package com.ozcaar.invirtual.entitlement.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ozcaar.invirtual.common.exception.global.ConflictException;
import com.ozcaar.invirtual.common.exception.global.NotFoundException;
import com.ozcaar.invirtual.entitlement.model.EntitlementModel;
import com.ozcaar.invirtual.entitlement.repository.EntitlementRepository;
import com.ozcaar.invirtual.invitation.model.InvitationModel;
import com.ozcaar.invirtual.order.model.OrderModel;
import com.ozcaar.invirtual.user.model.UserModel;
import com.ozcaar.invirtual.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EntitlementService {

    private final EntitlementRepository entitlementRepository;
    private final UserRepository userRepository;
    private final PlanEntitlementResolver planEntitlementResolver;

    public record EntitlementSnapshot(int maxPeople, int durationDays) {
    }

    public void consume(EntitlementModel ent, InvitationModel invitation, LocalDateTime now) {

        if (ent.getConsumedAt() != null)
            throw new ConflictException("Entitlement ya consumido");

        ent.setConsumedAt(now);
        ent.setInvitation(invitation);
        entitlementRepository.save(ent);
    }

    @Transactional
    public EntitlementModel grantFromOrder(OrderModel order) {

        // Idempotency: if entitlement already exists for this order, return (do not create a new one)
        var existing = entitlementRepository.findByOrder_Id(order.getId());
        if (existing.isPresent())
            return existing.get();

        // 1) Get user
        UserModel user = userRepository.findBySupabaseId(order.getSupabaseId())
                .orElseThrow(() -> new NotFoundException("No se encontró usuario: " + order.getSupabaseId()));

        // TODO: resolve by what was actually charged, not by what was attempted to be
        // charged (in case the payment fails)
        // 2) Resolve plan snapshot (maxPeople / durationDays) by priceId in Order
        var snap = planEntitlementResolver.resolveByStripePriceId(order.getStripePriceId());

        // 3) Create Entitlement
        EntitlementModel ent = new EntitlementModel();
        ent.setUser(user);
        ent.setOrder(order);
        ent.setMaxPeople(snap.maxPeople());
        ent.setDurationDays(snap.durationDays());
        ent.setConsumedAt(null);
        ent.setInvitation(null);

        // 4) Save and return
        return entitlementRepository.save(ent);
    }

}
