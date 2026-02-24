package com.ozcaar.invirtual.plan.dto.create;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

import com.ozcaar.invirtual.billing.enums.PlanKeyEnum;

public record AdminPlanCreateDTO(

        @NotNull(message = "El código es requerido.") PlanKeyEnum code,

        @NotBlank(message = "El nombre es requerido.") String name,

        String description,

        @NotNull(message = "El número máximo de personas es requerido.") Integer maxPeople,

        @NotNull(message = "La duración en días es requerida.") Integer durationDays,

        @NotEmpty(message = "La lista de características no puede estar vacía.") List<String> features,

        Integer position) {
}