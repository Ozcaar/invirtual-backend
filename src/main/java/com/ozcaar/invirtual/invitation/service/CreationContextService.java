package com.ozcaar.invirtual.invitation.service;

import java.time.LocalDateTime;
import java.util.Optional;

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

        InvitationTypeModel type = invitationTypeRepository.findById(dto.getInvitation_type_id())
                .orElseThrow(() -> new NotFoundException("Tipo de invitación no encontrado"));

        // 1) Intentar pagada: tomar entitlement disponible con lock
        Optional<EntitlementModel> entOpt = entitlementRepository.findFirstAvailableForUpdate(
                user.getSupabaseId(),
                type,
                now);

        if (entOpt.isPresent()) {
            EntitlementModel ent = entOpt.get();
            return new CreationContext(type, new EntitlementInvitationPolicy(ent), ent);
        }

        // 2) Fallback a demo (reglas demo)
        // regla demo: solo 1 demo activa por usuario
        if (userInvitationRepository.existsActiveDemoInvitation(user.getSupabaseId(), now)) {
            // aquí puedes cambiar el mensaje a algo más claro
            throw new ForbiddenException("No tienes invitaciones disponibles (y ya tienes una demo activa)");
        }

        // (opcional) regla extra: cooldown de demos (ej. 30 días)
        // if (userInvitationRepository.hasUsedDemoInLastDays(user.getUser_id(),
        // now.minusDays(30))) {
        // throw new ForbiddenException("Tu demo ya fue usada recientemente. Intenta más
        // tarde o compra una invitación.");
        // }

        return new CreationContext(type, new DemoInvitationPolicy(), null);
    }
}
