package com.ozcaar.invirtual.plan.controller;

import com.ozcaar.invirtual.plan.dto.create.CheckoutSessionCreateDTO;
import com.ozcaar.invirtual.plan.dto.read.CheckoutSessionReadDTO;
import com.ozcaar.invirtual.plan.dto.read.PlanReadDTO;
import com.ozcaar.invirtual.plan.service.PlanService;
import com.ozcaar.invirtual.plan.service.StripeCheckoutService;
import com.stripe.model.checkout.Session;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Plan Controller", description = "operaciones con planes")
@RequestMapping("/api/plan")
public class PlanController {

    private final PlanService planService;
    private final StripeCheckoutService stripeCheckoutService;

    @GetMapping("/list")
    public List<PlanReadDTO> getPlans() {
        return planService.getActivePlans();
    }

}
