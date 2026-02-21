package com.ozcaar.invirtual.plan.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ozcaar.invirtual.common.exception.global.AlreadyExistsException;
import com.ozcaar.invirtual.common.exception.global.NotFoundException;
import com.ozcaar.invirtual.plan.dto.create.AdminPlanCreateDTO;
import com.ozcaar.invirtual.plan.dto.read.AdminPlanReadDTO;
import com.ozcaar.invirtual.plan.dto.read.PlanReadDTO;
import com.ozcaar.invirtual.plan.model.PlanModel;
import com.ozcaar.invirtual.plan.repository.PlanRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PlanService {

    private final PlanRepository planRepository;

    @Transactional
    public AdminPlanReadDTO createPlan(AdminPlanCreateDTO req) {

        String code = req.code().name().toUpperCase();

        if (planRepository.findByCodeAndActiveTrue(code).isPresent()) {
            throw new AlreadyExistsException("Ya existe un plan activo con code " + code);
        }

        PlanModel plan = new PlanModel();
        plan.setCode(code);
        plan.setName(req.name().trim());
        plan.setDescription(req.description());
        plan.setFeatures(req.features());
        plan.setPosition(req.position());
        plan.setActive(true);

        PlanModel saved = planRepository.save(plan);

        return toDto(saved);
    }

    @Transactional
    public AdminPlanReadDTO deactivatePlan(String code) {
        PlanModel plan = planRepository.findByCodeAndActiveTrue(code.toUpperCase())
                .orElseThrow(() -> new NotFoundException("Plan no encontrado: " + code));

        plan.setActive(false);
        return toDto(plan);
    }

    public List<PlanReadDTO> getActivePlans() {
        return planRepository.findAllByActiveTrueOrderByPositionAscIdAsc()
                .stream()
                .map(p -> new PlanReadDTO(p.getCode(), p.getName(), p.getDescription(), p.getFeatures()))
                .toList();
    }

    // TODO: esto en otros métodos ya lo hace mapper, hay que unificar
    private AdminPlanReadDTO toDto(PlanModel plan) {
        return new AdminPlanReadDTO(
                plan.getId(),
                plan.getCode(),
                plan.getName(),
                plan.getDescription(),
                plan.getFeatures(),
                plan.isActive(),
                plan.getPosition(),
                plan.getCreatedAt());
    }
}