package com.github.rjansem.microservices.training.transaction.domain.pbi.transaction;

import com.github.rjansem.microservices.training.commons.domain.PbiBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Bean représentant une liste des ordres de paiement
 *
 * @author rjansem
 */
public class ListOfTransaction implements PbiBean {

    private List<Payment> transactions = new ArrayList<> ( );

    public ListOfTransaction() {
        // for jackson
    }

    public ListOfTransaction(List<Payment> transactions) {
        this.transactions = transactions;
    }

    public List<Payment> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Payment> transactions) {
        this.transactions = transactions;
    }
}
