package com.github.rjansem.microservices.training.exception;

import feign.FeignException;

/**
 * Wrapper ajoutant le code EFS à une FeignException
 *
 * @author rjansem
 */
public class EfsFeignException extends FeignException {

    private final EfsExceptionBean efsException;

    public EfsFeignException(int status, String message, EfsExceptionBean efsException) {
        super(status, message);
        this.efsException = efsException;
    }

    EfsExceptionBean getEfsException() {
        return efsException;
    }

}
