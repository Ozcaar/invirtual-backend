package com.ozcaar.invirtual.template.service;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ozcaar.invirtual.auth.service.AuthService;
import com.ozcaar.invirtual.common.exception.global.GlobalBadRequestException;
import com.ozcaar.invirtual.common.exception.global.NotFoundException;
import com.ozcaar.invirtual.draft.repository.DraftRepository;
import com.ozcaar.invirtual.entitlement.repository.EntitlementRepository;
import com.ozcaar.invirtual.invitation.model.InvitationTypeModel;
import com.ozcaar.invirtual.invitation.repository.InvitationTypeRepository;
import com.ozcaar.invirtual.plan.model.PlanModel;
import com.ozcaar.invirtual.plan.repository.PlanRepository;
import com.ozcaar.invirtual.template.dto.read.TemplateReadDTO;
import com.ozcaar.invirtual.template.model.TemplateModel;
import com.ozcaar.invirtual.template.repository.TemplateRepository;
import com.ozcaar.invirtual.user.model.UserModel;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TemplateService {

    private final TemplateRepository templateRepository;
    private final PlanRepository planRepository;
    private final InvitationTypeRepository invitationTypeRepository;
    private final DraftRepository draftRepository;
    private final EntitlementRepository entitlementRepository;
    private final AuthService authService;

    public List<TemplateReadDTO> list(String planCode, Integer invitationTypeId) {
        if (planCode == null || planCode.isBlank() || planCode.trim().length() > 40) {
            throw new GlobalBadRequestException("Indica un código de plan válido.");
        }
        UserModel user = authService.getUserFromToken()
                .orElseThrow(() -> new NotFoundException("Ha ocurrido un error con el usuario"));
        PlanModel plan = planRepository.findByCode(planCode.trim().toUpperCase(Locale.ROOT))
                .orElseThrow(() -> new NotFoundException("Plan no encontrado."));
        if (!plan.isActive()) {
            boolean keptPlan = draftRepository.findByUser_UserId(user.getUserId())
                    .map(draft -> draft.getPlan().getId().equals(plan.getId())).orElse(false);
            boolean acquiredPlan = entitlementRepository.findByUser_SupabaseIdAndConsumedAtIsNull(user.getSupabaseId())
                    .map(ent -> ent.getPlan() != null && ent.getPlan().getId().equals(plan.getId())).orElse(false);
            if (!keptPlan && !acquiredPlan) {
                throw new GlobalBadRequestException("El plan seleccionado está desactivado.");
            }
        }
        if (invitationTypeId != null) {
            if (invitationTypeId <= 0) throw new GlobalBadRequestException("El tipo de invitación debe ser un entero positivo.");
            InvitationTypeModel type = invitationTypeRepository.findById(invitationTypeId)
                    .orElseThrow(() -> new NotFoundException("Tipo de invitación no encontrado."));
            if (!type.isActive()) throw new GlobalBadRequestException("El tipo de invitación seleccionado está desactivado.");
        }
        return templateRepository.findAvailable(plan.getId(), invitationTypeId).stream()
                .map(template -> new TemplateReadDTO(template.getId(), template.getCode(), template.getRevision(),
                        template.getName(), template.getDescription(), template.getThumbnailUrl(),
                        template.getPlans().stream().map(PlanModel::getCode).sorted().toList(),
                        template.getInvitationTypes().stream().map(InvitationTypeModel::getInvitationTypeId).sorted().toList(),
                        template.getSchemaVersion(), template.getInitialConfiguration()))
                .toList();
    }

    public TemplateModel resolveSelection(UUID templateId, PlanModel plan, InvitationTypeModel type,
            TemplateModel current) {
        if (templateId == null) return null;
        TemplateModel template = templateRepository.findById(templateId)
                .orElseThrow(() -> new NotFoundException("Plantilla no encontrada."));
        boolean keptSelection = current != null && current.getId().equals(templateId);
        if (!template.isActive() && !keptSelection) {
            throw new GlobalBadRequestException("La plantilla está retirada de nuevas selecciones.");
        }
        boolean compatiblePlan = template.getPlans().stream().anyMatch(p -> p.getId().equals(plan.getId()));
        boolean compatibleType = type == null || template.getInvitationTypes().stream()
                .anyMatch(it -> it.getInvitationTypeId().equals(type.getInvitationTypeId()));
        if (!compatiblePlan || template.getInvitationTypes().isEmpty() || !compatibleType) {
            throw new GlobalBadRequestException("La plantilla no es compatible con el plan o tipo seleccionados. Elige otra o envía templateId: null.");
        }
        return template;
    }
}
