package com.ozcaar.invirtual.plan.dto.read;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

import com.ozcaar.invirtual.plan.enums.DiscountTypeEnum;

public record AdminPlanDiscountReadDTO(
        UUID id,
        UUID planPriceId,
        String name,
        String publicCode,
        String stripePromotionCodeId,
        String stripeCouponId,
        DiscountTypeEnum discountType,
        Integer percentOff,
        Integer amountOff,
        LocalDateTime startsAt,
        LocalDateTime endsAt,
        Integer maxRedemptions,
        boolean newCustomersOnly,
        int priority,
        boolean stackable,
        boolean active,
        Instant createdAt) {
}