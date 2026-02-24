package com.ozcaar.invirtual.plan.dto.update;

import jakarta.validation.constraints.NotNull;

public record AdminPriceOverrideUpdateDTO(
        @NotNull Boolean active) {
}