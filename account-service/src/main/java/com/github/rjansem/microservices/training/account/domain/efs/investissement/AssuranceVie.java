package com.github.rjansem.microservices.training.account.domain.efs.investissement;


/**
 * Bean représentant une assurances vie d’une racine d’un abonné web
 *
 * @author rjansem
 */
public class AssuranceVie extends Investissement {

    public static final String EXTERNE_TYPE = "Externe";

    public static final String INTERNE_TYPE = "Interne";

    public AssuranceVie() {
        //Necessary for Jackson
    }

    public AssuranceVie(String numero, String type) {
        super(numero, type);
    }

    public AssuranceVie setExterneTypeIfMissing() {
        return getType() == null ? (AssuranceVie) this.setType(EXTERNE_TYPE) : this;
    }

    public AssuranceVie setInterneTypeIfMissing() {
        return getType() == null ? (AssuranceVie) this.setType(INTERNE_TYPE) : this;
    }
}
