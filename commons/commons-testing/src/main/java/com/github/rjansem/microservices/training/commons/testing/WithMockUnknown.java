package com.github.rjansem.microservices.training.commons.testing;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Simule le contexte de sécurité d'un utilisateur inconnu
 *
 * @author jntakpe
 * @see WithMockAuthenticatedUser
 */
@Retention(RetentionPolicy.RUNTIME)
@WithMockAuthenticatedUser(login = UserConstants.UNKNOWN, racines = UserConstants.UNKNOWN_RACINE)
public @interface WithMockUnknown {

}
