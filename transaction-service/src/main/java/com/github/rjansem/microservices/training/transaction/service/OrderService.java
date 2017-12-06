package com.github.rjansem.microservices.training.transaction.service;

import com.github.rjansem.microservices.training.transaction.domain.pbi.PaymentOrder;
import com.github.rjansem.microservices.training.transaction.domain.pbi.transaction.ListOfTransaction;
import com.github.rjansem.microservices.training.transaction.domain.pbi.transaction.SignTransaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rx.Observable;

import java.util.ArrayList;
import java.util.Objects;

import static com.github.rjansem.microservices.training.transaction.api.ApiConstants.CODE_APPLICATION;

/**
 * Service pour la gestion des ordres
 *
 * @author aazzerrifi
 */
@Service
public class OrderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderService.class);


    @Autowired
    public OrderService() {
    }

    public Observable<ListOfTransaction> signPayment(SignTransaction signTransaction, String userId) {
        Objects.requireNonNull(userId);
        String login = userId;
        LOGGER.info("Signature des ordres de paiement du client {} de l'application {}", login, CODE_APPLICATION);
        ListOfTransaction listTransaction = new ListOfTransaction();
        listTransaction.setTransactions(new ArrayList<>());
        return Observable.just(listTransaction);
    }

    public Observable<PaymentOrder> initiateSEPAPaymentOrder(PaymentOrder paymentOrder, String userId) {
        Objects.requireNonNull(userId);
        String login = userId;

        LOGGER.info("Emission d'ordre de paiement du client {} de l'application {}", login, CODE_APPLICATION);
        return Observable.just(paymentOrder);
    }

}
