package com.ozcaar.invirtual.guest.service;

import java.util.List;
import java.util.UUID;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import com.ozcaar.invirtual.auth.service.AuthService;
import com.ozcaar.invirtual.common.exception.global.AlreadyExistsException;
import com.ozcaar.invirtual.common.exception.global.NotFoundException;
import com.ozcaar.invirtual.common.model.manytomany.InvitationGuestModel;
import com.ozcaar.invirtual.common.repository.InvitationGuestRepository;
import com.ozcaar.invirtual.common.repository.UserInvitationRepository;
import com.ozcaar.invirtual.invitation.model.InvitationModel;
import com.ozcaar.invirtual.invitation.repository.InvitationRepository;
import com.ozcaar.invirtual.guest.dto.create.GuestCreateDTO;
import com.ozcaar.invirtual.guest.dto.read.GuestReadDTO;
import com.ozcaar.invirtual.guest.mapper.GuestMapper;
import com.ozcaar.invirtual.guest.model.GuestGroupModel;
import com.ozcaar.invirtual.guest.model.GuestModel;
import com.ozcaar.invirtual.guest.repository.GuestGroupRepository;
import com.ozcaar.invirtual.guest.repository.GuestRepository;
import com.ozcaar.invirtual.user.model.UserModel;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GuestService {

    private final AuthService authService;
    private final GuestRepository guestRepository;
    private final InvitationRepository invitationRepository;
    private final GuestGroupRepository groupRepository;
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

        GuestGroupModel guestGroup = groupRepository.findById(dto.getGuest_group_id())
            .orElseThrow(() -> new NotFoundException("Grupo no encontrado"));
            
        if (!userInvitationRepository.findByUserIdAndInvitationName(user.getUser_id(), invitation.getName()).isPresent()) {
            throw new AccessDeniedException("No puedes modificar esta invitación");
        }
        
        if (invitationGuestRepository.findByInvitationUUIDAndGuestName(invitation.getInvitationUuid(), dto.getName().trim()).isPresent()) {
            throw new AlreadyExistsException("Ya existe un invitado con ese nombre");
        }

        GuestModel guest = guestMapper.toEntity(dto);

        guest.setGuest_group(guestGroup);

        guestRepository.save(guest);

        InvitationGuestModel invitationGuest = new InvitationGuestModel();

        invitationGuest.setInvitation(invitation);
        invitationGuest.setGuest_group(guestGroup);
        invitationGuest.setGuest(guest);
        invitationGuestRepository.save(invitationGuest);

        return guestMapper.toDTO(guest);
    }

    // READ

    public List<GuestReadDTO> getAllGuests(UUID invitationUUID) {

        UserModel user = authService.getUserFromToken()
            .orElseThrow(() -> new NotFoundException("Ha ocurrido un error con el usuario"));

        InvitationModel invitation = invitationRepository.findByUUID(invitationUUID)
            .orElseThrow(() -> new NotFoundException("Invitación no encontrada"));
  
        if (!userInvitationRepository.findByUserIdAndInvitationName(user.getUser_id(), invitation.getName()).isPresent()) {
            throw new AccessDeniedException("No puedes modificar esta invitación");
        }

        List<GuestModel> guestList = invitationGuestRepository.findGuestsByInvitationUuid(invitationUUID);
        return guestMapper.toDTOList(guestList);
    }

    // UPDATE

    // DELETE

}
