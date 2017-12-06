package com.github.rjansem.microservices.training.commons.testing;

import rx.observers.TestSubscriber;

/**
 * Implémentation de {@link TestSubscriber} avec quelques méthodes utilitaires additionnelles
 *
 * @author rjansem
 */
public class CustomTestSubscriber<T> extends TestSubscriber<T> {

    public T assertNoErrorsThenWaitAndGetValue() {
        awaitTerminalEvent();
        assertNoErrors();
        assertValueCount(1);
        return getSingleValue();
    }

    private T getSingleValue() {
        return this.getOnNextEvents().stream().findFirst()
                .orElseThrow(() -> new IllegalStateException("Impossible d'extraire une valeur de l'observable"));
    }
}
