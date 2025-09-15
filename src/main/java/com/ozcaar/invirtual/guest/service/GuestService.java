package com.ozcaar.invirtual.guest.service;

import java.util.UUID;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import com.ozcaar.invirtual.auth.service.AuthService;
import com.ozcaar.invirtual.common.exception.global.NotFoundException;
import com.ozcaar.invirtual.common.repository.InvitationGuestRepository;
import com.ozcaar.invirtual.common.repository.UserInvitationRepository;
import com.ozcaar.invirtual.invitation.model.InvitationModel;
import com.ozcaar.invirtual.invitation.repository.InvitationRepository;
import com.ozcaar.invirtual.guest.dto.create.GuestCreateDTO;
import com.ozcaar.invirtual.guest.dto.read.GuestReadDTO;
import com.ozcaar.invirtual.guest.mapper.GuestMapper;
import com.ozcaar.invirtual.guest.model.GuestModel;
import com.ozcaar.invirtual.user.model.UserModel;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GuestService {

    private final AuthService authService;
    private final InvitationRepository invitationRepository;
    private final UserInvitationRepository userInvitationRepository;
    private final InvitationGuestRepository invitationGuestRepository;
    private final GuestMapper guestMapper;

    // CRUDs

    // CREATE
    public GuestReadDTO createGuest(UUID invitationUUID, GuestCreateDTO dto) {

        UserModel user = authService.getUserFromToken()
            .orElseThrow(() -> new NotFoundException("Ha ocurrido un error con el usuario"));

        InvitationModel invitation = invitationRepository.findByUUID(invitationUUID)
            .orElseThrow(() -> new NotFoundException("Invitación no encontrada"));

            
        if (!userInvitationRepository.findByUserIdAndInvitationName(user.getUser_id(), invitation.getName()).isPresent()) {
            throw new AccessDeniedException("No puedes modificar esta invitación");
        }
        
        if (!invitationGuestRepository.findByInvitationUUIDAndGuestName(invitation.getInvitation_uuid(), dto.getName().trim()).isPresent()) {
            throw new AccessDeniedException("Ya existe un usuario con ese nombre");
        }

        GuestModel guest = guestMapper.toEntity(dto);

        return guestMapper.toDTO(guest);
    }

    // READ

    // UPDATE

    // DELETE

}
