package com.github.rjansem.microservices.training.transaction.service;

import com.github.rjansem.microservices.training.apisecurity.AuthenticatedUser;
import com.github.rjansem.microservices.training.commons.domain.GenericContent;
import com.github.rjansem.microservices.training.commons.domain.utils.FindCompteType;
import com.github.rjansem.microservices.training.exception.NOBCException;
import com.github.rjansem.microservices.training.transaction.client.CompteOrdreClient;
import com.github.rjansem.microservices.training.transaction.domain.efs.ordre.CompteOrdre;
import com.github.rjansem.microservices.training.transaction.domain.pbi.account.Account;
import com.github.rjansem.microservices.training.transaction.mapper.CompteOrdreToAccountMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rx.Observable;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service pour la gestion des comptes emetteurs
 *
 * @author mbouhamyd
 */
@Service
public class AccountToDebitService {

    static final String CODE_APPLICATION = "WBSCT3";

    static final String type_non_connu= FindCompteType.COMPTES_INCONNU.getType();

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountToDebitService.class);

    private final CompteOrdreClient compteOrdreClient;

    private final DeviseService deviseService;

    private static final String PROFIL_RACINE_PARTICULIER = "P";

    @Autowired
    public AccountToDebitService(CompteOrdreClient compteOrdreClient, DeviseService deviseService) {
        this.compteOrdreClient = compteOrdreClient;
        this.deviseService = deviseService;
    }

    public Observable<GenericContent<Account>> retrieveAccountToDebit(AuthenticatedUser user) {
        Objects.requireNonNull(user);
        return findAccountToDebit(user)
                .flatMap(ocs -> {
                    GenericContent<Account> retrieveAcctoDebit = new GenericContent<Account>();
                    retrieveAcctoDebit.setContent(ocs);
                    return Observable.just(retrieveAcctoDebit);
                });

    }

    private Observable<List<Account>> findAccountToDebit(AuthenticatedUser user) {
        Objects.requireNonNull(user);
        String login = Objects.requireNonNull(user.getLogin());
        CompteOrdreToAccountMapper mapper = new CompteOrdreToAccountMapper();
        LOGGER.info("Recherche les comptes emetteur du  {}", login);
        return compteOrdreClient.findComptesEmetteurs(login, CODE_APPLICATION, user.getToken())
                .doOnNext(cards -> LOGGER.info("Récupération les comptes emetteurs  {}", login))
                .map(comptes -> filterComptesEmetteursByProfil(comptes))
                .doOnError(err -> {
                    String msg = String.format("erreur lors de la récupération des comptes emetteurs du %s", user.getLogin());
                    NOBCException.throwEfsError(msg, err);
                })
                .flatMap(Observable::from)
                .flatMap(accountToDebit -> findAccountCurrency(user, mapper.map(accountToDebit)))
                .filter(addressBook -> !addressBook.getSubtype().equals(type_non_connu))
                .toList();
    }

    private Set<CompteOrdre> filterComptesEmetteursByProfil(Set<CompteOrdre> comptes) {
        return comptes.stream()
                .filter(compte -> compte.getProfil().equals(PROFIL_RACINE_PARTICULIER))
                .collect(Collectors.toSet());
    }

    private Observable<Account> findAccountCurrency(AuthenticatedUser user, Account accountToDebit) {
        LOGGER.info("Recherche la devise {} du compte emetteur {}", accountToDebit.getCurrency(), accountToDebit.getAccountNumber());
        if(accountToDebit.getCurrency()!=null) {
            return deviseService.codeDeviseToCurrency(Integer.valueOf(accountToDebit.getCurrency()), user.getToken())
                    .map(devise -> {
                        accountToDebit.setCurrency(devise);
                        return accountToDebit;
                    });
        }
        else
            return Observable.just(accountToDebit);
    }

}
