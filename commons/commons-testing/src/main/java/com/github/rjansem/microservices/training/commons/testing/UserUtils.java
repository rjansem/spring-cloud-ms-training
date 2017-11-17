package com.github.rjansem.microservices.training.commons.testing;

import com.github.rjansem.microservices.training.apisecurity.AuthenticatedUser;

import java.util.Collections;

/**
 * Utilitaires de tests pour les utilisateurs
 *
 * @author jntakpe
 */
public class UserUtils {

    public static final AuthenticatedUser UNKNOWN_USER = new AuthenticatedUser(null, UserConstants.UNKNOWN, null, null);

    public static final AuthenticatedUser NO_RIB = new AuthenticatedUser(null, "NORIB", Collections.singletonList("NORIBR1"), null);

    public static final AuthenticatedUser NO_X_USER = new AuthenticatedUser(null, "NO", null, null);

    public static final AuthenticatedUser SINGLE_ROLE_USER = new AuthenticatedUser(null, "SINGLE", null, null);

    public static final AuthenticatedUser A1_USER = new AuthenticatedUser(null, UserConstants.A1,
            Collections.singletonList("A1R1"), null);

    public static final AuthenticatedUser A2_USER = new AuthenticatedUser(null, UserConstants.A2,
            Collections.singletonList("A2R1"), null);

    public static final AuthenticatedUser A3_USER = new AuthenticatedUser(null, UserConstants.A3,
            Collections.singletonList("A3R1"), null);

    public static final AuthenticatedUser A4_USER = new AuthenticatedUser(null, UserConstants.A4,
            Collections.singletonList("A4R1"), null);

    public static final AuthenticatedUser A5_USER = new AuthenticatedUser(null, UserConstants.A5, Collections.singletonList("A5R1"), null);

    public static final AuthenticatedUser COUPLE_RACINE_USER = A5_USER;

    public static final AuthenticatedUser SINGLE_RACINE_USER = A1_USER;

    public static final AuthenticatedUser COUPLE_ROLE_USER = A5_USER;

    public static final AuthenticatedUser A6_USER = new AuthenticatedUser(null, "A6",
            Collections.singletonList("8800001"), null);

    public static final AuthenticatedUser UNKNOWN_EXCEPTION = new AuthenticatedUser(null, "UNKNOWN", Collections.singletonList("UNKNOWN"), null);

}