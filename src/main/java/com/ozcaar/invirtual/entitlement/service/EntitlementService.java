package com.ozcaar.invirtual.entitlement.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.ozcaar.invirtual.common.exception.global.ConflictException;
import com.ozcaar.invirtual.common.exception.global.ForbiddenException;
import com.ozcaar.invirtual.entitlement.model.EntitlementModel;
import com.ozcaar.invirtual.entitlement.repository.EntitlementRepository;
import com.ozcaar.invirtual.invitation.model.InvitationModel;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EntitlementService {

    private final EntitlementRepository entitlementRepository;

    public void consume(EntitlementModel ent, InvitationModel invitation, LocalDateTime now) {
        if (ent.getConsumedAt() != null)
            throw new ConflictException("Entitlement ya consumido");
        if (ent.getExpiresAt() != null && !ent.getExpiresAt().isAfter(now))
            throw new ForbiddenException("Entitlement expirado");

        ent.setConsumedAt(now);
        ent.setInvitation(invitation);
        entitlementRepository.save(ent);
    }

}
