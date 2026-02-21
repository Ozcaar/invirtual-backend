package com.ozcaar.invirtual.plan.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ozcaar.invirtual.plan.model.PlanDiscountModel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PlanDiscountRepository extends JpaRepository<PlanDiscountModel, UUID> {

    Optional<PlanDiscountModel> findFirstByPlanPrice_IdAndActiveTrueAndPublicCodeIgnoreCase(UUID planPriceId,
            String publicCode);

    // Auto-apply discounts (without code) by priority
    List<PlanDiscountModel> findAllByPlanPrice_IdAndActiveTrueAndPublicCodeIsNullOrderByPriorityDescIdDesc(
            UUID planPriceId);
}
