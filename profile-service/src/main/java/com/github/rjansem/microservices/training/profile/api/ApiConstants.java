package com.github.rjansem.microservices.training.profile.api;


import com.github.rjansem.microservices.training.apisecurity.ServicesUris;

/**
 * Constantes liées aux URIs
 *
 * @author jntakpe
 */
final class ApiConstants {

    static final String TOKEN = ServicesUris.API + "/token";

    static final String CLIENT = ServicesUris.API + "/clients";

    static final String RETRIEVE_CALENDAR = ServicesUris.API + "/calendar";

    static final String RACINE_BY_ID = "/api/racines/{idRacine}";

}
