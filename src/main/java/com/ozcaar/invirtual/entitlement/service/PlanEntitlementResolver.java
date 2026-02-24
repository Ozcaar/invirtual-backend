package com.ozcaar.invirtual.entitlement.service;

import org.springframework.stereotype.Service;

import com.ozcaar.invirtual.common.exception.global.NotFoundException;
import com.ozcaar.invirtual.entitlement.service.EntitlementService.EntitlementSnapshot;
import com.ozcaar.invirtual.plan.model.PlanModel;
import com.ozcaar.invirtual.plan.repository.PlanPriceOverrideRepository;
import com.ozcaar.invirtual.plan.repository.PlanPriceRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PlanEntitlementResolver {

    private final PlanPriceRepository planPriceRepository;
    private final PlanPriceOverrideRepository overrideRepository;

    public EntitlementSnapshot resolveByStripePriceId(String stripePriceId) {

        // Is it a base price?
        var baseOpt = planPriceRepository.findByStripePriceId(stripePriceId);
        if (baseOpt.isPresent()) {
            PlanModel plan = baseOpt.get().getPlan();
            return new EntitlementSnapshot(plan.getMaxPeople(), plan.getDurationDays());
        }

        // Is it an override?
        var overrideOpt = overrideRepository.findByStripePriceIdOverride(stripePriceId);
        if (overrideOpt.isPresent()) {
            PlanModel plan = overrideOpt.get().getPlanPrice().getPlan();
            return new EntitlementSnapshot(plan.getMaxPeople(), plan.getDurationDays());
        }

        throw new NotFoundException("stripePriceId no reconocido: " + stripePriceId);
    }
}