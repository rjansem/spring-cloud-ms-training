package com.github.rjansem.microservices.training.account.service;

import com.github.rjansem.microservices.training.account.client.CompteClient;
import com.github.rjansem.microservices.training.account.client.CreditClient;
import com.github.rjansem.microservices.training.account.domain.efs.credit.Credit;
import com.github.rjansem.microservices.training.account.domain.efs.credit.EngagementSignature;
import com.github.rjansem.microservices.training.account.domain.pbi.account.LoanAccount;
import com.github.rjansem.microservices.training.account.mapper.account.buloc.EngagementSignatureToLoanAccountMapper;
import com.github.rjansem.microservices.training.account.mapper.account.chekingAndSaving.DepotsATermeToLoanAccountMapper;
import com.github.rjansem.microservices.training.account.mapper.account.loan.CreditToLoanAccountMapper;
import com.github.rjansem.microservices.training.apisecurity.AuthenticatedUser;
import com.github.rjansem.microservices.training.commons.domain.EfsToPibMapper;
import com.github.rjansem.microservices.training.exception.EfsCode;
import com.github.rjansem.microservices.training.exception.NOBCException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rx.Observable;

import java.util.Objects;

/**
 * Services associés à la gestion du détail des credits
 *
 * @author aazzerrifi
 */
@Service
public class LoanDetailsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoanDetailsService.class);

    private final CreditClient creditClient;

    private final CompteClient compteClient;

    private final DeviseService deviseService;

    @Autowired
    public LoanDetailsService(CreditClient creditClient, CompteClient compteClient, DeviseService deviseService) {
        this.creditClient = creditClient;
        this.compteClient = compteClient;
        this.deviseService = deviseService;
    }


    public Observable<LoanAccount> findLoanDetailsByLoanId(String loanId, String codeRacine, AuthenticatedUser user) {
        Objects.requireNonNull(loanId);
        Objects.requireNonNull(user);
        return findOneLoanByNumero(loanId, codeRacine, user);
    }

    Observable<LoanAccount> findOneLoanByNumero(String loanId, String codeRacine, AuthenticatedUser user) {
        Objects.requireNonNull(user);
        String login = user.getLogin();
        LOGGER.info("Recherche du détail du credit {} pour du client {} avec la racine {}", loanId, login, codeRacine);
        EfsToPibMapper<Credit, LoanAccount> mapper = new CreditToLoanAccountMapper();
        return creditClient.findDetailCreditByLoginAndRacine(loanId, login, codeRacine, user.getToken())
                .doOnNext(accs -> LOGGER.info("Récupération du détail du credit {} pour le client {} avec " +
                        "la racine {}", loanId, login, codeRacine))
                .map(mapper::map)
                .onErrorReturn(err -> findOneBulocByNum(loanId, codeRacine, user).toBlocking().first());
    }

    Observable<LoanAccount> findOneBulocByNum(String numero, String codeRacine, AuthenticatedUser user) {
        Objects.requireNonNull(user);
        String login = user.getLogin();
        LOGGER.info("Recherche du détail du buloc {} pour du client {} avec la racine {}", numero, login, codeRacine);
        EfsToPibMapper<EngagementSignature, LoanAccount> mapper = new EngagementSignatureToLoanAccountMapper();
        return creditClient.findDetailEngagementSignatureByLoginAndRacine(numero, login, codeRacine, user.getToken())
                .doOnNext(accs -> LOGGER.info("Récupération du détail d'un  engagement par signature {} pour le client {} avec" +
                        " la racine {}", numero, login, codeRacine))
                .map(mapper::map)
                .onErrorReturn(err -> findDetailsOneDatByNum(numero, codeRacine, user).toBlocking().first());
    }

    private Observable<LoanAccount> findDetailsOneDatByNum(String numero, String codeRacine, AuthenticatedUser user) {
        Objects.requireNonNull(user);
        Objects.requireNonNull(numero);
        String login = user.getLogin();
        LOGGER.info("Recherche des détails du DAT {} pour le client {} avec la racine {}", numero, login, codeRacine);
        DepotsATermeToLoanAccountMapper mapper = new DepotsATermeToLoanAccountMapper();
        return compteClient.findDetailDatByNbr(numero, login, codeRacine, user.getToken())
                .doOnNext(accs -> LOGGER.info("Récupération des détails du DAT {} pour le client {} avec la racine {}",
                        numero, login, codeRacine))
                .doOnError(e -> {
                    String msg = String.format("le loan %s non rattaché à l'abonné %s", numero, login);
                    throw new NOBCException(EfsCode.fromStatus(400))
                            .set(NOBCException.EFS_MESSAGE, msg).set(NOBCException.EFS_CODE, "loan.not.found");
                })
                .map(mapper::map)
                .flatMap(a -> addLoanToAccount(a, user));
    }

    private Observable<LoanAccount> addLoanToAccount(LoanAccount account, AuthenticatedUser user) {
        if (account.getCurrency() == null) {
            return Observable.just(account);
        }
        return deviseService.codeDeviseToCurrency(account.getCurrency(), user)
                .map(d -> account.setCurrency(d).setBookedBalanceCurrency(d));
    }
}

