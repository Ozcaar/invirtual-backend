package com.ozcaar.invirtual.plan.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ozcaar.invirtual.common.exception.global.AlreadyExistsException;
import com.ozcaar.invirtual.common.exception.global.NotFoundException;
import com.ozcaar.invirtual.plan.dto.create.AdminPlanPriceCreateDTO;
import com.ozcaar.invirtual.plan.dto.read.AdminPlanPriceReadDTO;
import com.ozcaar.invirtual.plan.model.PlanModel;
import com.ozcaar.invirtual.plan.model.PlanPriceModel;
import com.ozcaar.invirtual.plan.repository.PlanPriceRepository;
import com.ozcaar.invirtual.plan.repository.PlanRepository;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class PlanPriceService {

    private final PlanRepository planRepository;
    private final PlanPriceRepository planPriceRepository;

    @Transactional
    public AdminPlanPriceReadDTO createPlanPrice(AdminPlanPriceCreateDTO req) {

        String planCode = req.planCode().trim().toUpperCase();
        // String currency = req.currency().trim().toUpperCase();
        String currency = "MXN";
        String stripePriceId = req.stripePriceId().trim();

        // 1) Plan must exist and be active
        PlanModel plan = planRepository.findByCodeAndActiveTrue(planCode)
                .orElseThrow(
                        () -> new NotFoundException("Plan no encontrado o inactivo: " + planCode));

        // 2) stripe_price_id must be unique
        planPriceRepository.findByStripePriceId(stripePriceId).ifPresent(x -> {
            throw new AlreadyExistsException("stripePriceId ya está registrado: " + stripePriceId);
        });

        // 3) Rule: only 1 active per (plan, env, currency, billingType)
        boolean active = (req.active() == null) ? true : req.active();

        if (active && planPriceRepository.existsByPlan_CodeAndEnvironmentAndCurrencyAndBillingTypeAndActiveTrue(
                planCode, req.environment(), currency, req.billingType())) {
            throw new AlreadyExistsException(
                    "Ya existe un PlanPrice ACTIVO para " + planCode + " (" + req.environment() + ", " + currency + ", "
                            + req.billingType() + ").");
        }

        PlanPriceModel pp = new PlanPriceModel();
        pp.setPlan(plan);
        pp.setEnvironment(req.environment());
        pp.setCurrency(currency);
        pp.setBillingType(req.billingType());
        pp.setStripePriceId(stripePriceId);
        pp.setActive(active);

        PlanPriceModel saved = planPriceRepository.save(pp);
        return toDto(saved);
    }

    @Transactional(readOnly = true)
    public List<AdminPlanPriceReadDTO> listByPlanPrice(String planCode) {
        return planPriceRepository
                .findAllByPlan_CodeOrderByEnvironmentAscCurrencyAscBillingTypeAscCreatedAtDesc(
                        planCode.trim().toUpperCase())
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Transactional
    public AdminPlanPriceReadDTO setActivePlanPrice(UUID planPriceId, boolean active) {
        @SuppressWarnings("null")
        PlanPriceModel pp = planPriceRepository.findById(planPriceId)
                .orElseThrow(() -> new NotFoundException("PlanPrice no encontrado: " + planPriceId));

        String planCode = pp.getPlan().getCode();
        String currency = pp.getCurrency();

        if (active) {
            // If you're activating this one, deactivate the other active ones in the same
            // group
            List<PlanPriceModel> others = planPriceRepository
                    .findAllByPlan_CodeAndEnvironmentAndCurrencyAndBillingTypeAndActiveTrue(
                            planCode, pp.getEnvironment(), currency, pp.getBillingType());

            for (PlanPriceModel other : others) {
                if (!other.getId().equals(pp.getId())) {
                    other.setActive(false);
                }
            }
        }

        pp.setActive(active);
        return toDto(pp);
    }

    private AdminPlanPriceReadDTO toDto(PlanPriceModel pp) {
        return new AdminPlanPriceReadDTO(
                pp.getId(),
                pp.getPlan().getCode(),
                pp.getEnvironment(),
                pp.getCurrency(),
                pp.getBillingType(),
                pp.getStripePriceId(),
                pp.isActive(),
                pp.getCreatedAt());
    }
}