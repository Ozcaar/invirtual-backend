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
import com.ozcaar.invirtual.guest.dto.create.GuestCreateDTO;
import com.ozcaar.invirtual.guest.dto.read.GuestReadDTO;
import com.ozcaar.invirtual.guest.service.GuestService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user/invitations")
@Tag(name = "Invitation Controller", description = "operaciones con invitaciones")
@RequiredArgsConstructor
public class InvitationController implements InvitationApiDoc {
    
    private final InvitationService invitationService;
    private final GuestService guestService;

    // POST
    @PreAuthorize("hasRole('DEV')")
    @PostMapping()
    @Operation(summary = "Crear invitación", description = "- Se requiere el rol \"DEV\"")
    public ResponseEntity<InvitationReadDTO> createInvitation(@Valid @RequestBody InvitationCreateDTO invitation) {
        InvitationReadDTO created = invitationService.createInvitation(invitation);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // GET
    @GetMapping("/{invitationUUID}")
    @Operation(summary = "Obtener invitación por UUID", description = "")
    public ResponseEntity<InvitationReadDTO> getInvitation(@PathVariable UUID invitationUUID) {
        InvitationReadDTO invitation = invitationService.getInvitation(invitationUUID);
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
    @PutMapping("/{invitationUUID}")
    @Operation(summary = "Actualizar invitación", description = "- Se requiere rol \"DEV\"")
    public ResponseEntity<InvitationReadDTO> updateInvitation(@PathVariable UUID invitationUUID, @Valid  @RequestBody InvitationUpdateDTO dto) {
        InvitationReadDTO updatedInvitation = invitationService.updateInvitation(invitationUUID, dto);
        return new ResponseEntity<>(updatedInvitation, HttpStatus.OK);
    }

    // DELETE
    @PreAuthorize("hasRole('DEV')")
    @DeleteMapping("/{invitationUUID}")
    @Operation(summary = "Eliminar invitación", description = "- Se requiere rol \"DEV\"")
    public ResponseEntity<Void> deleteInvitation(@PathVariable UUID invitationUUID) {
        invitationService.deleteInvitation(invitationUUID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // INVITEDS

    // POST
    @PostMapping("/{invitationUUID}/guests")
    @Operation(summary = "Agregar un invitado a la invitación", description = "")
    public ResponseEntity<GuestReadDTO> createGuest(@PathVariable UUID invitationUUID, @Valid @RequestBody GuestCreateDTO guest) {
        GuestReadDTO created = guestService.createGuest(invitationUUID, guest);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PostMapping("/{invitationUUID}/guests/batch-create")
    @Operation(summary = "Agregar una lista de invitados a la invitación", description = "")
    public ResponseEntity<List<GuestReadDTO>> createBatchGuests(@PathVariable UUID invitationUUID, @Valid @RequestBody List<@Valid GuestCreateDTO> guestBatch) {
        List<GuestReadDTO> created = guestService.createBatchGuests(invitationUUID, guestBatch);
        // new List<GuestReadDTO>();
        return new ResponseEntity<List<GuestReadDTO>>(created, HttpStatus.CREATED);
    }

    // GET
    @GetMapping("/{invitationUUID}/guests")
    @Operation(summary = "Obtener lista de invitados de una invitación", description = "")
    public ResponseEntity<List<GuestReadDTO>> getAllGuests(@PathVariable UUID invitationUUID) {
        List<GuestReadDTO> guestList = guestService.getAllGuests(invitationUUID);
        return new ResponseEntity<>(guestList, HttpStatus.OK);
    }


}
