package com.ozcaar.invirtual.draft.dto.update;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public record DraftUpdateDTO(
        @NotNull UUID id,
        @NotNull @PositiveOrZero Long version,
        @NotBlank @Size(max = 40) String planCode,
        @Size(min = 3, max = 100) String name,
        @Positive Integer invitationTypeId,
        @JsonProperty(value = "templateId", required = true)
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, nullable = true,
                description = "Enviar siempre: UUID para seleccionar o conservar; null para deseleccionar. Omitir el campo devuelve 400.")
        UUID templateId,
        String slugUrl,
        @NotNull @Positive Integer schemaVersion,
        @Schema(description = "Personalizaciones explícitas del usuario. No se fusionan ni reinician al cambiar templateId. "
                + "El futuro editor deberá resolver incompatibilidades visuales con la nueva plantilla. No concede funcionalidades del plan.")
        @NotNull JsonNode configuration) {

    public DraftUpdateDTO {
        name = name == null ? null : name.trim();
    }
}
