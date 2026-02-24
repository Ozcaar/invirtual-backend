package com.ozcaar.invirtual.invitation.service;

import java.text.Normalizer;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ozcaar.invirtual.auth.service.AuthService;
import com.ozcaar.invirtual.common.exception.global.AlreadyExistsException;
import com.ozcaar.invirtual.common.exception.global.NotFoundException;
import com.ozcaar.invirtual.common.model.manytomany.UserInvitationModel;
import com.ozcaar.invirtual.common.repository.UserInvitationRepository;
import com.ozcaar.invirtual.entitlement.repository.EntitlementRepository;
import com.ozcaar.invirtual.entitlement.service.EntitlementService;
import com.ozcaar.invirtual.invitation.dto.create.InvitationCreateDTO;
import com.ozcaar.invirtual.invitation.dto.read.InvitationReadDTO;
import com.ozcaar.invirtual.invitation.dto.update.InvitationUpdateDTO;
import com.ozcaar.invirtual.invitation.mapper.InvitationMapper;
import com.ozcaar.invirtual.invitation.model.InvitationModel;
import com.ozcaar.invirtual.invitation.policy.CreationContext;
import com.ozcaar.invirtual.invitation.repository.InvitationRepository;
import com.ozcaar.invirtual.user.model.UserModel;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InvitationService {

    private final AuthService authService;
    private final InvitationRepository invitationRepository;
    private final UserInvitationRepository userInvitationRepository;
    private final InvitationMapper invitationMapper;
    private final EntitlementRepository entitlementRepository;
    private final CreationContextService creationContextService;
    private final EntitlementService entitlementService;

    public static String toSlug(String input) {
        if (input == null) {
            return "";
        }

        // Convert to Lower case
        String slug = input.toLowerCase(Locale.ROOT);

        // Remove accents and diacritics
        slug = Normalizer.normalize(slug, Normalizer.Form.NFD);
        slug = slug.replaceAll("\\p{M}", ""); // Remove accents

        // Replace non-alphanumeric characters with hyphens
        slug = slug.replaceAll("[^a-z0-9]+", "-");

        // Remove hyphens at the beginning or end
        slug = slug.replaceAll("^-+|-+$", "");

        return slug;
    }

    // CRUDs

    // CREATE
    @SuppressWarnings("null")
    @Transactional
    public InvitationReadDTO createInvitation(InvitationCreateDTO dto) {

        final LocalDateTime now = LocalDateTime.now();

        UserModel user = authService.getUserFromToken()
                .orElseThrow(() -> new NotFoundException("Ha ocurrido un error con el usuario"));

        // 1) Basic validations
        if (userInvitationRepository.findByUserSupabaseIdAndInvitationName(user.getSupabaseId(), dto.getName())
                .isPresent()) {
            throw new AlreadyExistsException("El usuario ya tiene una invitación con ese nombre");
        }

        String slug = toSlug(dto.getSlugUrl());
        if (userInvitationRepository.existsByUserSupabaseIdAndInvitationSlugUrl(user.getSupabaseId(), slug)) {
            throw new AlreadyExistsException("Ya existe una invitación con ese nombre/slug");
        }

        // 2) Demo vs paid policies
        CreationContext ctx = creationContextService.resolve(user, dto);

        // 3) Create invitation
        InvitationModel invitation = invitationMapper.toEntity(dto);

        if (ctx.entitlement() != null) {
            ctx.entitlement().setInvitation(invitation);
            entitlementRepository.save(ctx.entitlement());
        }

        // 4) Set base fields
        invitation.setInvitationTypeId(ctx.invitationType());
        invitation.setSlugUrl(slug);
        invitation.setCreationDate(now);
        invitation.setActive(true);

        // 5) Apply policy
        ctx.policy().apply(invitation);

        // 6) Save invitation
        invitationRepository.save(invitation);

        // 7) Link user and invitation
        UserInvitationModel userInvitation = new UserInvitationModel();
        userInvitation.setUser(user);
        userInvitation.setInvitation(invitation);
        userInvitation.setCreated_at(now);
        userInvitationRepository.save(userInvitation);

        // 8) Consume entitlement
        if (ctx.entitlement() != null) {
            entitlementService.consume(ctx.entitlement(), invitation, now);
        }

        return invitationMapper.toDTO(invitation);
    }

    // READ
    public InvitationReadDTO getInvitation(UUID invitationUUID) {
        InvitationModel invitation = invitationRepository.findByUUID(invitationUUID)
                .orElseThrow(
                        () -> new NotFoundException("No se encontró la invitación con el UUID: " + invitationUUID));

        return invitationMapper.toDTO(invitation);
    }

    public List<InvitationReadDTO> getAllInvitations() {
        List<InvitationModel> invitations = (List<InvitationModel>) invitationRepository.findAll();
        return invitationMapper.toDTOList(invitations);
    }

    // UPDATE
    public InvitationReadDTO updateInvitation(UUID invitationUUID, InvitationUpdateDTO dto) {
        InvitationModel invitation = invitationRepository.findByUUID(invitationUUID)
                .orElseThrow(
                        () -> new NotFoundException("No se encontró la invitación con el UUID: " + invitationUUID));

        if (dto.getName() != null)
            invitation.setName(dto.getName());
        if (dto.getMaxPeople() != null)
            invitation.setMaxPeople(dto.getMaxPeople());
        if (dto.getSlugUrl() != null)
            invitation.setSlugUrl(dto.getSlugUrl());

        @SuppressWarnings("null")
        InvitationModel updatedInvitation = invitationRepository.save(invitation);
        return invitationMapper.toDTO(updatedInvitation);
    }

    // DELETE
    public void deleteInvitation(UUID invitationUUID) {

        // Soft delete
        InvitationModel invitation = invitationRepository.findByUUID(invitationUUID)
                .orElseThrow(() -> new NotFoundException("No se encontró la invitación con el ID: " + invitationUUID));

        invitation.setActive(false);
        invitationRepository.save(invitation);
    }

}
