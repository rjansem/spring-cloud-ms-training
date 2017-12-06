package com.github.rjansem.microservices.training.transaction.api;


/**
 * Constantes liées aux URIs
 *
 * @author azzerrifi
 */
public final class ApiConstants {

    public static final String CODE_APPLICATION = "WBSCT3";

    static final String ACCOUNT_TO_DEBIT = "/accounts-to-debit";

    static final String BENEFICIARIES = "/beneficiaries";

    static final String TRANSACTION_OVERWIEW = "/transaction-overview";

    static final String SIGN_PAYMENT = "/signTransactionByKbv";

    static final String PAYMENT_ORDER = "{userId}/clients/new-sepa-transfer";

    static final String VKB = "/getkeyboard";
}
