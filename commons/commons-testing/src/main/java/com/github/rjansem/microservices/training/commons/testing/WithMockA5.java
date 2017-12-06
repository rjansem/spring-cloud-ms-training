package com.github.rjansem.microservices.training.commons.testing;

import com.github.rjansem.microservices.training.apisecurity.Authority;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Simule le contexte de sécurité de l'utilisateur A5
 *
 * @author rjansem
 * @see WithMockAuthenticatedUser
 */
@Retention(RetentionPolicy.RUNTIME)
@WithMockAuthenticatedUser(login = UserConstants.A5, racines = {"A5R1", "A5R2"},
        authorities = {Authority.TRANSACTION, Authority.CONSULTATION})
public @interface WithMockA5 {

}
