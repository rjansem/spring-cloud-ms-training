package com.github.rjansem.microservices.training.account.domain.efs.investissement;


/**
 * Bean représentant un portefeuille titres d’une racine d’un abonné web.
 *
 * @author aazzerrifi
 */
public class PortefeuilleTitres extends Investissement {

    public PortefeuilleTitres() {
        //Necessary for Jackson
    }

    public PortefeuilleTitres(String numero, String type) {
        super(numero, type);
    }
}
