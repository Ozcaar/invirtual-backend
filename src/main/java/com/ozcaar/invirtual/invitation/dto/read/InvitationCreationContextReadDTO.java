package com.ozcaar.invirtual.invitation.dto.read;

import java.util.UUID;

public record InvitationCreationContextReadDTO(AvailableEntitlement availableEntitlement, Demo demo) {

    public record AvailableEntitlement(Long id, PlanSnapshot plan, Capabilities capabilities) {
    }

    public record PlanSnapshot(UUID id, String code, String name) {
    }

    public record Capabilities(int maxPeople, int durationDays) {
    }

    public record Demo(boolean eligible, String blockedReason, Capabilities capabilities) {
    }
}
