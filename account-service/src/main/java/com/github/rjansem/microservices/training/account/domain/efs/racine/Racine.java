package com.github.rjansem.microservices.training.account.domain.efs.racine;

import com.github.rjansem.microservices.training.commons.domain.EfsBean;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * Bean représentant la 'racine' d'un abonné
 *
 * @author jntakpe
 */
public class Racine implements EfsBean {

    @NotEmpty
    private String code;

    @NotEmpty
    private String libelle;

    @NotNull
    private Boolean titulaire;

    private Boolean dematerialisation;

    private Boolean droitDematerialisation;

    private Boolean droitEdocuments;

    private Boolean droitCredits;

    public Racine() {
    }

    public Racine(String code, String libelle, Boolean titulaire) {
        this.code = code;
        this.libelle = libelle;
        this.titulaire = titulaire;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Boolean getTitulaire() {
        return titulaire;
    }

    public void setTitulaire(Boolean titulaire) {
        this.titulaire = titulaire;
    }

    public Boolean getDematerialisation() {
        return dematerialisation;
    }

    public void setDematerialisation(Boolean dematerialisation) {
        this.dematerialisation = dematerialisation;
    }

    public Boolean getDroitDematerialisation() {
        return droitDematerialisation;
    }

    public void setDroitDematerialisation(Boolean droitDematerialisation) {
        this.droitDematerialisation = droitDematerialisation;
    }

    public Boolean getDroitEdocuments() {
        return droitEdocuments;
    }

    public void setDroitEdocuments(Boolean droitEdocuments) {
        this.droitEdocuments = droitEdocuments;
    }

    public Boolean getDroitCredits() {
        return droitCredits;
    }

    public void setDroitCredits(Boolean droitCredits) {
        this.droitCredits = droitCredits;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Racine)) {
            return false;
        }
        Racine racine = (Racine) o;
        return Objects.equals(code, racine.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("code", code)
                .append("libelle", libelle)
                .append("titulaire", titulaire)
                .toString();
    }
}
