package com.ozcaar.invirtual.plan.dto.read;

import java.util.List;

public record PlanReadDTO(
        String code,
        String name,
        int maxPeople,
        int durationDays,
        String description,
        List<String> features) {
}