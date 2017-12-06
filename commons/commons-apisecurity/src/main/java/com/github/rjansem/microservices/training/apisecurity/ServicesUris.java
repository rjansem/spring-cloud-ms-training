package com.github.rjansem.microservices.training.apisecurity;

/**
 * Constantes d'URIs relatives aux échanges avec les microservices
 *
 * @author rjansem
 */
public final class ServicesUris {

    public static final String API = "/api";

    static final String ACCOUNT_SERVICE = "account-service-${services.account-service.version}";

    static final String ACCOUNT_SERVICE_DYNAMIC_TEST_URL = "${account-service.test.url:}";

    static final String PROFILE_SERVICE = "profile-service-${services.profile-service.version}";

    static final String PROFILE_SERVICE_DYNAMIC_TEST_URL = "${profile-service.test.url:}";

    static final String EFS_SERVICE = "efs-service";

    static final String EFS_DYNAMIC_URL = "${efs.gateway.url}";

    static final String EFS_SERVICE_wsauthentication = "efs-service-wsauthentication";

    static final String EFS_DYNAMIC_URL_wsauthentication = "${efswsauthentication.gateway.url}";

}
