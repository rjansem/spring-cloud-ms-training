package com.github.rjansem.microservices.training.account.domain.efs.investissement;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.Objects;

/**
 * Objet représentant titre d'un portefeuilles ou une assurance vie
 *
 * @author mbouhamyd
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class InvestissementTitre implements Comparable<InvestissementTitre> {

    private String classe;

    @NotEmpty
    private String code;

    private String libelle;

    private String quantite;

    private String prixRevientMoyen;

    private String estimationEuro;

    private String poids;

    private String dateCotation;

    private String coursCodeDevise;

    private String codeDevise;

    private String cours;

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
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

    public String getQuantite() {
        return quantite;
    }

    public void setQuantite(String quantite) {
        this.quantite = quantite;
    }

    public String getPrixRevientMoyen() {
        return prixRevientMoyen;
    }

    public void setPrixRevientMoyen(String prixRevientMoyen) {
        this.prixRevientMoyen = prixRevientMoyen;
    }

    public String getEstimationEuro() {
        return estimationEuro;
    }

    public void setEstimationEuro(String estimationEuro) {
        this.estimationEuro = estimationEuro;
    }

    public String getPoids() {
        return poids;
    }

    public void setPoids(String poids) {
        this.poids = poids;
    }

    public String getDateCotation() {
        return dateCotation;
    }

    public void setDateCotation(String dateCotation) {
        this.dateCotation = dateCotation;
    }

    public String getCoursCodeDevise() {
        return coursCodeDevise;
    }

    public void setCoursCodeDevise(String coursCodeDevise) {
        this.coursCodeDevise = coursCodeDevise;
    }

    public String getCodeDevise() {
        return codeDevise;
    }

    public void setCodeDevise(String codeDevise) {
        this.codeDevise = codeDevise;
    }

    public String getCours() {
        return cours;
    }

    public void setCours(String cours) {
        this.cours = cours;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("classe", classe)
                .append("code", code)
                .append("libelle", libelle)
                .toString();
    }

    @Override
    public int compareTo(InvestissementTitre other) {
        return this.getClasse().compareTo(other.getClasse());
    }
}
