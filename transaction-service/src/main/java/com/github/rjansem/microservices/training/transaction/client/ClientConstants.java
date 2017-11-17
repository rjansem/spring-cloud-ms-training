package com.github.rjansem.microservices.training.transaction.client;

/**
 * Constantes d'URIs pour les appels aux clients
 *
 * @author azzerrifi
 * @author mbouhamyd
 */
final class ClientConstants {

    public static final String PREFEXIQUE_EFS = "/webank/v1.0";

    static final String RACINE = "/racines";

    static final String SERVICE = "/services";

    static final String ACCOUNT_BY_RACINE = "/api/clients/{clientTechnicalId}/accounts";

    static final String CLIENTS_TECHNICAL_ID_PATHVAR = "clientTechnicalId";

    static final String LOGIN_PARAM = "loginwebabonne";

    static final String CODE_APPLICATION = "codeapplication";

    static final String CODES_APPLICATIONS = "codesapplications";

    static final String COMPTES_EMETTEUR = PREFEXIQUE_EFS + "/transactions/comptesEmetteurs";

    static final String COMPTES_BENEF = PREFEXIQUE_EFS + "/transactions/beneficiaires";

    static final String TRANSACTIONS = PREFEXIQUE_EFS + "/transactions";

    static final String MOYENS_SIGN = PREFEXIQUE_EFS + "/authentification/moyensSignature";

    static final String ORDRES = PREFEXIQUE_EFS + "/ordres";

    static final String ORDRES_DETAIL = PREFEXIQUE_EFS + "/ordres/{id}";

    static final String SIGNE_KBV = ORDRES_DETAIL + "/kbv/signe";

    static final String ID_ORDRE = "id";

    static final String DEVISE = PREFEXIQUE_EFS + "/devises/{id}";

    static final String DEVISE_TRANSACTIONS = TRANSACTIONS + "/devises";

    static final String DEVISE_ID = "id";

    //Version permettant de faire fonctionner avec MS en local
    //static final String VKB = "/wsauthentication2/authentication/kbvlogin/generate/";

    //Version permettant de faire fonctionner avec MS sur HOMO3
    static final String VKB = "/wsauthentication/authentication/kbvlogin/generate/";

}