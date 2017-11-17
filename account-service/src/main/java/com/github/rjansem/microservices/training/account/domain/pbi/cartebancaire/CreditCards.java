package com.github.rjansem.microservices.training.account.domain.pbi.cartebancaire;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.HashSet;
import java.util.Set;

/**
 * Bean représentant une liste des cartes bancaires
 *
 * @author mbouhamyd
 */
// NON utilisée
public class CreditCards {

    private Set<CreditCard> creditcards = new HashSet<>();

    public CreditCards(Set<CreditCard> creditCards) {
        this.creditcards = creditCards;
    }

    public Set<CreditCard> getCreditcards() {
        return creditcards;
    }

    public void setCreditcards(Set<CreditCard> creditcards) {
        this.creditcards = creditcards;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("creditcards", creditcards)
                .toString();
    }
}
