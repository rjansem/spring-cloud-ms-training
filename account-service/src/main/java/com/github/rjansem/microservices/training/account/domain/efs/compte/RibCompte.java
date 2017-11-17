package com.github.rjansem.microservices.training.account.domain.efs.compte;

import com.github.rjansem.microservices.training.account.domain.efs.compte.rib.Domiciliation;
import com.github.rjansem.microservices.training.commons.domain.EfsBean;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Objects;

/**
 * Bean représentant un RIB d’un compte d’une racine d’un abonné web.
 *
 * @author aazzerrifi
 */
public class RibCompte implements EfsBean {

    private Domiciliation domiciliation;

    private String codeBanque;

    private String codeGuichet;

    private String numeroCompte;

    private String cleRib;

    private String iban;

    private String bic;

    private String titulaire;

    private String intitule;

    public Domiciliation getDomiciliation() {
        return domiciliation;
    }

    public void setDomiciliation(Domiciliation domiciliation) {
        this.domiciliation = domiciliation;
    }

    public String getCodeBanque() {
        return codeBanque;
    }

    public void setCodeBanque(String codeBanque) {
        this.codeBanque = codeBanque;
    }

    public String getCodeGuichet() {
        return codeGuichet;
    }

    public void setCodeGuichet(String codeGuichet) {
        this.codeGuichet = codeGuichet;
    }

    public String getNumeroCompte() {
        return numeroCompte;
    }

    public void setNumeroCompte(String numeroCompte) {
        this.numeroCompte = numeroCompte;
    }

    public String getCleRib() {
        return cleRib;
    }

    public void setCleRib(String cleRib) {
        this.cleRib = cleRib;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getBic() {
        return bic;
    }

    public void setBic(String bic) {
        this.bic = bic;
    }

    public String getTitulaire() {
        return titulaire;
    }

    public void setTitulaire(String titulaire) {
        this.titulaire = titulaire;
    }

    public String getIntitule() {
        return intitule;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RibCompte)) {
            return false;
        }
        RibCompte ribCompte = (RibCompte) o;
        return Objects.equals(iban, ribCompte.iban);
    }

    @Override
    public int hashCode() {
        return Objects.hash(iban);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("numeroCompte", numeroCompte)
                .append("iban", iban)
                .append("bic", bic)
                .toString();
    }
}
