package com.ozcaar.invirtual.billing.controller;

import java.util.Map;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ozcaar.invirtual.billing.dto.create.CheckoutSessionCreate;
import com.ozcaar.invirtual.billing.service.BillingService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/billing")
public class BillingController {

    private final BillingService billingService;

    @PostMapping("/checkout-session")
    public Map<String, String> createCheckoutSession(@AuthenticationPrincipal Jwt jwt,
            @RequestBody CheckoutSessionCreate req) throws Exception {

        String supabaseId = jwt.getSubject();
        String url = billingService.createCheckoutSession(supabaseId, req);

        return Map.of("url", url);
    }

}
