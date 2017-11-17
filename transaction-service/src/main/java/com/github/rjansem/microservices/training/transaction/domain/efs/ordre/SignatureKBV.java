package com.github.rjansem.microservices.training.transaction.domain.efs.ordre;

import com.github.rjansem.microservices.training.commons.domain.EfsBean;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Bean représentant un attribute d'entrée de la signature d’un ordre via le clavier virtuel.
 *
 * @author aazzerrifi
 */
public class SignatureKBV implements EfsBean {

    @NotEmpty
    private String loginWebAbonne;

    @NotEmpty
    private String idClavierVirtuel;

    @NotEmpty
    private String codeClavierVirtuel;

    public SignatureKBV() {
        //for jackson
    }

    public SignatureKBV(String loginWebAbonne, String idClavierVirtuel, String codeClavierVirtuel) {
        this.loginWebAbonne = loginWebAbonne;
        this.idClavierVirtuel = idClavierVirtuel;
        this.codeClavierVirtuel = codeClavierVirtuel;
    }

    public String getLoginWebAbonne() {
        return loginWebAbonne;
    }

    public void setLoginWebAbonne(String loginWebAbonne) {
        this.loginWebAbonne = loginWebAbonne;
    }

    public String getIdClavierVirtuel() {
        return idClavierVirtuel;
    }

    public void setIdClavierVirtuel(String idClavierVirtuel) {
        this.idClavierVirtuel = idClavierVirtuel;
    }

    public String getCodeClavierVirtuel() {
        return codeClavierVirtuel;
    }

    public void setCodeClavierVirtuel(String codeClavierVirtuel) {
        this.codeClavierVirtuel = codeClavierVirtuel;
    }
}
