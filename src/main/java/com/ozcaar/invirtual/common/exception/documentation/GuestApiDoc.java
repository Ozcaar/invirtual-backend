package com.ozcaar.invirtual.common.exception.documentation;

import org.springframework.http.ResponseEntity;

import com.ozcaar.invirtual.common.exception.global.ApiError;
import com.ozcaar.invirtual.common.exception.global.ApiFieldsError;
import com.ozcaar.invirtual.guest.dto.create.GuestCreateDTO;
import com.ozcaar.invirtual.guest.dto.read.GuestReadDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

public interface GuestApiDoc {
    @Operation(summary = "Crear nuevo invitado", description = "Devuelve el invitado")
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
        @ApiResponse(responseCode = "409", description = "Invitación ya existe",
            content = @Content(schema = @Schema(implementation = ApiError.class))),
        @ApiResponse(responseCode = "500", description = "Error interno",
            content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    ResponseEntity<GuestReadDTO> createGuest(Integer id, GuestCreateDTO guest);
    
}