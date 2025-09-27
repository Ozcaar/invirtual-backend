package com.ozcaar.invirtual.invitation.service;

import java.text.Normalizer;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ozcaar.invirtual.common.exception.global.AlreadyExistsException;
import com.ozcaar.invirtual.common.exception.global.NotFoundException;
import com.ozcaar.invirtual.common.model.manytomany.UserInvitationModel;
import com.ozcaar.invirtual.common.repository.UserInvitationRepository;
import com.ozcaar.invirtual.invitation.dto.create.InvitationCreateDTO;
import com.ozcaar.invirtual.invitation.dto.read.InvitationReadDTO;
import com.ozcaar.invirtual.invitation.dto.update.InvitationUpdateDTO;
import com.ozcaar.invirtual.invitation.mapper.InvitationMapper;
import com.ozcaar.invirtual.invitation.model.InvitationModel;
import com.ozcaar.invirtual.invitation.model.InvitationTypeModel;
import com.ozcaar.invirtual.invitation.repository.InvitationRepository;
import com.ozcaar.invirtual.invitation.repository.InvitationTypeRepository;
import com.ozcaar.invirtual.user.model.UserModel;
import com.ozcaar.invirtual.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InvitationService {
    
    private final InvitationRepository invitationRepository;
    private final InvitationTypeRepository invitationTypeRepository;
    private final UserRepository userRepository;
    private final UserInvitationRepository userInvitationRepository;
    private final InvitationMapper invitationMapper;

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
    @Transactional
    public InvitationReadDTO createInvitation(InvitationCreateDTO dto) {

        // ? I think we can use extractUsername() from JwtUtil.java
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();

        UserModel user = userRepository.findByEmail(userEmail)
            .orElseThrow(() -> new NotFoundException("No se encontró el usuario con el email: " + userEmail));

        if (userInvitationRepository.findByUserIdAndInvitationName(user.getUser_id(), dto.getName()).isPresent()) {
            throw new AlreadyExistsException("El usuario ya tiene una invitación con ese nombre");
        }

        if (userInvitationRepository.existsDemoInvitation(user.getUser_id())) {
            throw new AlreadyExistsException("El usuario ya tiene una invitación demo");
        }

        InvitationModel invitation = invitationMapper.toEntity(dto);
        
        // Setup invitation
        // TODO: Validate if each config matches with order's table
        // Here we have to validate in each field if the user can
        // create an invitation with each configuration with the
        // order's table.

        InvitationTypeModel invitation_type = invitationTypeRepository.findById(dto.getInvitation_type_id())
            .orElseThrow(() -> new NotFoundException("Tipo de invitación no encontrado"));

        invitation.setInvitation_type_id(invitation_type);
        // invitation.setMax_people(dto.getMax_people()); // Must be indicated by order's table
        invitation.setMax_people(100); // Must be indicated by order's table
        invitation.setSlug_url(toSlug(dto.getName()));
        invitation.setCreation_date(LocalDateTime.now());
        invitation.setLimit_date(LocalDateTime.now().plusYears(1));
        
        // TODO: Verificate if is demo and valid configuration
        // Here we have to verify if the user has another demo active
        // and if it is cant'create the demo, else the demo duration
        // will be like 2 weeks until be desactivated (the other fields)
        // have to match with demo configuration.
        invitation.setIs_demo(true);

        invitation.setActive(true);
        
        invitationRepository.save(invitation);

        UserInvitationModel userInvitation = new UserInvitationModel();
        userInvitation.setUser(user);
        userInvitation.setInvitation(invitation);
        userInvitation.setCreated_at(LocalDateTime.now());
        userInvitationRepository.save(userInvitation);

        return invitationMapper.toDTO(invitation);
    }

    // READ
    public InvitationReadDTO getInvitation(UUID invitationUUID) {
        InvitationModel invitation = invitationRepository.findByUUID(invitationUUID)
            .orElseThrow(() -> new NotFoundException("No se encontró la invitación con el UUID: " + invitationUUID));

        return invitationMapper.toDTO(invitation);
    }

    public List<InvitationReadDTO> getAllInvitations() {
        List<InvitationModel> invitations = (List<InvitationModel>) invitationRepository.findAll();
        return invitationMapper.toDTOList(invitations);
    }

    // UPDATE
    public InvitationReadDTO updateInvitation(UUID invitationUUID, InvitationUpdateDTO dto) {
        InvitationModel invitation = invitationRepository.findByUUID(invitationUUID)
            .orElseThrow(() -> new NotFoundException("No se encontró la invitación con el UUID: " + invitationUUID));

        if (dto.getName() != null) invitation.setName(dto.getName());
        if (dto.getMax_people() != null) invitation.setMax_people(dto.getMax_people());
        if (dto.getSlug_url() != null) invitation.setSlug_url(dto.getSlug_url());

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
