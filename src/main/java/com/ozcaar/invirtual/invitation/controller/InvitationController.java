package com.ozcaar.invirtual.invitation.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ozcaar.invirtual.common.exception.documentation.InvitationApiDoc;
import com.ozcaar.invirtual.invitation.dto.create.InvitationCreateDTO;
import com.ozcaar.invirtual.invitation.dto.read.InvitationReadDTO;
import com.ozcaar.invirtual.invitation.dto.update.InvitationUpdateDTO;
import com.ozcaar.invirtual.invitation.service.InvitationService;
import com.ozcaar.invirtual.invited.dto.create.InvitedCreateDTO;
import com.ozcaar.invirtual.invited.dto.read.InvitedReadDTO;
import com.ozcaar.invirtual.invited.service.InvitedService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/user/invitations")
@Tag(name = "Invitation Controller", description = "operaciones con invitaciones")
public class InvitationController implements InvitationApiDoc {
    
    @Autowired
    InvitationService invitationService;

    // POST
    @PreAuthorize("hasRole('DEV')")
    @PostMapping()
    @Operation(summary = "Crear invitación", description = "- Se requiere el rol \"DEV\"")
    public ResponseEntity<InvitationReadDTO> createInvitation(@Valid @RequestBody InvitationCreateDTO invitation) {
        InvitationReadDTO created = invitationService.createInvitation(invitation);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // GET
    @GetMapping("/{id}")
    @Operation(summary = "Obtener invitación por ID", description = "")
    public ResponseEntity<InvitationReadDTO> getInvitation(@PathVariable Integer id) {
        InvitationReadDTO invitation = invitationService.getInvitation(id);
        return new ResponseEntity<>(invitation, HttpStatus.OK);
    }

    @GetMapping()
    @Operation(summary = "Obtener lista de invitaciones", description = "")
    public ResponseEntity<List<InvitationReadDTO>> getAllInvitations() {
        List<InvitationReadDTO> invitationList = invitationService.getAllInvitations();
        return new ResponseEntity<>(invitationList, HttpStatus.OK);
    }

    // UPDATE
    @PreAuthorize("hasRole('DEV')")
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar invitación", description = "- Se requiere rol \"DEV\"")
    public ResponseEntity<InvitationReadDTO> updateInvitation(@PathVariable Integer id, @Valid  @RequestBody InvitationUpdateDTO dto) {
        InvitationReadDTO updatedInvitation = invitationService.updateInvitation(id, dto);
        return new ResponseEntity<>(updatedInvitation, HttpStatus.OK);
    }

    // DELETE
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar invitación", description = "")
    public ResponseEntity<Void> deleteInvitation(@PathVariable Integer id) {
        invitationService.deleteInvitation(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // INVITEDS
    @PostMapping("/{invitationUUID}/inviteds")
    @Operation(summary = "Agregar un invitado a la invitación", description = "")
    public ResponseEntity<InvitedReadDTO> createInvited(@PathVariable UUID invitationUUID, @Valid @RequestBody InvitedCreateDTO invited) {
        InvitedReadDTO created = invitedService.createInvited(invitationUUID, invited);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // @PostMapping("/{invitationUUID}/inviteds/batch-create")
    // @Operation(summary = "Agregar una lista de invitados a la invitación", description = "")
    // public ResponseEntity<List<InvitedReadDTO>> createBatchInviteds(@PathVariable UUID invitationUUID, @Valid @RequestBody List<InvitedCreateDTO> invitedBatch) {
    //     // List<InvitedReadDTO> created = invitedService.createBatchInviteds(invitationUUID, invitedBatch);
    //     new List<InvitedReadDTO>();
    //     return new ResponseEntity<List<InvitedReadDTO>>(created, HttpStatus.CREATED);
    // }

}
