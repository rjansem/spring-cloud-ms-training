package com.github.rjansem.microservices.training.account.domain;

/**
 * Énumération des devises
 *
 * @author jntakpe
 */
public enum Currency {

    EURO("EUR");

    private final String label;

    Currency(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
