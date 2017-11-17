package com.github.rjansem.microservices.training.apisecurity;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Meta annotation de {@link FeignClient} permettant de communiquer avec le microservice account-service
 *
 * @author jntakpe
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RUNTIME)
@FeignClient(name = ServicesUris.ACCOUNT_SERVICE, url = ServicesUris.ACCOUNT_SERVICE_DYNAMIC_TEST_URL)
public @interface AccountFeignClient {

    @AliasFor(annotation = FeignClient.class, attribute = "fallback")
    Class<?> fallback() default void.class;

}
