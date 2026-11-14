package com.ozcaar.invirtual.invitation.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ozcaar.invirtual.invitation.dto.read.InvitationTypeReadDTO;
import com.ozcaar.invirtual.invitation.service.InvitationTypeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/invitation/type")
@Tag(name = "Invitation Type", description = "Consulta de tipos de invitación")
@RequiredArgsConstructor
public class InvitationTypeController {

    private final InvitationTypeService invitationTypeService;

    @GetMapping("/list")
    @Operation(summary = "Obtener tipos de invitación activos", description = "Requiere autenticación. Ordenados por nombre.")
    public List<InvitationTypeReadDTO> getInvitationTypes() {
        return invitationTypeService.getActiveInvitationTypes();
    }
}
