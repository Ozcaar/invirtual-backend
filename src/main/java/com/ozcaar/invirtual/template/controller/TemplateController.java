package com.ozcaar.invirtual.template.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.ozcaar.invirtual.common.exception.global.ApiError;
import com.ozcaar.invirtual.template.dto.read.TemplateReadDTO;
import com.ozcaar.invirtual.template.service.TemplateService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/template")
@RequiredArgsConstructor
@Tag(name = "Template", description = "Catálogo de revisiones de plantilla. Requiere autenticación.")
public class TemplateController {

    private final TemplateService templateService;

    @GetMapping("/list")
    @Operation(summary = "Consultar plantillas disponibles",
        description = "Filtra por planCode obligatorio y tipo opcional. La configuración inicial describe preferencias visuales, no permisos del plan.")
    public List<TemplateReadDTO> list(@RequestParam("planCode") String planCode,
            @RequestParam(value = "invitationTypeId", required = false) Integer invitationTypeId) {
        return templateService.list(planCode, invitationTypeId);
    }

    @ExceptionHandler({ MissingServletRequestParameterException.class, MethodArgumentTypeMismatchException.class })
    public ResponseEntity<ApiError> invalidFilters(Exception ex) {
        return ResponseEntity.badRequest().body(new ApiError(400, "BAD_REQUEST",
                "planCode es obligatorio e invitationTypeId debe ser un entero válido si se proporciona."));
    }
}
