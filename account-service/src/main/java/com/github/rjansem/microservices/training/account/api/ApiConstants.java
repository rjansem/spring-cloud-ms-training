package com.github.rjansem.microservices.training.account.api;


import com.github.rjansem.microservices.training.apisecurity.ServicesUris;

/**
 * Constantes liées aux URIs
 *
 * @author rjansem
 */
final class ApiConstants {

    static final String ACCOUNT_OVERVIEW = "/account-overview";

    static final String LOAN_DETAILS = "/loans/{loanId}";

    static final String LOAN_DETAILS_BIS = "/loans/{loanId1}/{loanId2}";

    static final String ACCOUNT = "/accounts";

    static final String RIB = "/rib";

    static final String OPERATIONS = "/operations";

    static final String RETRIEVE_BALANCE_ALL = "/accounts/accounts-balance";

    static final String RETRIEVE_BALANCE_ACCOUNT = "/accounts/{accountId}/balance";

    static final String POSITION_DETAIL = "/investments/{investmentId}/positions/{positionId}";

    static final String PORTFOLIO_LI = "/investments/{investmentId}";

    private static final String USER = ServicesUris.API + "/{userId}";

    private static final String CLIENTS = USER + "/clients";

    static final String CLIENTS_TECHNICAL_BY_ID = CLIENTS + "/{clientTechnicalId}";

}
