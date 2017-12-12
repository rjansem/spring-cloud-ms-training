package com.github.rjansem.microservices.training.transaction.api;


/**
 * Constantes liées aux URIs
 *
 * @author rjansem
 */
public final class ApiConstants {

    public static final String CODE_APPLICATION = "WBSCT3";

    static final String SIGN_PAYMENT = "{userId}/signTransactionByKbv";

    static final String PAYMENT_ORDER = "{userId}/clients/new-sepa-transfer";

    static final String VKB = "/getkeyboard";
}
