package com.ozcaar.invirtual.common.exception.documentation;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.ozcaar.invirtual.common.exception.global.ApiError;
import com.ozcaar.invirtual.common.exception.global.ApiFieldsError;
import com.ozcaar.invirtual.user.dto.create.UserCreateDTO;
import com.ozcaar.invirtual.user.dto.read.UserReadDTO;
import com.ozcaar.invirtual.user.dto.update.UserUpdateDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

public interface UserApiDoc {
    @Operation(summary = "Crear nuevo usuario", description = "Devuelve el usuario creado")
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
        @ApiResponse(responseCode = "409", description = "Usuario ya existe",
            content = @Content(schema = @Schema(implementation = ApiError.class))),
        @ApiResponse(responseCode = "500", description = "Error interno",
            content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    ResponseEntity<UserReadDTO> createUser(UserCreateDTO user);

    @Operation(summary = "Busca un usuario rol por ID", description = "Devuelve el usuario")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = UserReadDTO.class))),
        @ApiResponse(responseCode = "401", description = "La petición carece de credenciales válidas",
            content = @Content(schema = @Schema(implementation = ApiError.class))),
        @ApiResponse(responseCode = "403", description = "No tiene permisos",
            content = @Content(schema = @Schema(implementation = ApiError.class))),
        @ApiResponse(responseCode = "500", description = "Error interno",
            content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    ResponseEntity<UserReadDTO> getUser(Integer id);

    @Operation(summary = "Obtener lista de usuarios", description = "Devuelve el listado de todos los usuarios")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "",
            content = @Content(mediaType = "application/json",
                array = @ArraySchema(schema = @Schema(implementation = UserReadDTO.class)))),
        @ApiResponse(responseCode = "401", description = "La petición carece de credenciales válidas",
            content = @Content(schema = @Schema(implementation = ApiError.class))),
        @ApiResponse(responseCode = "403", description = "No tiene permisos",
            content = @Content(schema = @Schema(implementation = ApiError.class))),
        @ApiResponse(responseCode = "500", description = "Error interno",
            content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    ResponseEntity<List<UserReadDTO>> getAllUsers();

    @Operation(summary = "Actualiza un usuario por ID", description = "Actualiza un usuario y devuelve el usuario actualizado")
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
    ResponseEntity<UserReadDTO> updateUser(Integer id, UserUpdateDTO dto);

    @Operation(summary = "Elimina un usuario por ID", description = "Elimina el usuario solicitado y devuelve una confirmación (NO CONTENT)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Se eliminó correctamente"),
        @ApiResponse(responseCode = "401", description = "La petición carece de credenciales válidas",
            content = @Content(schema = @Schema(implementation = ApiError.class))),
        @ApiResponse(responseCode = "403", description = "No tiene permisos",
            content = @Content(schema = @Schema(implementation = ApiError.class))),
        @ApiResponse(responseCode = "500", description = "Error interno",
            content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    ResponseEntity<Void> deleteUser(Integer id);

}
