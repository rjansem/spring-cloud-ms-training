package com.github.rjansem.microservices.training.account.service;

import com.github.rjansem.microservices.training.account.client.ClientConstants;
import com.github.rjansem.microservices.training.account.client.CompteClient;
import com.github.rjansem.microservices.training.account.domain.efs.compte.CompteCourant;
import com.github.rjansem.microservices.training.account.domain.efs.compte.CompteEpargne;
import com.github.rjansem.microservices.training.account.domain.efs.compte.DepotATerme;
import com.github.rjansem.microservices.training.account.domain.pbi.account.Account;
import com.github.rjansem.microservices.training.account.mapper.account.chekingAndSaving.CompteCourantToAccountMapper;
import com.github.rjansem.microservices.training.account.mapper.account.chekingAndSaving.CompteEpargneToAccountMapper;
import com.github.rjansem.microservices.training.account.mapper.account.chekingAndSaving.DepotsATermeToAccountMapper;
import com.github.rjansem.microservices.training.exception.APIException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rx.Observable;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Services associés à la gestion des comptes courant et épargnes
 *
 * @author rjansem
 * @author rjansem
 */
@Service
public class ChekingAndSavingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChekingAndSavingService.class);

    private final CompteClient compteClient;

    @Autowired
    public ChekingAndSavingService(CompteClient compteClient) {
        this.compteClient = compteClient;
    }


    Observable<List<Account>> findAllAccountsByRacine(String userId, String codeRacine) {
        Objects.requireNonNull(userId);
        return Observable.merge(
                findCheckingsAccountsByClientLogin(userId, codeRacine),
                findSavingsAccountsByLogin(userId, codeRacine))
                .toList();
    }

    Observable<Set<Account>> findCheckingsAndSavingsWithoutDATByLoginAndRacine(String userId, String codeRacine) {
        Objects.requireNonNull(userId);
        Observable<Account> csAccsObs = findCheckingsAccountsByClientLogin(userId, codeRacine);
        Observable<Account> savingsAccsObs = findSavingsAccountsByLogin(userId, codeRacine);
        return Observable.merge(csAccsObs, savingsAccsObs).toList().map(HashSet::new);
    }

    Observable<Account> findSavingsAccountsByLogin(String userId, String codeRacine) {
        Objects.requireNonNull(userId);
        String login = userId;
        LOGGER.info("Recherche des comptes épargne pour du client {} avec la racine {}", login, codeRacine);
        CompteEpargneToAccountMapper mapper = new CompteEpargneToAccountMapper();
        Observable<Set<CompteEpargne>> ceFromEfs = comptesEpargneFromEfs(userId, codeRacine, login);
        return ceFromEfs.flatMap(Observable::from).map(mapper::map);
    }

    Observable<Account> findCheckingsAccountsByClientLogin(String userId, String codeRacine) {
        Objects.requireNonNull(userId);
        String login = userId;
        LOGGER.info("Recherche des comptes courant pour du client {} avec la racine {}", login, codeRacine);
        CompteCourantToAccountMapper mapper = new CompteCourantToAccountMapper();
        Observable<Set<CompteCourant>> ccFromEfs = comptesCourantsFromEfs(userId, codeRacine, login);
        return ccFromEfs.flatMap(Observable::from).map(mapper::map);
    }

    Observable<Account> findDepotsATermeAccountsByClientLogin(String userId, String codeRacine) {
        Objects.requireNonNull(userId);
        String login = userId;
        LOGGER.info("Recherche des depots à terme pour le client {} avec la racine {}", login, codeRacine);
        DepotsATermeToAccountMapper mapper = new DepotsATermeToAccountMapper();
        Observable<Set<DepotATerme>> datFromEfs = depotsATermeFromEfs(userId, codeRacine, login);
        return datFromEfs.flatMap(Observable::from).map(mapper::map);
    }

    private Observable<Set<CompteCourant>> comptesCourantsFromEfs(String userId, String codeRacine, String login) {
        return compteClient.findCompteCourantByLoginAndRacine(login, codeRacine, ClientConstants.PP)
                .doOnNext(accs -> LOGGER.info("Récupération de {} compte(s) courant pour le client {} avec la racine {}", accs.size(),
                        login, codeRacine))
                .doOnError(e -> {
                    String msg = String.format("Impossible de récupérer les comptes courant de %s avec la racine %s", login, codeRacine);
                    APIException.throwEfsError(msg, e);
                });
    }

    private Observable<Set<DepotATerme>> depotsATermeFromEfs(String userId, String codeRacine, String login) {
        return compteClient.findDepotATermeByLoginAndRacine(login, codeRacine, ClientConstants.PP)
                .doOnNext(accs -> LOGGER.info("Récupération de {} depot(s) à terme pour le client {} avec la racine {}", accs.size(),
                        login, codeRacine))
                .doOnError(err -> {
                    String msg = String.format("Impossible de récupérer les depots à terme de %s avec la racine %s", login, codeRacine);
                    APIException.throwEfsError(msg, err);
                });
    }

    private Observable<Set<CompteEpargne>> comptesEpargneFromEfs(String userId, String codeRacine, String login) {
        return compteClient.findCompteEpargneByLoginAndRacine(login, codeRacine, ClientConstants.PP)
                .doOnNext(accs -> LOGGER.info("Récupération de {} compte(s) épargne pour le client {} avec la racine {}", accs.size(),
                        login, codeRacine))
                .doOnError(err -> {
                    String msg = String.format("Impossible de récupérer les comptes épargne du client %s avec la racine %s", login,
                            codeRacine);
                    APIException.throwEfsError(msg, err);
                });
    }
}

