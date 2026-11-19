package com.ozcaar.invirtual.template.dto.read;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.databind.JsonNode;

public record TemplateReadDTO(
        UUID id,
        String code,
        int revision,
        String name,
        String description,
        String thumbnailUrl,
        List<String> compatiblePlanCodes,
        List<Integer> invitationTypeIds,
        int schemaVersion,
        JsonNode initialConfiguration) {
}
