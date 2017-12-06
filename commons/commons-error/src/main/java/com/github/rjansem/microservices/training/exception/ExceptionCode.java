package com.github.rjansem.microservices.training.exception;

/**
 * Code représentant le type d'exception de manière unique
 *
 * @author rjansem
 */
public interface ExceptionCode {

    /**
     * Renvoi le statut HTTP de l'exception
     *
     * @return statut HTTP
     */
    int getStatus();

}
