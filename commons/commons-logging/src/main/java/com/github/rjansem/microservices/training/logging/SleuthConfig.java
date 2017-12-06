package com.github.rjansem.microservices.training.logging;

import org.springframework.cloud.sleuth.Sampler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration de Sleuth
 *
 * @author rjansem
 */
@Configuration
public class SleuthConfig {

    private static final String METRICS_SUFFIX = "-metrics";

    @Bean
    public Sampler ignoreMetricsSampler() {
        return span -> !span.getName().contains(METRICS_SUFFIX);
    }

}
