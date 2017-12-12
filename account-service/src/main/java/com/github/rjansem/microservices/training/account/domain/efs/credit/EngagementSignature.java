package com.github.rjansem.microservices.training.account.domain.efs.credit;

/**
 * Bean représentant un Engagement par signature
 *
 * @author rjansem
 * @see CompteCommun
 */
public class EngagementSignature extends CompteCommun {

    private String montantProchaineCommission;

    private String beneficiaire;

    private String dateDebut;

    public EngagementSignature() {
        //Necessary for Jackson
    }

    public EngagementSignature(String numero, String nature, String maturite, String capitalRestantDu,
                               String codeDevise, String montantEmprunte) {
        super(numero, nature, maturite, capitalRestantDu, codeDevise, montantEmprunte);
    }

    public String getMontantProchaineCommission() {
        return montantProchaineCommission;
    }

    public void setMontantProchaineCommission(String montantProchaineCommission) {
        this.montantProchaineCommission = montantProchaineCommission;
    }

    public String getBeneficiaire() {
        return beneficiaire;
    }

    public void setBeneficiaire(String beneficiaire) {
        this.beneficiaire = beneficiaire;
    }

    public String getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(String dateDebut) {
        this.dateDebut = dateDebut;
    }
}
