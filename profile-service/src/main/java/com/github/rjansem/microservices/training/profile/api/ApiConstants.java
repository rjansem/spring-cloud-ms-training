package com.github.rjansem.microservices.training.profile.api;


import com.github.rjansem.microservices.training.apisecurity.ServicesUris;

/**
 * Constantes liées aux URIs
 *
 * @author rjansem
 */
final class ApiConstants {

    private static final String USER = ServicesUris.API + "/{userId}";

    static final String TOKEN = ServicesUris.API + "/token";

    static final String CLIENT = USER + "/clients";

    static final String RACINE_BY_ID = "/api/racines/{idRacine}";

}
