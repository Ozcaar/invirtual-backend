package com.ozcaar.invirtual.billing.controller;

import java.util.Map;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ozcaar.invirtual.billing.service.BillingService;
import com.ozcaar.invirtual.plan.dto.create.CheckoutSessionCreateDTO;
import com.ozcaar.invirtual.plan.dto.read.CheckoutSessionReadDTO;
import com.ozcaar.invirtual.plan.service.StripeCheckoutService;
import com.stripe.model.checkout.Session;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Tag(name = "Billing Controller", description = "operaciones con facturación")
@RequestMapping("/api/billing")
public class BillingController {

    private final StripeCheckoutService stripeCheckoutService;

    // @PostMapping("/checkout-session")
    // public Map<String, String> createCheckoutSession(@AuthenticationPrincipal Jwt
    // jwt,
    // @RequestBody CheckoutSessionCreate req) throws Exception {

    // String supabaseId = jwt.getSubject();
    // String url = billingService.createCheckoutSession(supabaseId, req);

    // return Map.of("url", url);
    // }

    @PostMapping("/checkout/session")
    public CheckoutSessionReadDTO createCheckoutSession(@AuthenticationPrincipal Jwt jwt,
            @RequestBody CheckoutSessionCreateDTO req) {
        String supabaseId = jwt.getSubject();
        Session session = stripeCheckoutService.createCheckoutSession(
                req.planCode(),
                req.currency(),
                req.promoCode(),
                supabaseId);

        return new CheckoutSessionReadDTO(session.getId(), session.getUrl());
    }

}
