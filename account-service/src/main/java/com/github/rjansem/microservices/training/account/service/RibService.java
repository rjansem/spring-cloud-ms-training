package com.github.rjansem.microservices.training.account.service;

import com.github.rjansem.microservices.training.account.client.RibCompteClient;
import com.github.rjansem.microservices.training.account.domain.pbi.account.Rib;
import com.github.rjansem.microservices.training.account.mapper.account.RibCompteToRibMapper;
import com.github.rjansem.microservices.training.apisecurity.AuthenticatedUser;
import com.github.rjansem.microservices.training.exception.NOBCException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rx.Observable;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


/**
 * Services associés à la gestion des ribs
 *
 * @author aazzerrifi
 */
@Service
public class RibService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RibService.class);

    private final RibCompteClient ribCompteClient;

    private final ChekingAndSavingService chekingAndSavingService;

    RibCompteToRibMapper mapper = new RibCompteToRibMapper();

    @Autowired
    public RibService(RibCompteClient ribCompteClient, ChekingAndSavingService chekingAndSavingService) {
        this.ribCompteClient = ribCompteClient;
        this.chekingAndSavingService = chekingAndSavingService;
    }

    public Observable<Set<Rib>> findRibsByLoginAndRacine(AuthenticatedUser user, String codeRacine) {
        Objects.requireNonNull(user);
        String login = user.getLogin();
        LOGGER.info("Recherche de la liste des clients et de leurs comptes associés pour le login {} et la racine {}", login, codeRacine);
        return chekingAndSavingService.findCheckingsAndSavingsWithoutDATByLoginAndRacine(user, codeRacine)
                .flatMap(Observable::from)
                .flatMap(account -> findRibByIban(user, codeRacine, account.getId()).map(rib -> mapper.mapAccountIntoRib(account, rib)))
                .doOnNext(cs -> LOGGER.info("Récupération des ribs pour le client {} avec la racine {}", login, codeRacine))
                .doOnError(err -> {
                    String msg = String.format("Impossible de récupérer les ribs du client %s de la racine %s", login, codeRacine);
                    NOBCException.throwEfsError(msg, err);
                })
                .filter(rib -> rib.getIban() != null).toList().map(HashSet::new);
    }

    private Observable<Rib> findRibByIban(AuthenticatedUser user, String codeRacine, String iban) {
        String login = user.getLogin();
        LOGGER.info("Recherche du rib {} pour le login {}", iban, login);
        return ribCompteClient.findRibByLoginAndRacine(iban, login, codeRacine, user.getToken())
                .doOnNext(rib -> LOGGER.info("Récupération du rib {} pour le client {} avec la racine {}", iban, login, codeRacine))
                .doOnError(err -> {
                    String msg = String.format("Impossible de récupérer le rib du compte %s du client %s de la racine %s",
                            iban, login, codeRacine);
                    NOBCException.throwEfsError(msg, err);
                })
                .map(mapper::map);
    }
}
