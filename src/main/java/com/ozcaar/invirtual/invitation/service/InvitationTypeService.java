package com.ozcaar.invirtual.invitation.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ozcaar.invirtual.invitation.dto.read.InvitationTypeReadDTO;
import com.ozcaar.invirtual.invitation.repository.InvitationTypeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InvitationTypeService {

    private final InvitationTypeRepository invitationTypeRepository;

    @Transactional(readOnly = true)
    public List<InvitationTypeReadDTO> getActiveInvitationTypes() {
        return invitationTypeRepository.findAllByActiveTrueOrderByNameAsc().stream()
                .map(type -> new InvitationTypeReadDTO(type.getInvitationTypeId(), type.getName()))
                .toList();
    }
}
