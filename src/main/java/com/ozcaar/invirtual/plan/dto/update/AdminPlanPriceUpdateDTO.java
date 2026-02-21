package com.ozcaar.invirtual.plan.dto.update;

import jakarta.validation.constraints.NotNull;

public record AdminPlanPriceUpdateDTO(
        @NotNull(message = "El campo 'active' es requerido.") Boolean active) {
}