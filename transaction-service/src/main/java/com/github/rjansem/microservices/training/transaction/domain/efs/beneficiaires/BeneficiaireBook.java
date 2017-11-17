package com.github.rjansem.microservices.training.transaction.domain.efs.beneficiaires;

import com.github.rjansem.microservices.training.commons.domain.EfsBean;
import com.github.rjansem.microservices.training.transaction.domain.efs.ordre.CompteOrdre;

import java.util.Set;

/**
 * Created by aazzerrifi on 26/12/2016.
 */
public class BeneficiaireBook implements EfsBean {

    private Set<Tier> tiers;

    private Set<CompteOrdre> comptes;

    public BeneficiaireBook() {
        // for jackson
    }

    public Set<Tier> getTiers() {
        return tiers;
    }

    public void setTiers(Set<Tier> tiers) {
        this.tiers = tiers;
    }

    public Set<CompteOrdre> getComptes() {
        return comptes;
    }

    public void setComptes(Set<CompteOrdre> comptes) {
        this.comptes = comptes;
    }
}