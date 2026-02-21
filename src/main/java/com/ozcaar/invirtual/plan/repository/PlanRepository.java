package com.ozcaar.invirtual.plan.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ozcaar.invirtual.plan.model.PlanModel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PlanRepository extends JpaRepository<PlanModel, UUID> {
    Optional<PlanModel> findByCodeAndActiveTrue(String code);

    List<PlanModel> findAllByActiveTrueOrderByPositionAscIdAsc();
}