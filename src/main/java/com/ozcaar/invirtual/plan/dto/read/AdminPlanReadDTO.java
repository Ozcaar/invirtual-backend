package com.ozcaar.invirtual.plan.dto.read;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record AdminPlanReadDTO(
        UUID id,
        String code,
        String name,
        String description,
        List<String> features,
        boolean active,
        Integer position,
        Instant createdAt) {
}