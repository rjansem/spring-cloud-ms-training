package com.github.rjansem.microservices.training.gateway.config;

import com.github.rjansem.microservices.training.gateway.filter.SSLHostValidationRoutingFilter;
import org.springframework.cloud.netflix.zuul.ZuulProxyConfiguration;
import org.springframework.cloud.netflix.zuul.filters.ProxyRequestHelper;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(ZuulProxyConfiguration.class)
public class FilterConfiguration {

    @Bean
    public SSLHostValidationRoutingFilter simpleHostRoutingFilter(ProxyRequestHelper helper, ZuulProperties zuulProperties) {
        return new SSLHostValidationRoutingFilter(helper, zuulProperties);
    }
}
