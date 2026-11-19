package com.ozcaar.invirtual.draft.controller;

import java.net.URI;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.ozcaar.invirtual.common.exception.global.ApiError;
import com.ozcaar.invirtual.draft.dto.create.DraftCreateDTO;
import com.ozcaar.invirtual.draft.dto.read.DraftReadDTO;
import com.ozcaar.invirtual.draft.dto.update.DraftUpdateDTO;
import com.ozcaar.invirtual.draft.service.DraftService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/draft")
@RequiredArgsConstructor
@Tag(name = "Draft", description = "Borrador del usuario autenticado. No reserva ni consume derechos.")
public class DraftController {

    private final DraftService draftService;

    @PostMapping
    @Operation(summary = "Crear borrador", description = "Requiere un plan. Devuelve 409 si ya existe un borrador.")
    public ResponseEntity<DraftReadDTO> create(@Valid @RequestBody DraftCreateDTO dto) {
        return ResponseEntity.created(URI.create("/api/draft/current")).body(draftService.create(dto));
    }

    @GetMapping("/current")
    @Operation(summary = "Recuperar borrador actual", description = "Devuelve 404 si no existe.")
    public DraftReadDTO getCurrent() {
        return draftService.getCurrent();
    }

    @PutMapping("/current")
    @Operation(summary = "Reemplazar datos del borrador", description = "Requiere el id y la versión leídos. Devuelve 409 si están desactualizados.")
    public DraftReadDTO update(@Valid @RequestBody DraftUpdateDTO dto) {
        return draftService.update(dto);
    }

    @DeleteMapping("/current")
    @Operation(summary = "Descartar borrador", description = "Requiere draftId. Devuelve 204 si ya no existe y 409 si el actual es otro borrador.")
    public ResponseEntity<Void> delete(@RequestParam("draftId") UUID draftId) {
        draftService.delete(draftId);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler({ MissingServletRequestParameterException.class, MethodArgumentTypeMismatchException.class })
    public ResponseEntity<ApiError> invalidDraftId(Exception ex) {
        return ResponseEntity.badRequest()
                .body(new ApiError(400, "BAD_REQUEST", "El parámetro draftId es obligatorio y debe ser un UUID válido."));
    }
}
