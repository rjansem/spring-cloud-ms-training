package com.github.rjansem.microservices.training.account.domain.efs.operation;

import com.github.rjansem.microservices.training.commons.domain.EfsBean;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Bean représentant une opération Carte Bancaire
 *
 * @author mbouhamyd
 */
public class OperationCarte implements EfsBean {

    private String dateAchat;

    private String nomCommercant;

    private String lieuAchat;

    private String montant;

    private String codeDevise;

    public OperationCarte() {
        //pour jackson
    }

    public String getDateAchat() {
        return dateAchat;
    }

    public void setDateAchat(String dateAchat) {
        this.dateAchat = dateAchat;
    }

    public String getNomCommercant() {
        return nomCommercant;
    }

    public void setNomCommercant(String nomCommercant) {
        this.nomCommercant = nomCommercant;
    }

    public String getLieuAchat() {
        return lieuAchat;
    }

    public void setLieuAchat(String lieuAchat) {
        this.lieuAchat = lieuAchat;
    }

    public String getMontant() {
        return montant;
    }

    public void setMontant(String montant) {
        this.montant = montant;
    }

    public String getCodeDevise() {
        return codeDevise;
    }

    public void setCodeDevise(String codeDevise) {
        this.codeDevise = codeDevise;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("dateAchat", dateAchat)
                .append("nomCommercant", nomCommercant)
                .toString();
    }
}
