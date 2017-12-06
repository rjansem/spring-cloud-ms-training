package com.github.rjansem.microservices.training.account.domain.efs.compte;

import com.github.rjansem.microservices.training.commons.domain.EfsBean;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Objects;

/**
 * Bean représentant un compte remonté par EFS.
 * Les comptes peuvent être de type {@link CompteCourant} ou {@link CompteEpargne}
 *
 * @author rjansem
 * @see CompteCourant
 * @see CompteEpargne
 */
public abstract class Compte implements EfsBean {

    private String numero;

    private String type;

    private String intitule;

    private String solde;

    private Integer devise;

    private String iban;

    private String encoursCartesBancaires;

    private String dateEncoursCartesBancaires;

    private String dateSolde;

    public Compte() {
        //Necessary for Jackson
    }

    public Compte(String numero, String type, String intitule, String solde, Integer devise, String iban) {
        this.numero = numero;
        this.type = type;
        this.intitule = intitule;
        this.solde = solde;
        this.devise = devise;
        this.iban = iban;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIntitule() {
        return intitule;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

    public String getSolde() {
        return solde;
    }

    public void setSolde(String solde) {
        this.solde = solde;
    }

    public Integer getDevise() {
        return devise;
    }

    public void setDevise(Integer devise) {
        this.devise = devise;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getEncoursCartesBancaires() {

        return encoursCartesBancaires;
    }

    public void setEncoursCartesBancaires(String encoursCartesBancaires) {
        this.encoursCartesBancaires = encoursCartesBancaires;
    }

    public String getDateEncoursCartesBancaires() {
        return dateEncoursCartesBancaires;
    }

    public void setDateEncoursCartesBancaires(String dateEncoursCartesBancaires) {
        this.dateEncoursCartesBancaires = dateEncoursCartesBancaires;
    }

    public String getDateSolde() {
        return dateSolde;
    }

    public void setDateSolde(String dateSolde) {
        this.dateSolde = dateSolde;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Compte)) {
            return false;
        }
        Compte that = (Compte) o;
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
                .append("type", type)
                .append("intitule", intitule)
                .append("solde", solde)
                .append("devise", devise)
                .toString();
    }
}
