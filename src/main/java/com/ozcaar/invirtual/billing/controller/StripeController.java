package com.ozcaar.invirtual.billing.controller;

import java.nio.charset.StandardCharsets;

import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ozcaar.invirtual.billing.service.StripeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "Stripe Controller", description = "operaciones con stripe")
@RequestMapping("/api/public/stripe")
public class StripeController {

    private final StripeService stripeWebhookService;

    @PostMapping("/webhook")
    public ResponseEntity<String> stripeWebhook(
            HttpServletRequest request,
            @RequestHeader(value = "Stripe-Signature", required = false) String sigHeader) throws Exception {

        byte[] bodyBytes = StreamUtils.copyToByteArray(request.getInputStream());
        if (bodyBytes == null || bodyBytes.length == 0) {
            return ResponseEntity.badRequest().body("Empty body");
        }
        if (sigHeader == null || sigHeader.isBlank()) {
            return ResponseEntity.badRequest().body("Missing Stripe-Signature");
        }

        String payload = new String(bodyBytes, StandardCharsets.UTF_8);

        stripeWebhookService.handleStripeWebhook(payload, sigHeader);
        return ResponseEntity.ok("ok");
    }
}