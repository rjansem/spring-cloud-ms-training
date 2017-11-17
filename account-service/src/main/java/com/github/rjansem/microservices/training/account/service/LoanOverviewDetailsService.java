package com.github.rjansem.microservices.training.account.service;

import com.github.rjansem.microservices.training.account.client.CreditClient;
import com.github.rjansem.microservices.training.account.domain.efs.credit.Credit;
import com.github.rjansem.microservices.training.account.domain.pbi.account.Account;
import com.github.rjansem.microservices.training.account.domain.pbi.account.AccountGroup;
import com.github.rjansem.microservices.training.account.mapper.account.AccountsToAccountOverviewMapper;
import com.github.rjansem.microservices.training.account.mapper.account.loan.CreditToAccountMapper;
import com.github.rjansem.microservices.training.apisecurity.AuthenticatedUser;
import com.github.rjansem.microservices.training.commons.domain.EfsToPibMapper;
import com.github.rjansem.microservices.training.commons.domain.IdentifiableDomain;
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
public class LoanOverviewDetailsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoanOverviewDetailsService.class);

    private final CreditClient creditClient;

    @Autowired
    public LoanOverviewDetailsService(CreditClient creditClient) {
        this.creditClient = creditClient;
    }

    Observable<AccountGroup> findAccountGroupByRacine(AuthenticatedUser user, String codeRacine) {
        Objects.requireNonNull(user);
        String login = user.getLogin();
        AccountsToAccountOverviewMapper mapper = new AccountsToAccountOverviewMapper();
        LOGGER.info("Creation du group comptes loans pour le client {} et la racine {}", login, codeRacine);
        return findAllLoanDetailsByRacine(user, codeRacine)
                .toSortedList()
                .map(mapper::mapLoans);
    }

    Observable<Account> findAllLoanDetailsByRacine(AuthenticatedUser user, String codeRacine) {
        Objects.requireNonNull(user);
        String login = user.getLogin();
        LOGGER.info("Recherche des credits détaillée pour du client {} avec la racine {}", login, codeRacine);
        return creditClient.findCreditByLoginAndRacine(login, codeRacine, user.getToken())
                .doOnNext(accs -> LOGGER.info("Récupération de {} credit(s) détaillée pour le client {} avec la racine {}",
                        accs.size(), login, codeRacine))
                .doOnError(err -> {
                    String msg = String.format("Impossible de récupérer les crédits détailés du client %s avec la racine %s",
                            login, codeRacine);
                    NOBCException.throwEfsError(msg, err);
                })
                .flatMap(Observable::from)
                .flatMap(compte -> findOneLoanDetailsByIban(compte.getNumero(), user, codeRacine));
    }

    Observable<Account> findOneLoanDetailsByIban(String numero, AuthenticatedUser user, String codeRacine) {
        Objects.requireNonNull(user);
        String login = user.getLogin();
        LOGGER.info("Recherche du détail d'un credit pour du client {} avec la racine {}", login, codeRacine);
        EfsToPibMapper<Credit, Account> mapper = new CreditToAccountMapper();
        return creditClient.findDetailCreditByLoginAndRacine(numero, login, codeRacine, user.getToken())
                .doOnNext(accs -> LOGGER.info("Récupération du détail d'un credit {} pour le client {} avec " +
                        "la racine {}", numero, login, codeRacine))
                .doOnError(err -> {
                    String msg = String.format("Impossible de récupérer le detail du crédit %s du client %s avec la racine %s",
                            numero, login, codeRacine);
                    NOBCException.throwEfsError(msg, err);
                })
                .map(mapper::map)
                .filter(IdentifiableDomain::hasId);
    }

}

