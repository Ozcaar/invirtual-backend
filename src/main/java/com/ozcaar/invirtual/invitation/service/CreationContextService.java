package com.ozcaar.invirtual.invitation.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ozcaar.invirtual.auth.service.AuthService;
import com.ozcaar.invirtual.common.exception.global.ForbiddenException;
import com.ozcaar.invirtual.common.exception.global.GlobalBadRequestException;
import com.ozcaar.invirtual.common.exception.global.NotFoundException;
import com.ozcaar.invirtual.common.repository.UserInvitationRepository;
import com.ozcaar.invirtual.entitlement.model.EntitlementModel;
import com.ozcaar.invirtual.entitlement.repository.EntitlementRepository;
import com.ozcaar.invirtual.invitation.dto.create.InvitationCreateDTO;
import com.ozcaar.invirtual.invitation.dto.read.InvitationCreationContextReadDTO;
import com.ozcaar.invirtual.invitation.dto.read.InvitationCreationContextReadDTO.AvailableEntitlement;
import com.ozcaar.invirtual.invitation.dto.read.InvitationCreationContextReadDTO.Capabilities;
import com.ozcaar.invirtual.invitation.dto.read.InvitationCreationContextReadDTO.Demo;
import com.ozcaar.invirtual.invitation.dto.read.InvitationCreationContextReadDTO.PlanSnapshot;
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
    private final AuthService authService;

    @Transactional(readOnly = true)
    public InvitationCreationContextReadDTO getCreationContext() {
        UserModel user = authService.getUserFromToken()
                .orElseThrow(() -> new NotFoundException("Ha ocurrido un error con el usuario"));

        AvailableEntitlement availableEntitlement = entitlementRepository
                .findByUser_SupabaseIdAndConsumedAtIsNull(user.getSupabaseId())
                .map(ent -> new AvailableEntitlement(
                        ent.getId(),
                        ent.getPlan() == null ? null : new PlanSnapshot(
                                ent.getPlan().getId(), ent.getPlanCodeSnapshot(), ent.getPlanNameSnapshot()),
                        new Capabilities(ent.getMaxPeople(), ent.getDurationDays())))
                .orElse(null);

        boolean hasActiveDemo = userInvitationRepository.existsActiveDemoInvitation(
                user.getSupabaseId(), LocalDateTime.now());

        return new InvitationCreationContextReadDTO(availableEntitlement,
                new Demo(!hasActiveDemo, hasActiveDemo ? "ACTIVE_DEMO_EXISTS" : null,
                        new Capabilities(DemoInvitationPolicy.MAX_PEOPLE, DemoInvitationPolicy.DURATION_DAYS)));
    }

    @Transactional
    public CreationContext resolve(UserModel user, InvitationCreateDTO dto) {

        LocalDateTime now = LocalDateTime.now();

        @SuppressWarnings("null")
        InvitationTypeModel type = invitationTypeRepository.findById(dto.getInvitationTypeId())
                .orElseThrow(() -> new NotFoundException("Tipo de invitación no encontrado"));

        if (!type.isActive()) {
            throw new GlobalBadRequestException("El tipo de invitación está deshabilitado");
        }

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
