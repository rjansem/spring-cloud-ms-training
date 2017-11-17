package com.github.rjansem.microservices.training.account.domain.efs.compte.rib;

import com.github.rjansem.microservices.training.commons.domain.EfsBean;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Bean représentant l'adresse liée à un compte.
 *
 * @author aazzerrifi
 */
public class Domiciliation implements EfsBean {

    private String nom;

    private String adresse1;

    private String adresse2;

    private String adresse3;

    private String pays;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAdresse1() {
        return adresse1;
    }

    public void setAdresse1(String adresse1) {
        this.adresse1 = adresse1;
    }

    public String getAdresse2() {
        return adresse2;
    }

    public void setAdresse2(String adresse2) {
        this.adresse2 = adresse2;
    }

    public String getAdresse3() {
        return adresse3;
    }

    public void setAdresse3(String adresse3) {
        this.adresse3 = adresse3;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("nom", nom)
                .append("adresse1", adresse1)
                .append("adresse2", adresse2)
                .append("adresse3", adresse3)
                .append("pays", pays)
                .toString();
    }
}
