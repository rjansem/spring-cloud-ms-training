package com.github.rjansem.microservices.training.apisecurity.config;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.github.rjansem.microservices.training.apisecurity.config.deserializer.BigDecimalMoneySerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

import java.math.BigDecimal;

/**
 * Configuration de Spring MVC
 *
 * @author jntakpe
 */
@Configuration
public class WebConfig {

    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        return new MethodValidationPostProcessor();
    }

    @Bean
    public Module amountModule() {
        return new SimpleModule("amount", Version.unknownVersion()).addSerializer(BigDecimal.class, new BigDecimalMoneySerializer());
    }
}
