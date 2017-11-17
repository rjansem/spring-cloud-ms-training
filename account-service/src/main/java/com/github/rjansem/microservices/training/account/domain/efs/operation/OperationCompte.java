package com.github.rjansem.microservices.training.account.domain.efs.operation;

import com.github.rjansem.microservices.training.commons.domain.EfsBean;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.ArrayList;
import java.util.List;

/**
 * Bean représentant une opération compte
 *
 * @author mbouhamyd
 */
public class OperationCompte implements EfsBean {

    @NotEmpty
    private String dateValeur;

    @NotEmpty
    private Integer devise;

    @NotEmpty
    private String montantDevise;

    @NotEmpty
    private String montantEuro;

    @NotEmpty
    private String dateOperation;

    @NotEmpty
    private String libelle;

    private List<LibelleWrapper> complements = new ArrayList<>();

    public OperationCompte() {
        //pour jackson
    }

    public Integer getDevise() {
        return devise;
    }

    public void setDevise(Integer devise) {
        this.devise = devise;
    }

    public String getMontantDevise() {
        return montantDevise;
    }

    public void setMontantDevise(String montantDevise) {
        this.montantDevise = montantDevise;
    }

    public String getMontantEuro() {
        return montantEuro;
    }

    public void setMontantEuro(String montantEuro) {
        this.montantEuro = montantEuro;
    }

    public String getDateOperation() {
        return dateOperation;
    }

    public void setDateOperation(String dateOperation) {
        this.dateOperation = dateOperation;
    }

    public String getDateValeur() {
        return dateValeur;
    }

    public void setDateValeur(String dateValeur) {
        this.dateValeur = dateValeur;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public List<LibelleWrapper> getComplements() {
        return complements;
    }

    public void setComplements(List<LibelleWrapper> complements) {
        this.complements = complements;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("devise", devise)
                .append("montantDevise", montantDevise)
                .append("montantEuro", montantEuro)
                .append("dateOperation", dateOperation)
                .toString();
    }
}
