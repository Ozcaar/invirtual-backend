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
import org.springframework.security.access.prepost.PreAuthorize;

import com.ozcaar.invirtual.plan.dto.create.AdminPriceOverrideCreateDTO;
import com.ozcaar.invirtual.plan.dto.read.AdminPriceOverrideReadDTO;
import com.ozcaar.invirtual.plan.dto.update.AdminPriceOverrideUpdateDTO;
import com.ozcaar.invirtual.plan.service.PriceOverrideService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/api/admin/plan-price-overrides")
@Tag(name = "Admin Plan Price Override", description = "manejar precios temporales para temporadas, etc.")
public class AdminPlanPriceOverrideController {

    private final PriceOverrideService service;

    @PostMapping
    public AdminPriceOverrideReadDTO create(@Valid @RequestBody AdminPriceOverrideCreateDTO req) {
        return service.create(req);
    }

    @GetMapping
    public List<AdminPriceOverrideReadDTO> list(@RequestParam UUID planPriceId) {
        return service.listByPlanPrice(planPriceId);
    }

    @PatchMapping("/{id}/active")
    public AdminPriceOverrideReadDTO setActive(@PathVariable UUID id,
            @Valid @RequestBody AdminPriceOverrideUpdateDTO req) {
        return service.setActive(id, req.active());
    }
}
