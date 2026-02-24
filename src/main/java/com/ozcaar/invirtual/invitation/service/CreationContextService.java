package com.ozcaar.invirtual.invitation.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ozcaar.invirtual.common.exception.global.ForbiddenException;
import com.ozcaar.invirtual.common.exception.global.NotFoundException;
import com.ozcaar.invirtual.common.repository.UserInvitationRepository;
import com.ozcaar.invirtual.entitlement.model.EntitlementModel;
import com.ozcaar.invirtual.entitlement.repository.EntitlementRepository;
import com.ozcaar.invirtual.invitation.dto.create.InvitationCreateDTO;
import com.ozcaar.invirtual.invitation.model.InvitationTypeModel;
import com.ozcaar.invirtual.invitation.policy.CreationContext;
import com.ozcaar.invirtual.invitation.policy.DemoInvitationPolicy;
import com.ozcaar.invirtual.invitation.policy.EntitlementInvitationPolicy;
import com.ozcaar.invirtual.invitation.repository.InvitationTypeRepository;
import com.ozcaar.invirtual.user.model.UserModel;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreationContextService {

    private final InvitationTypeRepository invitationTypeRepository;
    private final EntitlementRepository entitlementRepository;
    private final UserInvitationRepository userInvitationRepository;

    @Transactional
    public CreationContext resolve(UserModel user, InvitationCreateDTO dto) {

        LocalDateTime now = LocalDateTime.now();

        @SuppressWarnings("null")
        InvitationTypeModel type = invitationTypeRepository.findById(dto.getInvitationTypeId())
                .orElseThrow(() -> new NotFoundException("Tipo de invitación no encontrado"));

        // 1) Try paid: take available entitlement with lock (without filtering by type)
        Optional<EntitlementModel> entOpt = entitlementRepository
                .findAvailableForUpdate(user.getSupabaseId(), Pageable.unpaged())
                .stream()
                .findFirst();

        if (entOpt.isPresent()) {
            EntitlementModel ent = entOpt.get();
            return new CreationContext(type, new EntitlementInvitationPolicy(ent, now), ent);
        }

        // 2) Fallback demo
        if (userInvitationRepository.existsActiveDemoInvitation(user.getSupabaseId(), now)) {
            throw new ForbiddenException("No tienes invitaciones disponibles (y ya tienes una demo activa)");
        }

        return new CreationContext(type, new DemoInvitationPolicy(), null);
    }
}
