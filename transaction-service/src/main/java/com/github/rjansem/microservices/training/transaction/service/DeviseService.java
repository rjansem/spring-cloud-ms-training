package com.github.rjansem.microservices.training.transaction.service;

import com.github.rjansem.microservices.training.exception.NOBCException;
import com.github.rjansem.microservices.training.transaction.client.DeviseClient;
import com.github.rjansem.microservices.training.transaction.domain.efs.devise.Devise;
import com.github.rjansem.microservices.training.transaction.domain.pbi.AddressBook;
import com.github.rjansem.microservices.training.transaction.domain.pbi.PaymentOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rx.Observable;

import java.util.Objects;

/**
 * Service pour la gestion de la devise
 *
 * @author mbouhamyd
 * @author aazzerrifi
 */
@Service
public class DeviseService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DeviseService.class);

    private final DeviseClient deviseClient;

    @Autowired
    public DeviseService(DeviseClient deviseClient) {
        this.deviseClient = deviseClient;
    }

    /**
     * Récupère le détail d'une devise
     *
     * @param id code de la devise
     * @param token
     * @return détail de la devise
     */
    public Observable<Devise> findDeviseById(String id, String token) {
        Objects.requireNonNull(id);
        LOGGER.info("Recherche la devise {}", id);
        return deviseClient.findDeviseById(id, token)
                .doOnNext(devise -> LOGGER.info("Récupération le détail de la devise {} avec le code {}", id, devise.getCode()))
                .doOnError(err -> LOGGER.warn("Impossible de récupérer le detail de la devise {}", id))
                .onErrorReturn(err -> new Devise());
    }

    public Observable<Integer> findDeviseTransactionById(String token, String codeDevise) {
        Objects.requireNonNull(codeDevise);
        LOGGER.info("Recherche la devise {}", codeDevise);
        return deviseClient.findDeviseTransactionById(token)
                .doOnNext(devise -> LOGGER.info("Récupération des devises"))
                .doOnError(err -> NOBCException.throwEfsError("Impossible de récupérer le detail de la devise ", err))
                .map(devises -> devises.stream().filter(devise -> devise.getCode().equals(codeDevise)).findFirst().get().getId());
    }

    /**
     * Récupère le code d'une devise à partir d'un id devise
     *
     * @param idDevise id de la devise
     * @return code de la devise
     */
    public Observable<String> codeDeviseToCurrency(int idDevise, String token) {
        Objects.requireNonNull(idDevise);
        LOGGER.info("Recherche la devise {}", idDevise);
        return deviseClient.findDeviseById(String.valueOf(idDevise), token)
                .doOnNext(devise -> LOGGER.info("Récupération le détail de la devise {} {}", idDevise, devise.toString()))
                .doOnError(err -> {
                    String msg = String.format("erreur lors de la récupération de la devise %s", idDevise);
                    NOBCException.throwEfsError(msg, err);
                })
                .map(devise -> devise.getCode());
    }

    public AddressBook getAddressBookWithDevise(AddressBook addressBook, String token) {
        if (addressBook.getBalanceCurrency ( ) != (null)) {
            addressBook.setBalanceCurrency (codeDeviseToCurrency (
                    Integer.parseInt(addressBook.getBalanceCurrency()), token).toBlocking().first());
        }
        return addressBook;
    }

    public PaymentOrder getPaymentOrderWithDevise(PaymentOrder paymentOrder, String token) {
        if(paymentOrder.getDebitAccount().getAccountBalanceCurrency() != (null)){
            paymentOrder.getDebitAccount().setAccountBalanceCurrency(codeDeviseToCurrency(
                    Integer.parseInt(paymentOrder.getDebitAccount().getAccountBalanceCurrency()), token).toBlocking().first());
        }
        return paymentOrder;
    }
}
