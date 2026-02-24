package com.ozcaar.invirtual.plan.dto.read;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

public record AdminPriceOverrideReadDTO(
        UUID id,
        UUID planPriceId,
        String name,
        String stripePriceIdOverride,
        LocalDateTime startsAt,
        LocalDateTime endsAt,
        int priority,
        boolean active,
        Instant createdAt) {
}