package com.github.rjansem.microservices.training.account.domain.efs.compte;

/**
 * Bean représentant un compte courant
 *
 * @author rjansem
 * @see Compte
 */
public class CompteCourant extends Compte {

    public CompteCourant() {
        //Necessary for Jackson
    }

    public CompteCourant(String numero, String type, String intitule, String solde, Integer devise, String iban) {
        super(numero, type, intitule, solde, devise, iban);
    }

}
