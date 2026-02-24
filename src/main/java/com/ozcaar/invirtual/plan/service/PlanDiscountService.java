package com.ozcaar.invirtual.plan.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ozcaar.invirtual.common.exception.global.GlobalBadRequestException;
import com.ozcaar.invirtual.common.exception.global.NotFoundException;
import com.ozcaar.invirtual.plan.dto.create.AdminPlanDiscountCreateDTO;
import com.ozcaar.invirtual.plan.dto.read.AdminPlanDiscountReadDTO;
import com.ozcaar.invirtual.plan.model.PlanDiscountModel;
import com.ozcaar.invirtual.plan.model.PlanPriceModel;
import com.ozcaar.invirtual.plan.repository.PlanDiscountRepository;
import com.ozcaar.invirtual.plan.repository.PlanPriceRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PlanDiscountService {

    private final PlanDiscountRepository discountRepo;
    private final PlanPriceRepository planPriceRepo;

    @Transactional
    public AdminPlanDiscountReadDTO create(AdminPlanDiscountCreateDTO req) {

        // 1) PlanPrice must exist
        @SuppressWarnings("null")
        PlanPriceModel planPrice = planPriceRepo.findById(req.planPriceId())
                .orElseThrow(() -> new NotFoundException("PlanPrice no encontrado: " + req.planPriceId()));

        String publicCode = normalizeNullable(req.publicCode());

        // 2) Validation: must provide either coupon or promotion_code (at least one)
        boolean hasPromo = notBlank(req.stripePromotionCodeId());
        boolean hasCoupon = notBlank(req.stripeCouponId());
        if (!hasPromo && !hasCoupon) {
            throw new GlobalBadRequestException("Debes enviar stripePromotionCodeId o stripeCouponId.");
        }

        // 3) If it has publicCode, avoid duplicates per planPrice
        if (publicCode != null
                && discountRepo.existsByPlanPrice_IdAndPublicCodeIgnoreCase(planPrice.getId(), publicCode)) {
            throw new GlobalBadRequestException("Ya existe publicCode para este planPrice: " + publicCode);
        }

        PlanDiscountModel d = new PlanDiscountModel();
        d.setPlanPrice(planPrice);
        d.setName(req.name().trim());
        d.setPublicCode(publicCode);

        d.setStripePromotionCodeId(trimOrNull(req.stripePromotionCodeId()));
        d.setStripeCouponId(trimOrNull(req.stripeCouponId()));

        d.setDiscountType(req.discountType());
        d.setPercentOff(req.percentOff());
        d.setAmountOff(req.amountOff());

        d.setStartsAt(req.startsAt());
        d.setEndsAt(req.endsAt());

        d.setMaxRedemptions(req.maxRedemptions());
        d.setNewCustomersOnly(Boolean.TRUE.equals(req.newCustomersOnly()));

        d.setPriority(req.priority() == null ? 0 : req.priority());
        d.setStackable(Boolean.TRUE.equals(req.stackable()));

        d.setActive(req.active() == null ? true : req.active());

        PlanDiscountModel saved = discountRepo.save(d);
        return toDto(saved);
    }

    @Transactional(readOnly = true)
    public List<AdminPlanDiscountReadDTO> listByPlanPrice(UUID planPriceId) {
        return discountRepo.findAllByPlanPrice_IdOrderByPriorityDescIdDesc(planPriceId)
                .stream().map(this::toDto).toList();
    }

    @Transactional
    public AdminPlanDiscountReadDTO setActive(UUID id, boolean active) {
        @SuppressWarnings("null")
        PlanDiscountModel d = discountRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Discount no encontrado: " + id));
        d.setActive(active);
        return toDto(d);
    }

    private AdminPlanDiscountReadDTO toDto(PlanDiscountModel d) {
        return new AdminPlanDiscountReadDTO(
                d.getId(),
                d.getPlanPrice().getId(),
                d.getName(),
                d.getPublicCode(),
                d.getStripePromotionCodeId(),
                d.getStripeCouponId(),
                d.getDiscountType(),
                d.getPercentOff(),
                d.getAmountOff(),
                d.getStartsAt(),
                d.getEndsAt(),
                d.getMaxRedemptions(),
                d.isNewCustomersOnly(),
                d.getPriority(),
                d.isStackable(),
                d.isActive(),
                d.getCreatedAt());
    }

    private static boolean notBlank(String s) {
        return s != null && !s.isBlank();
    }

    private static String trimOrNull(String s) {
        return notBlank(s) ? s.trim() : null;
    }

    private static String normalizeNullable(String s) {
        if (s == null)
            return null;
        String t = s.trim();
        return t.isBlank() ? null : t.toUpperCase();
    }
}