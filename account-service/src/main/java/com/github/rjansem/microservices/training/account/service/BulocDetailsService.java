package com.github.rjansem.microservices.training.account.service;

import com.github.rjansem.microservices.training.account.client.CreditClient;
import com.github.rjansem.microservices.training.account.domain.efs.credit.EngagementSignature;
import com.github.rjansem.microservices.training.account.domain.pbi.account.Account;
import com.github.rjansem.microservices.training.account.domain.pbi.account.AccountGroup;
import com.github.rjansem.microservices.training.account.mapper.account.AccountsToAccountOverviewMapper;
import com.github.rjansem.microservices.training.account.mapper.account.buloc.EngagementSignatureToAccountMapper;
import com.github.rjansem.microservices.training.apisecurity.AuthenticatedUser;
import com.github.rjansem.microservices.training.commons.domain.EfsToPibMapper;
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
public class BulocDetailsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BulocDetailsService.class);

    private final CreditClient creditClient;

    @Autowired
    public BulocDetailsService(CreditClient creditClient) {
        this.creditClient = creditClient;
    }

    Observable<AccountGroup> findAccountGroupByRacine(AuthenticatedUser user, String codeRacine) {
        Objects.requireNonNull(user);
        String login = user.getLogin();
        AccountsToAccountOverviewMapper mapper = new AccountsToAccountOverviewMapper();
        LOGGER.info("Creation du group comptes bulocs pour le client {} et la racine {}", login, codeRacine);
        return findAllBulocDetailsByIban(user, codeRacine)
                .toSortedList()
                .map(mapper::mapBulocs);
    }

    Observable<Account> findAllBulocDetailsByIban(AuthenticatedUser user, String codeRacine) {
        Objects.requireNonNull(user);
        String login = user.getLogin();
        LOGGER.info("Recherche des engagements par signature détaillés pour le client {} avec la racine {}", login, codeRacine);
        return creditClient.findEngagementSignatureByLoginAndRacine(login, codeRacine, user.getToken())
                .doOnNext(accs -> LOGGER.info("Récupération des détails de {} engagement par signature du client {} avec la racine {}",
                        accs.size(), login, codeRacine))
                .doOnError(err -> {
                    String msg = String.format("Erreur lors de la récupération des engagements par signature détaillés du client %s de la racine %s",
                            login, codeRacine);
                    NOBCException.throwEfsError(msg, err);
                })
                .flatMap(Observable::from)
                .flatMap(compte -> findOneBulocDetailsByIban(user, compte.getNumero(), codeRacine));
    }

    Observable<Account> findOneBulocDetailsByIban(AuthenticatedUser user, String numero, String codeRacine) {
        Objects.requireNonNull(user);
        String login = user.getLogin();
        LOGGER.info("Recherche du détail d'un engagement par signature pour du client {} avec la racine {}", login, codeRacine);
        EfsToPibMapper<EngagementSignature, Account> mapper = new EngagementSignatureToAccountMapper();
        return creditClient.findDetailEngagementSignatureByLoginAndRacine(numero, login, codeRacine, user.getToken())
                .doOnNext(accs -> LOGGER.info("Récupération du détail d'un  engagement par signature {} pour le client {} avec" +
                        " la racine {}", numero, login, codeRacine))
                .doOnError(err -> {
                    String msg = String.format("Erreur lors de la récupération du détail du l'engagements par signature %s du client %s de la racine %s",
                            numero, login, codeRacine);
                    NOBCException.throwEfsError(msg, err);
                })
                .map(mapper::map);
    }

}

