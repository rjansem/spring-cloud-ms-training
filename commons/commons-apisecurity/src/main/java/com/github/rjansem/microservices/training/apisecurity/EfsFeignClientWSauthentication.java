package com.github.rjansem.microservices.training.apisecurity;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Meta annotation de {@link FeignClient} permettant de communiquer avec EFS
 *
 * @author mbouhamyd
 * //FIXME [Code review Jocelyn] : A supprimer utiliser changer la variable ${efs.gateway.url}
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RUNTIME)
@FeignClient(name = ServicesUris.EFS_SERVICE_wsauthentication, url = ServicesUris.EFS_DYNAMIC_URL_wsauthentication)
public @interface EfsFeignClientWSauthentication {

    @AliasFor(annotation = FeignClient.class, attribute = "fallback")
    Class<?> fallback() default void.class;

}
