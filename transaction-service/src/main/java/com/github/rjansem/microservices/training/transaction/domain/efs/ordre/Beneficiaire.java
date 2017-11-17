package com.github.rjansem.microservices.training.transaction.domain.efs.ordre;

import com.github.rjansem.microservices.training.commons.domain.EfsBean;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Bean représentant un compte beneficiaire
 *
 * @author mbouhamyd
 */
public class Beneficiaire implements EfsBean {

    private String iban;

    private String libelle;

    private String motif;

    private String reference;

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getMotif() {
        return motif;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("iban", iban)
                .append("libelle", libelle)
                .append("motif", motif)
                .append("reference", reference)
                .toString();
    }
}
