package com.ozcaar.invirtual.plan.dto.read;

import java.time.Instant;
import java.util.UUID;

import com.ozcaar.invirtual.billing.enums.BillingTypeEnum;
import com.ozcaar.invirtual.plan.enums.EnvironmentEnum;

public record AdminPlanPriceReadDTO(
        UUID id,
        String planCode,
        EnvironmentEnum environment,
        String currency,
        BillingTypeEnum billingType,
        String stripePriceId,
        boolean active,
        Instant createdAt) {
}