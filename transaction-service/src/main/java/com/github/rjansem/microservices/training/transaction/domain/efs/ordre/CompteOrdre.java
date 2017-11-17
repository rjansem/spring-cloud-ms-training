package com.github.rjansem.microservices.training.transaction.domain.efs.ordre;

import com.github.rjansem.microservices.training.commons.domain.EfsBean;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Bean représentant un compte émetteur/bénéficiaire
 *
 * @author mbouhamyd
 */
public class CompteOrdre implements EfsBean {

    private String numero;

    private String type;

    private String intitule;

    private String intitulePersonnalise;

    private String solde;

    private Integer devise;

    private String profil;

    private String iban;

    private String nature;

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIntitule() {
        return intitule;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

    public String getIntitulePersonnalise() {
        return intitulePersonnalise;
    }

    public void setIntitulePersonnalise(String intitulePersonnalise) {
        this.intitulePersonnalise = intitulePersonnalise;
    }

    public String getSolde() {
        return solde;
    }

    public void setSolde(String solde) {
        this.solde = solde;
    }

    public Integer getDevise() {
        return devise;
    }

    public void setDevise(Integer devise) {
        this.devise = devise;
    }

    public String getProfil() {
        return profil;
    }

    public void setProfil(String profil) {
        this.profil = profil;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getNature() {
        return nature;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("numero", numero)
                .append("iban", iban)
                .toString();
    }
}
