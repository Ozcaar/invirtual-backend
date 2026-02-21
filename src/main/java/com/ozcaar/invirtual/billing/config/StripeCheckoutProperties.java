package com.ozcaar.invirtual.billing.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.ozcaar.invirtual.plan.enums.EnvironmentEnum;

@ConfigurationProperties(prefix = "stripe.checkout")
public class StripeCheckoutProperties {

    private String secretKey;
    private String successUrl;
    private String cancelUrl;
    private EnvironmentEnum environment = EnvironmentEnum.TEST;
    private String defaultCurrency = "MXN";

    // Getters and Setters

    public String getSecretKey() {
        return secretKey;
    }
    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }
    public String getSuccessUrl() {
        return successUrl;
    }
    public void setSuccessUrl(String successUrl) {
        this.successUrl = successUrl;
    }
    public String getCancelUrl() {
        return cancelUrl;
    }
    public void setCancelUrl(String cancelUrl) {
        this.cancelUrl = cancelUrl;
    }
    public EnvironmentEnum getEnvironment() {
        return environment;
    }
    public void setEnvironment(EnvironmentEnum environment) {
        this.environment = environment;
    }
    public String getDefaultCurrency() {
        return defaultCurrency;
    }
    public void setDefaultCurrency(String defaultCurrency) {
        this.defaultCurrency = defaultCurrency;
    }

}
