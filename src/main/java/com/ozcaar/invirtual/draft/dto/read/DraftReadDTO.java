package com.ozcaar.invirtual.draft.dto.read;

import java.time.Instant;
import java.util.UUID;

import com.fasterxml.jackson.databind.JsonNode;

public record DraftReadDTO(
        UUID id,
        Long version,
        SelectedPlan plan,
        String name,
        Integer invitationTypeId,
        SelectedTemplate template,
        String slugUrl,
        Integer schemaVersion,
        JsonNode configuration,
        EntitlementPlanMatch entitlementPlanMatch,
        Instant createdAt,
        Instant updatedAt) {

    public record SelectedPlan(UUID id, String code, String name, boolean active) {
    }

    public record SelectedTemplate(UUID id, String code, int revision, String name, boolean active,
            int schemaVersion) {
    }

    public enum EntitlementPlanMatch {
        NO_ENTITLEMENT, SAME_PLAN, DIFFERENT_PLAN, UNKNOWN_PLAN
    }
}
