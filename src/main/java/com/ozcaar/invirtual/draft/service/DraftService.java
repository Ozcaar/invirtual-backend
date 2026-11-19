package com.ozcaar.invirtual.draft.service;

import java.text.Normalizer;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.JsonNode;
import com.ozcaar.invirtual.auth.service.AuthService;
import com.ozcaar.invirtual.common.exception.global.ConflictException;
import com.ozcaar.invirtual.common.exception.global.GlobalBadRequestException;
import com.ozcaar.invirtual.common.exception.global.NotFoundException;
import com.ozcaar.invirtual.draft.dto.create.DraftCreateDTO;
import com.ozcaar.invirtual.draft.dto.read.DraftReadDTO;
import com.ozcaar.invirtual.draft.dto.read.DraftReadDTO.EntitlementPlanMatch;
import com.ozcaar.invirtual.draft.dto.read.DraftReadDTO.SelectedPlan;
import com.ozcaar.invirtual.draft.dto.read.DraftReadDTO.SelectedTemplate;
import com.ozcaar.invirtual.draft.dto.update.DraftUpdateDTO;
import com.ozcaar.invirtual.draft.model.DraftModel;
import com.ozcaar.invirtual.draft.repository.DraftRepository;
import com.ozcaar.invirtual.entitlement.model.EntitlementModel;
import com.ozcaar.invirtual.entitlement.repository.EntitlementRepository;
import com.ozcaar.invirtual.invitation.model.InvitationTypeModel;
import com.ozcaar.invirtual.invitation.repository.InvitationTypeRepository;
import com.ozcaar.invirtual.plan.model.PlanModel;
import com.ozcaar.invirtual.plan.repository.PlanRepository;
import com.ozcaar.invirtual.template.model.TemplateModel;
import com.ozcaar.invirtual.template.service.TemplateService;
import com.ozcaar.invirtual.user.model.UserModel;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DraftService {

    private final DraftRepository draftRepository;
    private final PlanRepository planRepository;
    private final InvitationTypeRepository invitationTypeRepository;
    private final EntitlementRepository entitlementRepository;
    private final AuthService authService;
    private final TemplateService templateService;

    @Transactional
    public DraftReadDTO create(DraftCreateDTO dto) {
        UserModel user = currentUser();
        if (draftRepository.findByUser_UserId(user.getUserId()).isPresent()) {
            throw new ConflictException("Ya tienes un borrador. Recupéralo o descártalo antes de crear otro.");
        }

        validateConfiguration(dto.schemaVersion(), dto.configuration());
        Optional<EntitlementModel> entitlement = availableEntitlement(user);
        PlanModel plan = resolvePlan(dto.planCode(), null, entitlement);
        InvitationTypeModel type = resolveType(dto.invitationTypeId(), null);
        TemplateModel template = templateService.resolveSelection(dto.templateId(), plan, type, null);
        String slug = normalizeSlug(dto.slugUrl());

        DraftModel draft = new DraftModel();
        draft.setUser(user);
        draft.setPlan(plan);
        draft.setInvitationType(type);
        draft.setTemplate(template);
        draft.setName(dto.name());
        draft.setSlugUrl(slug);
        draft.setSchemaVersion(dto.schemaVersion());
        draft.setConfiguration(dto.configuration().deepCopy());

        try {
            draftRepository.saveAndFlush(draft);
        } catch (DataIntegrityViolationException ex) {
            for (Throwable cause = ex; cause != null; cause = cause.getCause()) {
                if (cause instanceof ConstraintViolationException constraint
                        && "uq_draft_user".equals(constraint.getConstraintName())) {
                    throw new ConflictException("Ya tienes un borrador. Recupéralo antes de crear otro.");
                }
            }
            throw ex;
        }
        return toDto(draft, entitlement);
    }

    @Transactional(readOnly = true)
    public DraftReadDTO getCurrent() {
        UserModel user = currentUser();
        return toDto(requireDraft(user), availableEntitlement(user));
    }

    @Transactional
    public DraftReadDTO update(DraftUpdateDTO dto) {
        UserModel user = currentUser();
        DraftModel draft = requireDraft(user);
        if (!Objects.equals(draft.getId(), dto.id()) || !Objects.equals(draft.getVersion(), dto.version())) {
            throw new ConflictException("El borrador cambió. Recupéralo de nuevo antes de guardar.");
        }

        validateConfiguration(dto.schemaVersion(), dto.configuration());
        Optional<EntitlementModel> entitlement = availableEntitlement(user);
        PlanModel plan = resolvePlan(dto.planCode(), draft.getPlan(), entitlement);
        InvitationTypeModel type = resolveType(dto.invitationTypeId(), draft.getInvitationType());
        TemplateModel template = templateService.resolveSelection(dto.templateId(), plan, type, draft.getTemplate());
        String slug = normalizeSlug(dto.slugUrl());

        draft.setPlan(plan);
        draft.setInvitationType(type);
        draft.setTemplate(template);
        draft.setName(dto.name());
        draft.setSlugUrl(slug);
        draft.setSchemaVersion(dto.schemaVersion());
        draft.setConfiguration(dto.configuration().deepCopy());
        try {
            draftRepository.saveAndFlush(draft);
        } catch (OptimisticLockingFailureException ex) {
            throw new ConflictException("El borrador fue modificado o descartado. Recupéralo de nuevo.");
        }
        return toDto(draft, entitlement);
    }

    @Transactional
    public void delete(UUID draftId) {
        UserModel user = currentUser();
        Optional<DraftModel> current = draftRepository.findByUser_UserId(user.getUserId());
        if (current.isEmpty()) return;

        DraftModel draft = current.get();
        if (!Objects.equals(draft.getId(), draftId)) {
            throw new ConflictException("El borrador actual es distinto al que intentas descartar.");
        }
        try {
            draftRepository.delete(draft);
            draftRepository.flush();
        } catch (OptimisticLockingFailureException ex) {
            throw new ConflictException("El borrador cambió mientras se descartaba. Recupéralo de nuevo.");
        }
    }

    private UserModel currentUser() {
        return authService.getUserFromToken()
                .orElseThrow(() -> new NotFoundException("Ha ocurrido un error con el usuario"));
    }

    private DraftModel requireDraft(UserModel user) {
        return draftRepository.findByUser_UserId(user.getUserId())
                .orElseThrow(() -> new NotFoundException("No tienes un borrador guardado."));
    }

    private Optional<EntitlementModel> availableEntitlement(UserModel user) {
        return entitlementRepository.findByUser_SupabaseIdAndConsumedAtIsNull(user.getSupabaseId());
    }

    private PlanModel resolvePlan(String code, PlanModel current, Optional<EntitlementModel> entitlement) {
        PlanModel plan = planRepository.findByCode(code.trim().toUpperCase(Locale.ROOT))
                .orElseThrow(() -> new NotFoundException("Plan no encontrado."));
        boolean keptPlan = current != null && current.getId().equals(plan.getId());
        boolean acquiredPlan = entitlement.map(ent -> ent.getPlan() != null
                && ent.getPlan().getId().equals(plan.getId())).orElse(false);
        if (!plan.isActive() && !keptPlan && !acquiredPlan) {
            throw new GlobalBadRequestException("El plan seleccionado está desactivado.");
        }
        return plan;
    }

    private InvitationTypeModel resolveType(Integer id, InvitationTypeModel current) {
        if (id == null) return null;
        InvitationTypeModel type = invitationTypeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Tipo de invitación no encontrado."));
        boolean keptType = current != null && current.getInvitationTypeId().equals(id);
        if (!type.isActive() && !keptType) {
            throw new GlobalBadRequestException("El tipo de invitación seleccionado está desactivado.");
        }
        return type;
    }

    private void validateConfiguration(Integer schemaVersion, JsonNode configuration) {
        if (!Integer.valueOf(1).equals(schemaVersion)) {
            throw new GlobalBadRequestException("La versión de configuración admitida es 1.");
        }
        if (configuration == null || !configuration.isObject()) {
            throw new GlobalBadRequestException("La configuración debe ser un objeto JSON.");
        }
    }

    private String normalizeSlug(String value) {
        if (value == null) return null;
        String slug = Normalizer.normalize(value.toLowerCase(Locale.ROOT), Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .replaceAll("[^a-z0-9]+", "-")
                .replaceAll("^-+|-+$", "");
        if (slug.length() < 3 || slug.length() > 50) {
            throw new GlobalBadRequestException("El identificador del enlace debe tener entre 3 y 50 caracteres tras normalizarse.");
        }
        return slug;
    }

    private DraftReadDTO toDto(DraftModel draft, Optional<EntitlementModel> entitlement) {
        PlanModel plan = draft.getPlan();
        TemplateModel template = draft.getTemplate();
        EntitlementPlanMatch match = EntitlementPlanMatch.NO_ENTITLEMENT;
        if (entitlement.isPresent()) {
            PlanModel acquiredPlan = entitlement.get().getPlan();
            match = acquiredPlan == null ? EntitlementPlanMatch.UNKNOWN_PLAN
                    : acquiredPlan.getId().equals(plan.getId()) ? EntitlementPlanMatch.SAME_PLAN
                    : EntitlementPlanMatch.DIFFERENT_PLAN;
        }
        return new DraftReadDTO(draft.getId(), draft.getVersion(),
                new SelectedPlan(plan.getId(), plan.getCode(), plan.getName(), plan.isActive()),
                draft.getName(),
                draft.getInvitationType() == null ? null : draft.getInvitationType().getInvitationTypeId(),
                template == null ? null : new SelectedTemplate(template.getId(), template.getCode(),
                        template.getRevision(), template.getName(), template.isActive(), template.getSchemaVersion()),
                draft.getSlugUrl(), draft.getSchemaVersion(), draft.getConfiguration().deepCopy(),
                match, draft.getCreatedAt(), draft.getUpdatedAt());
    }
}
