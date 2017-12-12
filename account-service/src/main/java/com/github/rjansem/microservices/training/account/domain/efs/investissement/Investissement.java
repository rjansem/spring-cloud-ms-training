package com.github.rjansem.microservices.training.account.domain.efs.investissement;

import com.github.rjansem.microservices.training.commons.domain.EfsBean;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Objects;

/**
 * Bean représentant un compte d'investissement remonté par EFS.
 * Les comptes peuvent être de type {@link AssuranceVie} ou {@link PortefeuilleTitres}
 *
 * @author rjansem
 * @see AssuranceVie
 * @see PortefeuilleTitres
 */
public abstract class Investissement implements EfsBean {

    private String numero;

    private String nomAssureur;

    private String intitule;

    private String premierSouscripteur;

    private String valorisation;

    private String type;

    private String codeDevise;

    private String typeGestion;

    private String nantissement;

    private String dateValorisation;

    private String ibanCompte;

    Investissement() {
    }

    Investissement(String numero, String type) {
        this.numero = numero;
        this.type = type;
    }

    public String getNumero() {
        return numero;
    }

    public Investissement setNumero(String numero) {
        this.numero = numero;
        return this;
    }

    public String getNomAssureur() {
        return nomAssureur;
    }

    public Investissement setNomAssureur(String nomAssureur) {
        this.nomAssureur = nomAssureur;
        return this;
    }

    public String getIntitule() {
        return intitule;
    }

    public Investissement setIntitule(String intitule) {
        this.intitule = intitule;
        return this;
    }

    public String getPremierSouscripteur() {
        return premierSouscripteur;
    }

    public Investissement setPremierSouscripteur(String premierSouscripteur) {
        this.premierSouscripteur = premierSouscripteur;
        return this;
    }

    public String getValorisation() {
        return valorisation;
    }

    public Investissement setValorisation(String valorisation) {
        this.valorisation = valorisation;
        return this;
    }

    public String getType() {
        return type;
    }

    public Investissement setType(String type) {
        this.type = type;
        return this;
    }

    public String getCodeDevise() {
        return codeDevise;
    }

    public Investissement setCodeDevise(String codeDevise) {
        this.codeDevise = codeDevise;
        return this;
    }

    public String getTypeGestion() {
        return typeGestion;
    }

    public Investissement setTypeGestion(String typeGestion) {
        this.typeGestion = typeGestion;
        return this;
    }

    public String getNantissement() {
        return nantissement;
    }

    public Investissement setNantissement(String nantissement) {
        this.nantissement = nantissement;
        return this;
    }

    public String getDateValorisation() {
        return dateValorisation;
    }

    public Investissement setDateValorisation(String dateValorisation) {
        this.dateValorisation = dateValorisation;
        return this;
    }

    public String getIbanCompte() {
        return ibanCompte;
    }

    public Investissement setIbanCompte(String ibanCompte) {
        this.ibanCompte = ibanCompte;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Investissement)) {
            return false;
        }
        Investissement that = (Investissement) o;
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
                .toString();
    }
}
