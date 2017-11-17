package com.github.rjansem.microservices.training.transaction.domain.efs.devise;

import org.apache.commons.lang.builder.ToStringBuilder;

import java.util.Objects;

/**
 * Bean représentant la Devise
 *
 * @author mbouhmyd
 */
public class Devise {

    private Integer id;

    private String code;

    private String euroCoursAchat;

    private String euroCoursVente;

    private String libelle;

    private String libelleMonnaie;

    private Integer nbDecimales;

    private String euroCours;

    public Devise() {
        //pour jackson
    }

    public Devise(int id, String code) {
        this.id = id;
        this.code = code;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getEuroCoursAchat() {
        return euroCoursAchat;
    }

    public void setEuroCoursAchat(String euroCoursAchat) {
        this.euroCoursAchat = euroCoursAchat;
    }

    public String getEuroCoursVente() {
        return euroCoursVente;
    }

    public void setEuroCoursVente(String euroCoursVente) {
        this.euroCoursVente = euroCoursVente;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getLibelleMonnaie() {
        return libelleMonnaie;
    }

    public void setLibelleMonnaie(String libelleMonnaie) {
        this.libelleMonnaie = libelleMonnaie;
    }

    public Integer getNbDecimales() {
        return nbDecimales;
    }

    public void setNbDecimales(Integer nbDecimales) {
        this.nbDecimales = nbDecimales;
    }

    public String getEuroCours() {
        return euroCours;
    }

    public void setEuroCours(String euroCours) {
        this.euroCours = euroCours;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Devise)) return false;
        Devise devise = (Devise) o;
        return Objects.equals(id, devise.id) &&
                Objects.equals(code, devise.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("code", code)
                .append("libelle", libelle)
                .toString();
    }
}
