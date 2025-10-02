package com.ozcaar.invirtual.common.exception.documentation;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.ozcaar.invirtual.auth.dto.LoginDTO;
import com.ozcaar.invirtual.common.exception.global.ApiError;
import com.ozcaar.invirtual.common.exception.global.ApiFieldsError;
import com.ozcaar.invirtual.user.dto.create.UserCreateDTO;
import com.ozcaar.invirtual.user.dto.read.UserReadDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

public interface AuthApiDoc {

    // Auth Controller
    @Operation(summary = "Registrar nuevo usuario", description = "Devuelve el usuario creado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Registro correcto",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = UserReadDTO.class))),
        @ApiResponse(responseCode = "400", description = "El formato JSON capturado está mal formado",
            content = @Content(schema = @Schema(implementation = ApiFieldsError.class))),
        @ApiResponse(responseCode = "409", description = "Usuario ya existe",
            content = @Content(schema = @Schema(implementation = ApiError.class))),
        @ApiResponse(responseCode = "500", description = "Error interno",
            content = @Content(schema = @Schema(implementation = ApiError.class))),

    })
    ResponseEntity<Map<String, Object>> register(UserCreateDTO dto);


    @Operation(summary = "Iniciar sesión", description = "Devuelve un JWT")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Login correcto",
            content = @Content(mediaType = "application/json",
                schema = @Schema(example = "{\"token\":\"...\"}"))),
        @ApiResponse(responseCode = "400", description = "El formato JSON capturado está mal formado",
            content = @Content(schema = @Schema(implementation = ApiFieldsError.class))),
        @ApiResponse(responseCode = "401", description = "Credenciales inválidas",
            content = @Content(schema = @Schema(implementation = ApiError.class))),
        @ApiResponse(responseCode = "500", description = "Error interno",
            content = @Content(schema = @Schema(implementation = ApiError.class))),

    })
    ResponseEntity<Map<String, String>> login(LoginDTO dto);
}
