package com.ozcaar.invirtual.plan.dto.create;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AdminPriceOverrideCreateDTO(

        @NotNull UUID planPriceId,

        @NotBlank String name,

        @NotBlank String stripePriceIdOverride, // price_... (another Stripe price ID)

        LocalDateTime startsAt,
        LocalDateTime endsAt,

        Integer priority,
        Boolean active) {
}