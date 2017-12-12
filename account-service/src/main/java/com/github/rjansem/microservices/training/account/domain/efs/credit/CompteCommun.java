package com.github.rjansem.microservices.training.account.domain.efs.credit;

import com.github.rjansem.microservices.training.commons.domain.EfsBean;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * Bean représentant un compte crédit remonté par EFS.
 * Les comptes peuvent être de type {@link Credit} ou {@link EngagementSignature}
 *
 * @author rjansem
 * @see Credit
 * @see EngagementSignature
 */
public class CompteCommun implements EfsBean {

    @NotNull
    private String numero;

    private String nature;

    private String maturite;

    private String capitalRestantDu;

    private String codeDevise;

    private String montantEmprunte;

    CompteCommun(String numero, String nature, String maturite, String capitalRestantDu, String codeDevise,
                 String montantEmprunte) {
        this.numero = numero;
        this.nature = nature;
        this.maturite = maturite;
        this.capitalRestantDu = capitalRestantDu;
        this.codeDevise = codeDevise;
        this.montantEmprunte = montantEmprunte;
    }

    CompteCommun() {
        //Necessary for Jackson
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getNature() {
        return nature;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }

    public String getMaturite() {
        return maturite;
    }

    public void setMaturite(String maturite) {
        this.maturite = maturite;
    }

    public String getCapitalRestantDu() {
        return capitalRestantDu;
    }

    public void setCapitalRestantDu(String capitalRestantDu) {
        this.capitalRestantDu = capitalRestantDu;
    }

    public String getCodeDevise() {
        return codeDevise;
    }

    public void setCodeDevise(String codeDevise) {
        this.codeDevise = codeDevise;
    }

    public String getMontantEmprunte() {
        return montantEmprunte;
    }

    public void setMontantEmprunte(String montantEmprunte) {
        this.montantEmprunte = montantEmprunte;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CompteCommun)) {
            return false;
        }
        CompteCommun that = (CompteCommun) o;
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
                .append("nature", nature)
                .append("maturite", maturite)
                .append("capitalRestantDu", capitalRestantDu)
                .append("codeDevise", codeDevise)
                .toString();
    }
}
