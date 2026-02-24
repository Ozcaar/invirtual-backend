package com.ozcaar.invirtual.plan.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ozcaar.invirtual.billing.enums.BillingTypeEnum;
import com.ozcaar.invirtual.plan.enums.EnvironmentEnum;
import com.ozcaar.invirtual.plan.model.PlanPriceModel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PlanPriceRepository extends JpaRepository<PlanPriceModel, UUID> {

	Optional<PlanPriceModel> findFirstByPlan_CodeAndPlan_ActiveTrueAndActiveTrueAndEnvironmentAndCurrencyAndBillingType(
			String planCode,
			EnvironmentEnum environment,
			String currency,
			BillingTypeEnum billingType);

	Optional<PlanPriceModel> findByStripePriceId(String stripePriceId);

	List<PlanPriceModel> findAllByPlan_CodeOrderByEnvironmentAscCurrencyAscBillingTypeAscCreatedAtDesc(String planCode);

	boolean existsByPlan_CodeAndEnvironmentAndCurrencyAndBillingTypeAndActiveTrue(
			String planCode, EnvironmentEnum environment, String currency, BillingTypeEnum billingType);

	List<PlanPriceModel> findAllByPlan_CodeAndEnvironmentAndCurrencyAndBillingTypeAndActiveTrue(
			String planCode, EnvironmentEnum environment, String currency, BillingTypeEnum billingType);
}
