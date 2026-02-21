package com.ozcaar.invirtual.plan.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ozcaar.invirtual.plan.model.PlanPriceOverrideModel;

import java.util.List;
import java.util.UUID;

public interface PlanPriceOverrideRepository extends JpaRepository<PlanPriceOverrideModel, UUID> {

    // Get active overrides by plan_price to then filter by date in service
    List<PlanPriceOverrideModel> findAllByPlanPrice_IdAndActiveTrueOrderByPriorityDescIdDesc(UUID planPriceId);

    // Validation
    boolean existsByStripePriceIdOverride(String stripePriceIdOverride);
}
