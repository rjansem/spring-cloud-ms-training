package com.github.rjansem.microservices.training.account.domain.efs.compte;

/**
 * Bean représentant un compte épargne
 *
 * @author jntakpe
 * @see Compte
 */
public class CompteEpargne extends Compte {

    public CompteEpargne() {
        //Necessary for Jackson
    }

    public CompteEpargne(String numero, String type, String intitule, String solde, Integer devise, String iban) {
        super(numero, type, intitule, solde, devise, iban);
    }

}
