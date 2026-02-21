package com.ozcaar.invirtual.plan.dto.create;

import com.ozcaar.invirtual.billing.enums.PlanKeyEnum;

public record CheckoutSessionCreateDTO(
        PlanKeyEnum planCode,
        String currency, // Ex: "MXN", optional
        String promoCode // Ex: "SUMMER21", optional
    ) {
}
