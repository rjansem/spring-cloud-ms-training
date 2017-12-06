package com.github.rjansem.microservices.training.profile.domain.efs;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.github.rjansem.microservices.training.apisecurity.config.deserializer.OuiNonCharDeserializer;
import com.github.rjansem.microservices.training.commons.domain.EfsBean;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * Bean représentant une racine d'un utilisateur
 *
 * @author rjansem
 */
public class Racine implements EfsBean {

    @NotEmpty
    private String code;

    @NotEmpty
    private String libelle;

    @NotNull
    @JsonDeserialize(using = OuiNonCharDeserializer.class)
    private Boolean titulaire;

    @JsonDeserialize(using = OuiNonCharDeserializer.class)
    private Boolean dematerialisation;

    @JsonDeserialize(using = OuiNonCharDeserializer.class)
    private Boolean droitDematerialisation;

    @JsonDeserialize(using = OuiNonCharDeserializer.class)
    private Boolean droitEdocuments;

    @JsonDeserialize(using = OuiNonCharDeserializer.class)
    private Boolean droitCredits;

    private String profil;

    private String type;

    private Short poids;

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

    public String getProfil() {
        return profil;
    }

    public void setProfil(String profil) {
        this.profil = profil;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Short getPoids() {
        return poids;
    }

    public void setPoids(Short poids) {
        this.poids = poids;
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
                .append("profil", profil)
                .toString();
    }
}
