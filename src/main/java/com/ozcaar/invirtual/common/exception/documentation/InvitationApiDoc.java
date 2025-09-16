package com.ozcaar.invirtual.common.exception.documentation;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.ozcaar.invirtual.common.exception.global.ApiError;
import com.ozcaar.invirtual.common.exception.global.ApiFieldsError;
import com.ozcaar.invirtual.guest.dto.create.GuestCreateDTO;
import com.ozcaar.invirtual.guest.dto.read.GuestReadDTO;
import com.ozcaar.invirtual.invitation.dto.create.InvitationCreateDTO;
import com.ozcaar.invirtual.invitation.dto.read.InvitationReadDTO;
import com.ozcaar.invirtual.invitation.dto.update.InvitationUpdateDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

public interface InvitationApiDoc {

    @Operation(summary = "Crear nueva invitación", description = "Devuelve la invitación creada")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Registro correcto",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = InvitationReadDTO.class))),
        @ApiResponse(responseCode = "400", description = "El formato JSON capturado está mal formado",
            content = @Content(schema = @Schema(implementation = ApiFieldsError.class))),
        @ApiResponse(responseCode = "401", description = "La petición carece de credenciales válidas",
            content = @Content(schema = @Schema(implementation = ApiError.class))),
        @ApiResponse(responseCode = "403", description = "No tiene permisos",
            content = @Content(schema = @Schema(implementation = ApiError.class))),
        @ApiResponse(responseCode = "409", description = "La invitación ya existe",
            content = @Content(schema = @Schema(implementation = ApiError.class))),
        @ApiResponse(responseCode = "500", description = "Error interno",
            content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    ResponseEntity<InvitationReadDTO> createInvitation(@Valid @RequestBody InvitationCreateDTO invitation);

    @Operation(summary = "Busca una invitación rol por ID", description = "Devuelve la invitación")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = InvitationReadDTO.class))),
        @ApiResponse(responseCode = "401", description = "La petición carece de credenciales válidas",
            content = @Content(schema = @Schema(implementation = ApiError.class))),
        @ApiResponse(responseCode = "403", description = "No tiene permisos",
            content = @Content(schema = @Schema(implementation = ApiError.class))),
        @ApiResponse(responseCode = "500", description = "Error interno",
            content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    ResponseEntity<InvitationReadDTO> getInvitation(UUID invitationUUID);

    @Operation(summary = "Obtener lista de invitaciones", description = "Devuelve el listado de todas las invitaciones")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "",
            content = @Content(mediaType = "application/json",
                array = @ArraySchema(schema = @Schema(implementation = InvitationReadDTO.class)))),
        @ApiResponse(responseCode = "401", description = "La petición carece de credenciales válidas",
            content = @Content(schema = @Schema(implementation = ApiError.class))),
        @ApiResponse(responseCode = "403", description = "No tiene permisos",
            content = @Content(schema = @Schema(implementation = ApiError.class))),
        @ApiResponse(responseCode = "500", description = "Error interno",
            content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    ResponseEntity<List<InvitationReadDTO>> getAllInvitations();

    @Operation(summary = "Actualiza una invitación por ID", description = "Actualiza una invitación y devuelve la invitación actualizada")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = InvitationReadDTO.class))),
        @ApiResponse(responseCode = "400", description = "El formato JSON capturado está mal formado",
            content = @Content(schema = @Schema(implementation = ApiFieldsError.class))),
        @ApiResponse(responseCode = "401", description = "La petición carece de credenciales válidas",
            content = @Content(schema = @Schema(implementation = ApiError.class))),
        @ApiResponse(responseCode = "403", description = "No tiene permisos",
            content = @Content(schema = @Schema(implementation = ApiError.class))),
        @ApiResponse(responseCode = "500", description = "Error interno",
            content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    ResponseEntity<InvitationReadDTO> updateInvitation(@PathVariable UUID invitationUUID, @Valid  @RequestBody InvitationUpdateDTO dto);

    @Operation(summary = "Elimina una invitación por ID", description = "Elimina la invitación solicitada y devuelve una confirmación (NO CONTENT)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Se eliminó correctamente"),
        @ApiResponse(responseCode = "401", description = "La petición carece de credenciales válidas",
            content = @Content(schema = @Schema(implementation = ApiError.class))),
        @ApiResponse(responseCode = "403", description = "No tiene permisos",
            content = @Content(schema = @Schema(implementation = ApiError.class))),
        @ApiResponse(responseCode = "500", description = "Error interno",
            content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    ResponseEntity<Void> deleteInvitation(UUID invitationUUID);

    @Operation(summary = "Agregar un invitado a una invitación", description = "Devuelve el invitado creado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Registro correcto",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = GuestReadDTO.class))),
        @ApiResponse(responseCode = "400", description = "El formato JSON capturado está mal formado",
            content = @Content(schema = @Schema(implementation = ApiFieldsError.class))),
        @ApiResponse(responseCode = "401", description = "La petición carece de credenciales válidas",
            content = @Content(schema = @Schema(implementation = ApiError.class))),
        @ApiResponse(responseCode = "403", description = "No tiene permisos",
            content = @Content(schema = @Schema(implementation = ApiError.class))),
        @ApiResponse(responseCode = "409", description = "El invitado ya existe",
            content = @Content(schema = @Schema(implementation = ApiError.class))),
        @ApiResponse(responseCode = "500", description = "Error interno",
            content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    ResponseEntity<GuestReadDTO> createGuest(@PathVariable UUID invitationUUID, @Valid @RequestBody GuestCreateDTO guest);
    
    @Operation(summary = "Agregar varios invitados a una invitación", description = "Devuelve la lista de invitados creados")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Registro correcto",
            content = @Content(mediaType = "application/json",
                array = @ArraySchema(schema = @Schema(implementation = GuestReadDTO.class)))),
        @ApiResponse(responseCode = "400", description = "El formato JSON capturado está mal formado",
            content = @Content(schema = @Schema(implementation = ApiFieldsError.class))),
        @ApiResponse(responseCode = "401", description = "La petición carece de credenciales válidas",
            content = @Content(schema = @Schema(implementation = ApiError.class))),
        @ApiResponse(responseCode = "403", description = "No tiene permisos",
            content = @Content(schema = @Schema(implementation = ApiError.class))),
        @ApiResponse(responseCode = "409", description = "Alguno de los invitados ya existe",
            content = @Content(schema = @Schema(implementation = ApiError.class))),
        @ApiResponse(responseCode = "500", description = "Error interno",
            content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    ResponseEntity<List<GuestReadDTO>> createBatchGuests(@PathVariable UUID invitationUUID, @Valid @RequestBody List<@Valid GuestCreateDTO> guestBatch);

    @Operation(summary = "Obtener lista de invitados de una invitación", description = "Devuelve el listado de todos los invitados de una invitación")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "",
            content = @Content(mediaType = "application/json",
                array = @ArraySchema(schema = @Schema(implementation = GuestReadDTO.class)))),
        @ApiResponse(responseCode = "401", description = "La petición carece de credenciales válidas",
            content = @Content(schema = @Schema(implementation = ApiError.class))),
        @ApiResponse(responseCode = "403", description = "No tiene permisos",
            content = @Content(schema = @Schema(implementation = ApiError.class))),
        @ApiResponse(responseCode = "500", description = "Error interno",
            content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    ResponseEntity<List<GuestReadDTO>> getAllGuests(@PathVariable UUID invitationUUID);
}
