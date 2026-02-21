
package com.ozcaar.invirtual.plan.dto.create;

import com.ozcaar.invirtual.billing.enums.BillingTypeEnum;
import com.ozcaar.invirtual.plan.enums.EnvironmentEnum;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AdminPlanPriceCreateDTO(
                @NotBlank String planCode,
                @NotNull EnvironmentEnum environment,
                BillingTypeEnum billingType,
                @NotBlank String stripePriceId,
                Boolean active) {

        // If billingType is not specified, it defaults to ONE_TIME
        public AdminPlanPriceCreateDTO {
                if (billingType == null) {
                        billingType = BillingTypeEnum.ONE_TIME;
                }
        }
}