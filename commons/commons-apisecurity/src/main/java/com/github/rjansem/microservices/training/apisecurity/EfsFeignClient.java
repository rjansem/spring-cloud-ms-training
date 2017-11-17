package com.github.rjansem.microservices.training.apisecurity;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Meta annotation de {@link org.springframework.cloud.netflix.feign.FeignClient} permettant de communiquer avec EFS
 *
 * @author jntakpe
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RUNTIME)
@FeignClient(name = ServicesUris.EFS_SERVICE, url = ServicesUris.EFS_DYNAMIC_URL)
public @interface EfsFeignClient {

    @AliasFor(annotation = FeignClient.class, attribute = "fallback")
    Class<?> fallback() default void.class;

}
