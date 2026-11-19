package com.ozcaar.invirtual.draft.dto.create;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record DraftCreateDTO(
        @NotBlank @Size(max = 40) String planCode,
        @Size(min = 3, max = 100) String name,
        @Positive Integer invitationTypeId,
        String slugUrl,
        @Positive Integer schemaVersion,
        JsonNode configuration) {

    public DraftCreateDTO {
        name = name == null ? null : name.trim();
        schemaVersion = schemaVersion == null ? 1 : schemaVersion;
        configuration = configuration == null ? JsonNodeFactory.instance.objectNode() : configuration;
    }
}
