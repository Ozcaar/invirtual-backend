package com.ozcaar.invirtual.common.exception.documentation;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.ozcaar.invirtual.common.exception.global.ApiError;
import com.ozcaar.invirtual.invitation.dto.create.InvitationCreateDTO;
import com.ozcaar.invirtual.invitation.dto.read.InvitationReadDTO;
import com.ozcaar.invirtual.invitation.dto.update.InvitationUpdateDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

public interface InvitationApiDoc {

        @Operation(summary = "Crear nueva invitación", description = "Devuelve la invitación creada")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Registro correcto",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = InvitationReadDTO.class))),
        @ApiResponse(responseCode = "400", description = "El formato JSON capturado está mal formado",
            content = @Content(schema = @Schema(implementation = ApiError.class))),
        @ApiResponse(responseCode = "403", description = "No tiene permisos",
            content = @Content(schema = @Schema(implementation = ApiError.class))),
        @ApiResponse(responseCode = "409", description = "Usuario ya existe",
            content = @Content(schema = @Schema(implementation = ApiError.class))),
        @ApiResponse(responseCode = "500", description = "Error interno",
            content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    ResponseEntity<InvitationReadDTO> createInvitation(InvitationCreateDTO invitation);

    @Operation(summary = "Busca una invitación rol por ID", description = "Devuelve la invitación")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = InvitationReadDTO.class))),
        @ApiResponse(responseCode = "400", description = "El formato JSON capturado está mal formado",
            content = @Content(schema = @Schema(implementation = ApiError.class))),
        @ApiResponse(responseCode = "403", description = "No tiene permisos",
            content = @Content(schema = @Schema(implementation = ApiError.class))),
        @ApiResponse(responseCode = "409", description = "Usuario ya existe",
            content = @Content(schema = @Schema(implementation = ApiError.class))),
        @ApiResponse(responseCode = "500", description = "Error interno",
            content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    ResponseEntity<InvitationReadDTO> getInvitation(Integer id);

    @Operation(summary = "Obtener lista de invitaciones", description = "Devuelve el listado de todos las invitaciones")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "",
            content = @Content(mediaType = "application/json",
                array = @ArraySchema(schema = @Schema(implementation = InvitationReadDTO.class)))),
        @ApiResponse(responseCode = "400", description = "El formato JSON capturado está mal formado",
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
            content = @Content(schema = @Schema(implementation = ApiError.class))),
        @ApiResponse(responseCode = "400", description = "El ID proporcionado no puede ser nulo",
            content = @Content(schema = @Schema(implementation = ApiError.class))),
        @ApiResponse(responseCode = "403", description = "No tiene permisos",
            content = @Content(schema = @Schema(implementation = ApiError.class))),
        @ApiResponse(responseCode = "409", description = "Usuario ya existe",
            content = @Content(schema = @Schema(implementation = ApiError.class))),
        @ApiResponse(responseCode = "500", description = "Error interno",
            content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    ResponseEntity<InvitationReadDTO> updateInvitation(Integer id, InvitationUpdateDTO dto);

    @Operation(summary = "Elimina una invitación por ID", description = "Elimina la invitación solicitada y devuelve una confirmación (NO CONTENT)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Se eliminó correctamente"),
        @ApiResponse(responseCode = "400", description = "El formato JSON capturado está mal formado",
            content = @Content(schema = @Schema(implementation = ApiError.class))),
        @ApiResponse(responseCode = "403", description = "No tiene permisos",
            content = @Content(schema = @Schema(implementation = ApiError.class))),
        @ApiResponse(responseCode = "409", description = "Usuario ya existe",
            content = @Content(schema = @Schema(implementation = ApiError.class))),
        @ApiResponse(responseCode = "500", description = "Error interno",
            content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    ResponseEntity<Void> deleteInvitation(Integer id);
}
