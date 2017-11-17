package com.github.rjansem.microservices.training.account.domain.efs.credit;


/**
 * Bean représentant un credit
 *
 * @author aazzerrifi
 * @see CompteCommun
 */
public class Credit extends CompteCommun {

    private String emprunteur;

    private String taux;

    private String modeAmortissement;

    private String frequenceAmortissement;

    private String dateProchaineEcheance;

    private String montantProchaineEcheance;

    private String typePrelevement;

    private String dateDebut;

    public Credit() {
        //Necessary for Jackson
    }

    public Credit(String numero, String nature, String maturite, String capitalRestantDu,
                  String codeDevise, String montantEmprunte) {
        super(numero, nature, maturite, capitalRestantDu, codeDevise, montantEmprunte);
    }

    public String getEmprunteur() {
        return emprunteur;
    }

    public void setEmprunteur(String emprunteur) {
        this.emprunteur = emprunteur;
    }

    public String getTaux() {
        return taux;
    }

    public void setTaux(String taux) {
        this.taux = taux;
    }

    public String getModeAmortissement() {
        return modeAmortissement;
    }

    public void setModeAmortissement(String modeAmortissement) {
        this.modeAmortissement = modeAmortissement;
    }

    public String getFrequenceAmortissement() {
        return frequenceAmortissement;
    }

    public void setFrequenceAmortissement(String frequenceAmortissement) {
        this.frequenceAmortissement = frequenceAmortissement;
    }

    public String getDateProchaineEcheance() {
        return dateProchaineEcheance;
    }

    public void setDateProchaineEcheance(String dateProchaineEcheance) {
        this.dateProchaineEcheance = dateProchaineEcheance;
    }

    public String getMontantProchaineEcheance() {
        return montantProchaineEcheance;
    }

    public void setMontantProchaineEcheance(String montantProchaineEcheance) {
        this.montantProchaineEcheance = montantProchaineEcheance;
    }

    public String getTypePrelevement() {
        return typePrelevement;
    }

    public void setTypePrelevement(String typePrelevement) {
        this.typePrelevement = typePrelevement;
    }

    public String getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(String dateDebut) {
        this.dateDebut = dateDebut;
    }
}
