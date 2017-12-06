package com.github.rjansem.microservices.training.profile.client;

/**
 * Constantes d'URIs pour les appels aux clients
 *
 * @author rjansem
 */
final class ClientConstants {

    public static final String PREFIXE_EFS = "/webank/v1.0";

    static final String RACINE = PREFIXE_EFS + "/racines";

    static final String SERVICE = PREFIXE_EFS + "/services";

    static final String ACCOUNT_BY_RACINE = "/api/{userId}/clients/{clientTechnicalId}/accounts";

    static final String USER_ID_PATHVAR = "userId";

    static final String CLIENTS_TECHNICAL_ID_PATHVAR = "clientTechnicalId";

    static final String LOGIN_PARAM = "loginwebabonne";

    static final String ABONNE = PREFIXE_EFS + "/abonnes/web";
}