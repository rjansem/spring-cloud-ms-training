package com.github.rjansem.microservices.training.apisecurity.utils;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Indique qu'il faut créer un contexte de sécurité avec des racines pour la méthode annotée
 *
 * @author jntakpe
 */
@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockRacinesContextFactory.class)
public @interface WithMockRacines {

    String[] value() default {};

}
