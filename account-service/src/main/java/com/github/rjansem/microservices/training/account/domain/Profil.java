package com.github.rjansem.microservices.training.account.domain;

/**
 * Énumération des profils
 *
 * @author jntakpe
 */
public enum Profil {

    PARTICULIER("comptes-p"),
    ENTREPRISE("comptes-e");

    private final String libelle;

    Profil(String libelle) {
        this.libelle = libelle;
    }

    public String getLibelle() {
        return libelle;
    }
}
