package com.ozcaar.invirtual.draft.dto.update;

import java.util.UUID;

import com.fasterxml.jackson.databind.JsonNode;

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
        String slugUrl,
        @NotNull @Positive Integer schemaVersion,
        @NotNull JsonNode configuration) {

    public DraftUpdateDTO {
        name = name == null ? null : name.trim();
    }
}
