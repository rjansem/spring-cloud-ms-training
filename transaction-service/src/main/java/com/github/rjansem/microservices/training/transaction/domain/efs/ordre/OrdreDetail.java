package com.github.rjansem.microservices.training.transaction.domain.efs.ordre;


import com.github.rjansem.microservices.training.commons.domain.EfsBean;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Bean représentant le detail d'un ordre
 *
 * @author mbouhamyd
 */
public class OrdreDetail implements EfsBean {

    private String id;

    private CompteOrdre compteEmetteur;

    private String dateExecutionSouhaitee;

    private String codeApplication;

    private String statut;

    private String montant;

    private String codeDevise;

    private String dateCreation;

    private String media;

    private String codeOperation;

    private List<Beneficiaire> beneficiaires = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public CompteOrdre getCompteEmetteur() {
        return compteEmetteur;
    }

    public void setCompteEmetteur(CompteOrdre compteEmetteur) {
        this.compteEmetteur = compteEmetteur;
    }

    public String getDateExecutionSouhaitee() {
        return dateExecutionSouhaitee;
    }

    public void setDateExecutionSouhaitee(String dateExecutionSouhaitee) {
        this.dateExecutionSouhaitee = dateExecutionSouhaitee;
    }

    public String getCodeApplication() {
        return codeApplication;
    }

    public void setCodeApplication(String codeApplication) {
        this.codeApplication = codeApplication;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
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

    public String getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(String dateCreation) {
        this.dateCreation = dateCreation;
    }

    public String getMedia() {
        return media;
    }

    public void setMedia(String media) {
        this.media = media;
    }

    public String getCodeOperation() {
        return codeOperation;
    }

    public void setCodeOperation(String codeOperation) {
        this.codeOperation = codeOperation;
    }

    public List<Beneficiaire> getBeneficiaires() {
        return beneficiaires;
    }

    public void setBeneficiaires(List<Beneficiaire> beneficiaires) {
        this.beneficiaires = beneficiaires;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("compteEmetteur", compteEmetteur)
                .append("dateExecutionSouhaitee", dateExecutionSouhaitee)
                .append("codeApplication", codeApplication)
                .append("statut", statut)
                .append("montant", montant)
                .append("codeDevise", codeDevise)
                .append("dateCreation", dateCreation)
                .append("media", media)
                .append("codeOperation", codeOperation)
                .append("beneficiaires", beneficiaires)
                .toString();
    }
}
