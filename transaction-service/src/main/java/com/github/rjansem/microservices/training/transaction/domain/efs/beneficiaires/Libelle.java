package com.github.rjansem.microservices.training.transaction.domain.efs.beneficiaires;

import com.github.rjansem.microservices.training.commons.domain.EfsBean;

/**
 * Created by aazzerrifi on 28/12/2016.
 */
public class Libelle implements EfsBean {

    private String libelle;

    public Libelle() {
        // For jackson
    }

    public Libelle(String libelle) {
        this.libelle = libelle;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
}
