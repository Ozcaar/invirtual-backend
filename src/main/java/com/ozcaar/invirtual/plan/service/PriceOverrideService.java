package com.ozcaar.invirtual.plan.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ozcaar.invirtual.common.exception.global.AlreadyExistsException;
import com.ozcaar.invirtual.common.exception.global.NotFoundException;
import com.ozcaar.invirtual.plan.dto.create.AdminPriceOverrideCreateDTO;
import com.ozcaar.invirtual.plan.dto.read.AdminPriceOverrideReadDTO;
import com.ozcaar.invirtual.plan.model.PlanPriceModel;
import com.ozcaar.invirtual.plan.model.PlanPriceOverrideModel;
import com.ozcaar.invirtual.plan.repository.PlanPriceOverrideRepository;
import com.ozcaar.invirtual.plan.repository.PlanPriceRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PriceOverrideService {

    private final PlanPriceRepository planPriceRepo;
    private final PlanPriceOverrideRepository overrideRepo;

    @SuppressWarnings("null")
    @Transactional
    public AdminPriceOverrideReadDTO create(AdminPriceOverrideCreateDTO req) {

        // 1) PlanPrice must exist
        PlanPriceModel planPrice = planPriceRepo.findById(req.planPriceId())
                .orElseThrow(() -> new NotFoundException("PlanPrice no encontrado: " + req.planPriceId()));

        // 2) stripePriceIdOverride must be unique
        String stripeOverride = req.stripePriceIdOverride().trim();

        // 3) Validation: endsAt must be after startsAt (if both provided)
        if (req.startsAt() != null && req.endsAt() != null && !req.endsAt().isAfter(req.startsAt())) {
            throw new AlreadyExistsException("endsAt debe ser después de startsAt.");
        }
        if (overrideRepo.existsByStripePriceIdOverride(stripeOverride)) {
            throw new AlreadyExistsException("stripePriceIdOverride ya existe: " + stripeOverride);
        }

        // 4) If this is active, make sure to deactivate other active overrides for the
        // same planPrice
        if (req.active() != null && req.active()) {
            List<PlanPriceOverrideModel> others = overrideRepo
                    .findAllByPlanPrice_IdAndActiveTrueOrderByPriorityDescIdDesc(
                            planPrice.getId());
            for (PlanPriceOverrideModel other : others) {
                other.setActive(false);
            }
            overrideRepo.saveAll(others);
        }

        // 5) Create the override
        PlanPriceOverrideModel o = new PlanPriceOverrideModel();
        o.setPlanPrice(planPrice);
        o.setName(req.name().trim());
        o.setStripePriceIdOverride(stripeOverride);
        o.setStartsAt(req.startsAt());
        o.setEndsAt(req.endsAt());
        o.setPriority(req.priority() == null ? 0 : req.priority());
        o.setActive(req.active() == null ? true : req.active());

        PlanPriceOverrideModel saved = overrideRepo.save(o);
        return toDto(saved);
    }

    @Transactional(readOnly = true)
    public List<AdminPriceOverrideReadDTO> listByPlanPrice(UUID planPriceId) {
        return overrideRepo.findAllByPlanPrice_IdOrderByPriorityDescIdDesc(planPriceId)
                .stream().map(this::toDto).toList();
    }

    @SuppressWarnings("null")
    @Transactional
    public AdminPriceOverrideReadDTO setActive(UUID id, boolean active) {
        PlanPriceOverrideModel o = overrideRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Override no encontrado: " + id));

        // If you're activating this one, deactivate the other active ones in the same
        // planPrice group
        if (active) {
            List<PlanPriceOverrideModel> others = overrideRepo
                    .findAllByPlanPrice_IdAndActiveTrueOrderByPriorityDescIdDesc(
                            o.getPlanPrice().getId());
            for (PlanPriceOverrideModel other : others) {
                if (!other.getId().equals(o.getId())) {
                    other.setActive(false);
                }
            }
            overrideRepo.saveAll(others);
        }

        o.setActive(active);
        overrideRepo.save(o);
        return toDto(o);
    }

    private AdminPriceOverrideReadDTO toDto(PlanPriceOverrideModel o) {
        return new AdminPriceOverrideReadDTO(
                o.getId(),
                o.getPlanPrice().getId(),
                o.getName(),
                o.getStripePriceIdOverride(),
                o.getStartsAt(),
                o.getEndsAt(),
                o.getPriority(),
                o.isActive(),
                o.getCreatedAt());
    }
}