package com.ozcaar.invirtual.plan.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ozcaar.invirtual.plan.dto.create.AdminPlanDiscountCreateDTO;
import com.ozcaar.invirtual.plan.dto.read.AdminPlanDiscountReadDTO;
import com.ozcaar.invirtual.plan.dto.update.AdminPlanDiscountUpdateDTO;
import com.ozcaar.invirtual.plan.service.PlanDiscountService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@Tag(name = "Admin Plan Discount", description = "manejar descuentos de planes")
@RequestMapping("/api/admin/plan-discounts")
public class AdminPlanDiscountController {

    private final PlanDiscountService service;

    @PostMapping
    public AdminPlanDiscountReadDTO create(@Valid @RequestBody AdminPlanDiscountCreateDTO req) {
        return service.create(req);
    }

    @GetMapping
    public List<AdminPlanDiscountReadDTO> list(@RequestParam UUID planPriceId) {
        return service.listByPlanPrice(planPriceId);
    }

    @PatchMapping("/{id}/active")
    public AdminPlanDiscountReadDTO setActive(@PathVariable UUID id, @Valid @RequestBody AdminPlanDiscountUpdateDTO req) {
        return service.setActive(id, req.active());
    }
}