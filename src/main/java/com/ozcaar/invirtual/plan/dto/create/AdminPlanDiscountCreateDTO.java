package com.ozcaar.invirtual.plan.dto.create;

import java.time.LocalDateTime;
import java.util.UUID;

import com.ozcaar.invirtual.plan.enums.DiscountTypeEnum;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AdminPlanDiscountCreateDTO(

		@NotNull UUID planPriceId,

		@NotBlank String name,

		String publicCode, // "FEB10" (null = auto-applied, no code)

		// Stripe references (promotion_code or coupon)
		String stripePromotionCodeId, // promo_...
		String stripeCouponId, // coupon_...

		// Optionals (informative only; Stripe is the source of truth)
		DiscountTypeEnum discountType,
		Integer percentOff,
		Integer amountOff,
		LocalDateTime startsAt,
		LocalDateTime endsAt,
		Integer maxRedemptions,
		Boolean newCustomersOnly,
		Integer priority,
		Boolean stackable,
		Boolean active) {
}