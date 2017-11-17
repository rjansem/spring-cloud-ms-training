package com.github.rjansem.microservices.training.transaction.service;

import com.github.rjansem.microservices.training.transaction.exception.TransactionExceptionFactory;
import com.github.rjansem.microservices.training.apisecurity.AuthenticatedUser;
import com.github.rjansem.microservices.training.exception.NOBCException;
import com.github.rjansem.microservices.training.transaction.client.AuthentificationClient;
import com.github.rjansem.microservices.training.transaction.domain.efs.ordre.Alarme;
import com.github.rjansem.microservices.training.transaction.domain.efs.ordre.MoyenSignature;
import com.github.rjansem.microservices.training.transaction.domain.pbi.PaymentOrder;
import com.github.rjansem.microservices.training.transaction.domain.pbi.TransactionInfo;
import com.github.rjansem.microservices.training.transaction.exception.TransactionExceptionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import rx.Observable;

import java.util.Set;

/**
 * Service pour la gestion des moyens de signature
 *
 * @author aazzerrifi
 */
@Service
public class MeansSignatureService {

    static final String CODE_APPLICATION = "WBSCT3";
    private static final Logger LOGGER = LoggerFactory.getLogger(MeansSignatureService.class);
    private final AuthentificationClient authentificationClient;


    public MeansSignatureService(AuthentificationClient authentificationClient) {
        this.authentificationClient = authentificationClient;
    }

    public Observable<MoyenSignature> checkMeansOfSignature(AuthenticatedUser user, PaymentOrder paymentOrder) {
        return meansOfSignatureOrder(user, paymentOrder)
                .map(moyenSignatures -> {
                    if (moyenSignatures.stream().filter(vkb -> vkb.getType().equals("KBV")).count() == 0) {
                        Alarme alarme = new Alarme();
                        alarme.setCode("KBV");
                        LOGGER.info("L'abonné {} n'est pas autorisé à signer ce virement avec un KBV.", user.getLogin());
                        TransactionExceptionFactory.wrapNOBCException(alarme, user.getLogin());
                    }
                    MoyenSignature moyenSignature = moyenSignatures.stream().filter(vkb -> vkb.getType().equals("KBV")).findFirst().get();
                    if (!moyenSignature.getAlarmes().isEmpty()) {
                        Alarme alarme = moyenSignature.getAlarmes().get(0);
                        TransactionExceptionFactory.wrapNOBCException(alarme, user.getLogin());
                    }
                    return moyenSignature;
                });
    }

    private Observable<Set<MoyenSignature>> meansOfSignatureOrder(AuthenticatedUser user, PaymentOrder paymentOrder) {
        String login = user.getLogin();
        if (paymentOrder.getCreditAccount().getId().equals(paymentOrder.getCreditAccount().getIban())) {
            return meansOfSignatureOrderIntern(user, paymentOrder);
        }
        return meansOfSignatureOrderExtern(user, paymentOrder);
    }

    private Observable<Set<MoyenSignature>> meansOfSignatureOrderExtern(AuthenticatedUser user, PaymentOrder paymentOrder) {
        String login = user.getLogin();
        TransactionInfo transactionInfo = paymentOrder.getTransactionInfo();
        return authentificationClient.findMoyensSignature(login, CODE_APPLICATION, "EXT",
                transactionInfo.getInstructedAmount().toString(), transactionInfo.getInstructedCurrency(), user.getToken())
                .doOnNext(moyenSignatures -> LOGGER.info("Récupération des moyens de signature {}", login))
                .doOnError(throwable -> throwable.printStackTrace())
                .doOnError(err -> {
                    String msg = String.format("erreur lors de la récupération des moyens de signature du %s", login);
                    NOBCException.throwEfsError(msg, err);
                });
    }

    private Observable<Set<MoyenSignature>> meansOfSignatureOrderIntern(AuthenticatedUser user, PaymentOrder paymentOrder) {
        String login = user.getLogin();
        TransactionInfo transactionInfo = paymentOrder.getTransactionInfo();
        return authentificationClient.findMoyensSignature(login, CODE_APPLICATION, "INT",
                transactionInfo.getInstructedAmount().toString(), transactionInfo.getInstructedCurrency(), user.getToken())
                .doOnNext(moyenSignatures -> LOGGER.info("Récupération des moyens de signature {}", login))
                .doOnError(err -> {
                    String msg = String.format("erreur lors de la récupération des moyens de signature du %s", login);
                    NOBCException.throwEfsError(msg, err);
                });
    }
}
