package com.github.rjansem.microservices.training.account.domain.efs.operation;

/**
 * Bean wrappant un champ libelle
 *
 * @author jntakpe
 */
public class LibelleWrapper {

    private String libelle;

    public String getLibelle() {
        return libelle;
    }

    public LibelleWrapper setLibelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

}
