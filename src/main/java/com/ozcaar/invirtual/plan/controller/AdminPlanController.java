package com.ozcaar.invirtual.plan.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import com.ozcaar.invirtual.plan.dto.create.AdminPlanCreateDTO;
import com.ozcaar.invirtual.plan.dto.create.AdminPlanPriceCreateDTO;
import com.ozcaar.invirtual.plan.dto.read.AdminPlanPriceReadDTO;
import com.ozcaar.invirtual.plan.dto.read.AdminPlanReadDTO;
import com.ozcaar.invirtual.plan.dto.update.AdminPlanPriceUpdateDTO;

import java.util.List;
import java.util.UUID;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.ozcaar.invirtual.plan.service.PlanService;
import com.ozcaar.invirtual.plan.service.PlanPriceService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequiredArgsConstructor
@Tag(name = "Admin Plan Controller", description = "operaciones de administrador con planes")
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/api/admin/plan")
public class AdminPlanController {

    private final PlanService planService;
    private final PlanPriceService planPriceService;

    // ============================= //

    // PLAN

    // CREATE PLAN
    @PostMapping
    public AdminPlanReadDTO createPlan(@Valid @RequestBody AdminPlanCreateDTO request) {
        return planService.createPlan(request);
    }

    // GET PLANS (FOR ADMIN)
    @GetMapping("/list")
    public List<AdminPlanReadDTO> getPlansAsAdmin() {
        return planService.getPlansAsAdmin();
    }

    // DELETE PLAN (SOFT DELETE)
    @DeleteMapping("/{code}")
    public AdminPlanReadDTO deactivatePlan(@PathVariable String code) {
        return planService.deactivatePlan(code);
    }

    // ============================= //

    // PLAN PRICE

    // CREATE PLAN PRICE (map intor to a stripe_price_id)
    @PostMapping("/price")
    public AdminPlanPriceReadDTO createPlanPrice(@Valid @RequestBody AdminPlanPriceCreateDTO req) {
        return planPriceService.createPlanPrice(req);
    }

    // GET PLAN PRICES BY PLAN ex: /api/admin/plan-prices?planCode=BASIC
    @GetMapping("/price/list")
    public List<AdminPlanPriceReadDTO> listPlanPrices(@RequestParam String planCode) {
        return planPriceService.listByPlanPrice(planCode);
    }

    // PATCH /api/admin/plan-price/{id}/active
    // If active=true, set active=false to other PlanPrice with same
    // (plan/env/currency/billingType)
    @PatchMapping("/price/{id}/active")
    public AdminPlanPriceReadDTO setActivePlanPrice(@PathVariable UUID id,
            @Valid @RequestBody AdminPlanPriceUpdateDTO req) {
        return planPriceService.setActivePlanPrice(id, req.active());
    }

}