package com.github.rjansem.microservices.training.transaction.domain.efs.post;

import com.github.rjansem.microservices.training.commons.domain.EfsBean;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Bean représentant un attribute d'entrée de emissionTransaction
 *
 * @author aazzerrifi
 */
public class PostTransaction implements EfsBean {

    private String loginWebAbonne;

    private String nbDecimales;

    private String codeApplication;

    private String ibanCompteEmetteur;

    private String ibanCompteBeneficiaire;

    private String idTiersBeneficiaire;

    @NotEmpty
    private String montant;

    private String dateExecutionSouhaitee;

    private String motif;

    @NotEmpty
    private String reference;

    @NotEmpty
    private String devise;

    public PostTransaction() {
        //For jackson
    }

    public PostTransaction(String loginWebAbonne, String nbDecimales, String codeApplication, String ibanCompteEmetteur, String ibanCompteBeneficiaire, String idTiersBeneficiaire, String montant, String dateExecutionSouhaitee, String motif, String reference, String devise) {
        this.loginWebAbonne = loginWebAbonne;
        this.nbDecimales = nbDecimales;
        this.codeApplication = codeApplication;
        this.ibanCompteEmetteur = ibanCompteEmetteur;
        this.ibanCompteBeneficiaire = ibanCompteBeneficiaire;
        this.idTiersBeneficiaire = idTiersBeneficiaire;
        this.montant = montant;
        this.dateExecutionSouhaitee = dateExecutionSouhaitee;
        this.motif = motif;
        this.reference = reference;
        this.devise = devise;
    }

    public PostTransaction(String loginWebAbonne, String nbDecimales, String codeApplication, String ibanCompteEmetteur, String ibanCompteBeneficiaire, String montant, String dateExecutionSouhaitee, String motif, String reference, String devise) {
        this.loginWebAbonne = loginWebAbonne;
        this.nbDecimales = nbDecimales;
        this.codeApplication = codeApplication;
        this.ibanCompteEmetteur = ibanCompteEmetteur;
        this.ibanCompteBeneficiaire = ibanCompteBeneficiaire;
        this.montant = montant;
        this.dateExecutionSouhaitee = dateExecutionSouhaitee;
        this.motif = motif;
        this.reference = reference;
        this.devise = devise;
    }

    public String getLoginWebAbonne() {
        return loginWebAbonne;
    }

    public void setLoginWebAbonne(String loginWebAbonne) {
        this.loginWebAbonne = loginWebAbonne;
    }

    public String getNbDecimales() {
        return nbDecimales;
    }

    public void setNbDecimales(String nbDecimales) {
        this.nbDecimales = nbDecimales;
    }

    public String getCodeApplication() {
        return codeApplication;
    }

    public void setCodeApplication(String codeApplication) {
        this.codeApplication = codeApplication;
    }

    public String getIbanCompteEmetteur() {
        return ibanCompteEmetteur;
    }

    public void setIbanCompteEmetteur(String ibanCompteEmetteur) {
        this.ibanCompteEmetteur = ibanCompteEmetteur;
    }

    public String getIbanCompteBeneficiaire() {
        return ibanCompteBeneficiaire;
    }

    public void setIbanCompteBeneficiaire(String ibanCompteBeneficiaire) {
        this.ibanCompteBeneficiaire = ibanCompteBeneficiaire;
    }

    public String getIdTiersBeneficiaire() {
        return idTiersBeneficiaire;
    }

    public void setIdTiersBeneficiaire(String idTiersBeneficiaire) {
        this.idTiersBeneficiaire = idTiersBeneficiaire;
    }

    public String getMontant() {
        return montant;
    }

    public void setMontant(String montant) {
        this.montant = montant;
    }

    public String getDateExecutionSouhaitee() {
        return dateExecutionSouhaitee;
    }

    public void setDateExecutionSouhaitee(String dateExecutionSouhaitee) {
        this.dateExecutionSouhaitee = dateExecutionSouhaitee;
    }

    public String getMotif() {
        return motif;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getDevise() {
        return devise;
    }

    public void setDevise(String devise) {
        this.devise = devise;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("loginWebAbonne", loginWebAbonne)
                .append("nbDecimales", nbDecimales)
                .append("codeApplication", codeApplication)
                .append("ibanCompteEmetteur", ibanCompteEmetteur)
                .append("ibanCompteBeneficiaire", ibanCompteBeneficiaire)
                .append("idTiersBeneficiaire", idTiersBeneficiaire)
                .append("montant", montant)
                .append("dateExecutionSouhaitee", dateExecutionSouhaitee)
                .append("motif", motif)
                .append("reference", reference)
                .append("devise", devise)
                .toString();
    }
}
