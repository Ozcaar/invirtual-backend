package com.ozcaar.invirtual.billing.config;

import com.stripe.Stripe;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(StripeCheckoutProperties.class)
public class StripeConfig {

    private final StripeCheckoutProperties props;

    public StripeConfig(StripeCheckoutProperties props) {
        this.props = props;
    }

    @PostConstruct
    public void initStripe() {
        Stripe.apiKey = props.getSecretKey();
    }
}
