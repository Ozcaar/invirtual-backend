package com.ozcaar.invirtual.common.exception.documentation;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.ozcaar.invirtual.common.exception.global.ApiError;
import com.ozcaar.invirtual.common.exception.global.ApiFieldsError;
import com.ozcaar.invirtual.role.dto.create.RoleCreateDTO;
import com.ozcaar.invirtual.role.dto.read.RoleReadDTO;
import com.ozcaar.invirtual.role.dto.update.RoleUpdateDTO;
import com.ozcaar.invirtual.user.dto.read.UserReadDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

public interface RoleApiDoc {

    @Operation(summary = "Crear nuevo rol", description = "Devuelve el rol creado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Registro correcto",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = UserReadDTO.class))),
        @ApiResponse(responseCode = "400", description = "El formato JSON capturado está mal formado",
            content = @Content(schema = @Schema(implementation = ApiFieldsError.class))),
        @ApiResponse(responseCode = "401", description = "La petición carece de credenciales válidas",
            content = @Content(schema = @Schema(implementation = ApiError.class))),
        @ApiResponse(responseCode = "403", description = "No tiene permisos",
            content = @Content(schema = @Schema(implementation = ApiError.class))),
        @ApiResponse(responseCode = "409", description = "El rol ya existe",
            content = @Content(schema = @Schema(implementation = ApiError.class))),
        @ApiResponse(responseCode = "500", description = "Error interno",
            content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    ResponseEntity<RoleReadDTO> createRole(RoleCreateDTO role);

    @Operation(summary = "Busca un rol por ID", description = "Devuelve el rol solicitado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = RoleReadDTO.class))),
        @ApiResponse(responseCode = "401", description = "La petición carece de credenciales válidas",
            content = @Content(schema = @Schema(implementation = ApiError.class))),
        @ApiResponse(responseCode = "403", description = "No tiene permisos",
            content = @Content(schema = @Schema(implementation = ApiError.class))),
        @ApiResponse(responseCode = "500", description = "Error interno",
            content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    ResponseEntity<RoleReadDTO> getRole(Integer id);

    @Operation(summary = "Obtener lista de roles", description = "Devuelve el listado de todos los roles")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = UserReadDTO.class))),
        @ApiResponse(responseCode = "400", description = "El formato JSON capturado está mal formado",
            content = @Content(schema = @Schema(implementation = ApiFieldsError.class))),
        @ApiResponse(responseCode = "401", description = "La petición carece de credenciales válidas",
            content = @Content(schema = @Schema(implementation = ApiError.class))),
        @ApiResponse(responseCode = "403", description = "No tiene permisos",
            content = @Content(schema = @Schema(implementation = ApiError.class))),
        @ApiResponse(responseCode = "500", description = "Error interno",
            content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    ResponseEntity<List<RoleReadDTO>> getAllRoles();

    @Operation(summary = "Actualiza un rol por ID", description = "Actualiza un rol y devuelve el rol actualizado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = RoleReadDTO.class))),
        @ApiResponse(responseCode = "400", description = "El formato JSON capturado está mal formado",
            content = @Content(schema = @Schema(implementation = ApiFieldsError.class))),
        @ApiResponse(responseCode = "401", description = "La petición carece de credenciales válidas",
            content = @Content(schema = @Schema(implementation = ApiError.class))),
        @ApiResponse(responseCode = "403", description = "No tiene permisos",
            content = @Content(schema = @Schema(implementation = ApiError.class))),
        @ApiResponse(responseCode = "500", description = "Error interno",
            content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    ResponseEntity<RoleReadDTO> updateRole(Integer id, RoleUpdateDTO dto);

    @Operation(summary = "Elimina un rol por ID", description = "Elimina el rol solicitado y devuelve una confirmación (NO CONTENT)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Se eliminó correctamente"),
        @ApiResponse(responseCode = "401", description = "La petición carece de credenciales válidas",
            content = @Content(schema = @Schema(implementation = ApiError.class))),
        @ApiResponse(responseCode = "403", description = "No tiene permisos",
            content = @Content(schema = @Schema(implementation = ApiError.class))),
        @ApiResponse(responseCode = "500", description = "Error interno",
            content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    ResponseEntity<Void> deleteRole(Integer id);
}