package com.ozcaar.invirtual.plan.dto.update;

import jakarta.validation.constraints.NotNull;

public record AdminPlanDiscountUpdateDTO(
        @NotNull Boolean active) {
}