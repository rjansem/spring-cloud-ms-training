package com.github.rjansem.microservices.training.account.domain.efs.cartebancaire;

import com.github.rjansem.microservices.training.commons.domain.EfsBean;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.util.Objects;

/**
 * Bean représentant la carte bancaire
 *
 * @author mbouhamyd
 */
public class CarteBancaire implements EfsBean {

    private Integer id;

    private String type;

    private String numero;

    private String titulaire;

    private String encours;

    private Integer devise;

    private String statut;

    private String dateExpiration;

    private String ibanCompte;

    private String intitule;

    private String datePrelevement;

    private String libelleDevise;

    public CarteBancaire() {
    }

    public CarteBancaire(Integer id, String type, String numero, String titulaire, String encours,
                         Integer devise, String statut, String dateExpiration, String ibanCompte,
                         String intitule, String datePrelevement) {
        this.id = id;
        this.type = type;
        this.numero = numero;
        this.titulaire = titulaire;
        this.encours = encours;
        this.devise = devise;
        this.statut = statut;
        this.dateExpiration = dateExpiration;
        this.ibanCompte = ibanCompte;
        this.intitule = intitule;
        this.datePrelevement = datePrelevement;
    }

    public Integer getId() {
        return id;
    }

    public CarteBancaire setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getType() {
        return type;
    }

    public CarteBancaire setType(String type) {
        this.type = type;
        return this;
    }

    public String getNumero() {
        return numero;
    }

    public CarteBancaire setNumero(String numero) {
        this.numero = numero;
        return this;
    }

    public String getTitulaire() {
        return titulaire;
    }

    public CarteBancaire setTitulaire(String titulaire) {
        this.titulaire = titulaire;
        return this;
    }

    public String getEncours() {
        return encours;
    }

    public CarteBancaire setEncours(String encours) {
        this.encours = encours;
        return this;
    }

    public Integer getDevise() {
        return devise;
    }

    public CarteBancaire setDevise(Integer devise) {
        this.devise = devise;
        return this;
    }

    public String getStatut() {
        return statut;
    }

    public CarteBancaire setStatut(String statut) {
        this.statut = statut;
        return this;
    }

    public String getDateExpiration() {
        return dateExpiration;
    }

    public CarteBancaire setDateExpiration(String dateExpiration) {
        this.dateExpiration = dateExpiration;
        return this;
    }

    public String getIbanCompte() {
        return ibanCompte;
    }

    public CarteBancaire setIbanCompte(String ibanCompte) {
        this.ibanCompte = ibanCompte;
        return this;
    }

    public String getIntitule() {
        return intitule;
    }

    public CarteBancaire setIntitule(String intitule) {
        this.intitule = intitule;
        return this;
    }

    public String getDatePrelevement() {
        return datePrelevement;
    }

    public CarteBancaire setDatePrelevement(String datePrelevement) {
        this.datePrelevement = datePrelevement;
        return this;
    }

    public String getLibelleDevise() {
        return libelleDevise;
    }

    public CarteBancaire setLibelleDevise(String libelleDevise) {
        this.libelleDevise = libelleDevise;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CarteBancaire)) {
            return false;
        }
        CarteBancaire that = (CarteBancaire) o;
        return Objects.equals(numero, that.numero) &&
                Objects.equals(ibanCompte, that.ibanCompte);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numero, ibanCompte);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("numero", numero)
                .append("titulaire", titulaire)
                .toString();
    }
}
