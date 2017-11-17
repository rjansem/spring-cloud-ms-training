package com.github.rjansem.microservices.training.commons.domain;

/**
 * Interface publiant les méthodes permettant manipuler les identifiants de domain
 *
 * @author jntakpe
 */
public interface IdentifiableDomain {

    String getId();

    IdentifiableDomain setId(String setId);

    default boolean hasId() {
        return getId() != null;
    }

}
