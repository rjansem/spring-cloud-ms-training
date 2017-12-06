package com.github.rjansem.microservices.training.commons.testing;

import com.github.rjansem.microservices.training.apisecurity.Authority;
import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Indique qu'il faut créer un contexte de sécurité pour la méthode annotée
 *
 * @author rjansem
 */
@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockAuthenticatedUserContextFactory.class)
public @interface WithMockAuthenticatedUser {

    String value() default "";

    String login() default "";

    String[] racines() default {};

    Authority[] authorities() default {};

}
