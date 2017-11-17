package com.github.rjansem.microservices.training.profile.domain.efs;

import com.github.rjansem.microservices.training.commons.domain.EfsBean;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.Objects;

/**
 * Bean représentant un abonné
 *
 * @author mbouhamyd
 */
public class Abonne implements EfsBean {

    @NotEmpty
    private String nom;

    @NotEmpty
    private String prenom;

    public Abonne() {
    }

    public Abonne(String nom, String prenom) {
        this.nom = nom;
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Abonne)) {
            return false;
        }
        Abonne abonne = (Abonne) o;
        return Objects.equals(nom, abonne.nom) &&
                Objects.equals(prenom, abonne.prenom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nom);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("nom", nom)
                .append("prenom", prenom)
                .toString();
    }
}
