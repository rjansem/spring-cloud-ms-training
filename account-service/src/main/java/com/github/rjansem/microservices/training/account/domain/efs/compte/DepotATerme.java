package com.github.rjansem.microservices.training.account.domain.efs.compte;

import com.github.rjansem.microservices.training.commons.domain.EfsBean;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Objects;

/**
 * Bean représentant un depot à terme
 *
 * @author rjansem
 */
public class DepotATerme implements EfsBean {

    private String numero;

    private String numeroCompte;

    private String montant;

    private Integer devise;

    private String dateDebut;

    private String dateFin;

    private String tauxNominal;

    private String prelevementLiberatoire;

    private String nomTitulaire;

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getNumeroCompte() {
        return numeroCompte;
    }

    public void setNumeroCompte(String numeroCompte) {
        this.numeroCompte = numeroCompte;
    }

    public String getMontant() {
        return montant;
    }

    public void setMontant(String montant) {
        this.montant = montant;
    }

    public Integer getDevise() {
        return devise;
    }

    public void setDevise(Integer devise) {
        this.devise = devise;
    }

    public String getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(String dateDebut) {
        this.dateDebut = dateDebut;
    }

    public String getDateFin() {
        return dateFin;
    }

    public void setDateFin(String dateFin) {
        this.dateFin = dateFin;
    }

    public String getTauxNominal() {
        return tauxNominal;
    }

    public void setTauxNominal(String tauxNominal) {
        this.tauxNominal = tauxNominal;
    }

    public String getPrelevementLiberatoire() {
        return prelevementLiberatoire;
    }

    public void setPrelevementLiberatoire(String prelevementLiberatoire) {
        this.prelevementLiberatoire = prelevementLiberatoire;
    }

    public String getNomTitulaire() {
        return nomTitulaire;
    }

    public void setNomTitulaire(String nomTitulaire) {
        this.nomTitulaire = nomTitulaire;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DepotATerme)) {
            return false;
        }
        DepotATerme that = (DepotATerme) o;
        return Objects.equals(numero, that.numero);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numero);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("numero", numero)
                .append("numeroCompte", numeroCompte)
                .append("dateDebut", dateDebut)
                .append("dateFin", dateFin)
                .append("nomTitulaire", nomTitulaire)
                .toString();
    }
}
