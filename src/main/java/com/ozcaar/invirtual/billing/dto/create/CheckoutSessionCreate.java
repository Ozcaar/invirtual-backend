package com.ozcaar.invirtual.billing.dto.create;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CheckoutSessionCreate {
    
    @NotNull(message = "El tipo de plan es requerido.")
    @NotBlank(message = "El tipo de plan no puede estar en blanco.")
    private String plan;

    // Getters & setters

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }
}
