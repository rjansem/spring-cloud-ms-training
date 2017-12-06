package com.github.rjansem.microservices.training.commons.testing;

import com.github.rjansem.microservices.training.apisecurity.Authority;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Simule le contexte de sécurité de l'utilisateur A1
 *
 * @author rjansem
 * @see WithMockAuthenticatedUser
 */
@Retention(RetentionPolicy.RUNTIME)
@WithMockAuthenticatedUser(login = UserConstants.A1, racines = {"A1R1"}, authorities = {Authority.TRANSACTION, Authority.CONSULTATION})
public @interface WithMockA1 {

}
