package com.github.rjansem.microservices.training.account.service;

import com.github.rjansem.microservices.training.account.client.CarteBancaireClient;
import com.github.rjansem.microservices.training.account.client.ClientConstants;
import com.github.rjansem.microservices.training.account.client.CompteClient;
import com.github.rjansem.microservices.training.account.domain.efs.compte.CompteCourant;
import com.github.rjansem.microservices.training.account.domain.efs.compte.CompteEpargne;
import com.github.rjansem.microservices.training.account.domain.pbi.account.Account;
import com.github.rjansem.microservices.training.account.domain.pbi.account.AccountGroup;
import com.github.rjansem.microservices.training.account.mapper.account.AccountsToAccountOverviewMapper;
import com.github.rjansem.microservices.training.account.mapper.account.chekingAndSaving.CompteCourantToAccountMapper;
import com.github.rjansem.microservices.training.account.mapper.account.chekingAndSaving.CompteEpargneToAccountMapper;
import com.github.rjansem.microservices.training.account.mapper.account.chekingAndSaving.DepotsATermeToAccountMapper;
import com.github.rjansem.microservices.training.apisecurity.AuthenticatedUser;
import com.github.rjansem.microservices.training.commons.domain.EfsToPibMapper;
import com.github.rjansem.microservices.training.commons.domain.utils.FindCompteType;
import com.github.rjansem.microservices.training.exception.NOBCException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rx.Observable;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static com.github.rjansem.microservices.training.account.client.ClientConstants.PP;

/**
 * Gère les détails des comptes épargne, courant et à terme
 *
 * @author aazzerrifi
 */
@Service
public class CheckingAndSavingDetailsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CheckingAndSavingDetailsService.class);

    private final CompteClient compteClient;

    private final DeviseService deviseService;

    private final CarteBancaireClient carteBancaireClient;

    static final String type_non_connu= FindCompteType.COMPTES_INCONNU.getType();

    @Autowired
    public CheckingAndSavingDetailsService(CompteClient compteClient,
                                           DeviseService deviseService,
                                           CarteBancaireClient carteBancaireClient) {
        this.compteClient = compteClient;
        this.deviseService = deviseService;
        this.carteBancaireClient = carteBancaireClient;
    }

    Observable<AccountGroup> findAccountGroupByRacine(AuthenticatedUser user, String codeRacine) {
        AccountsToAccountOverviewMapper mapper = new AccountsToAccountOverviewMapper();
        return findAllAccountsForThisGroupByUser(user, codeRacine)
                .map(mapper::mapCheckingsAndSavings);
    }

    Observable<List<Account>> findAllAccountsForThisGroupByUser(AuthenticatedUser user, String codeRacine) {
        return Observable.merge(
                findAllChekingsDetailsByIban(user, codeRacine),
                findAllSavingsDetailsByIban(user, codeRacine),
                findDetailsAllDatByIban(user, codeRacine))
                .toSortedList()
                .map(accounts ->{
                    //ne pas afficher les comptes avec un type qui n'est pas définit dans la liste com.github.rjansem.microservices.training.commons.domain.utils
                    return  accounts.stream()
                            .filter(account -> {
                                if (StringUtils.isNotBlank(account.getSubtype()) )
                                    return !account.getSubtype().equals(type_non_connu)? true:false;
                                else
                                    return true;
                            })
                            .collect(Collectors.toList());
                });
    }

    Observable<Account> findAllChekingsDetailsByIban(AuthenticatedUser user, String codeRacine) {
        Objects.requireNonNull(user);
        String login = user.getLogin();
        LOGGER.info("Recherche des comptes courant détaillée pour le client {} avec la racine {}", login, codeRacine);
        return compteClient.findCompteCourantByLoginAndRacine(login, codeRacine, ClientConstants.PP, user.getToken())
                .doOnNext(accs -> LOGGER.info("Récupération des détails de {} compte(s) courant pour le client {} avec la racine {}",
                        accs.size(), login, codeRacine))
                .doOnError(e -> {
                    String msg = String.format("Erreur lors de la récupération des comptes courants détaillés de %s de la racine %s", login,
                            codeRacine);
                    NOBCException.throwEfsError(msg, e);
                })
                .flatMap(Observable::from)
                .flatMap(c -> findOneChekingDetailsByIban(c.getIban(), user, codeRacine));
    }

    Observable<Account> findOneChekingDetailsByIban(String iban, AuthenticatedUser user, String codeRacine) {
        Objects.requireNonNull(user);
        Objects.requireNonNull(iban);
        String login = user.getLogin();
        LOGGER.info("Recherche du détail d'un compte courant pour du client {} avec la racine {}", login, codeRacine);
        EfsToPibMapper<CompteCourant, Account> mapper = new CompteCourantToAccountMapper();
        return compteClient.findDetailCompteCourantByIban(iban, login, codeRacine, user.getToken())
                .doOnNext(accs -> LOGGER.info("Récupération du détail d'un compte courant {} pour le client {} avec la racine {}",
                        iban, login, codeRacine))
                .doOnError(e -> {
                    String msg = String.format("Erreur lors de la récupération du compte courant %s du client %s avec la racine %s", iban,
                            login, codeRacine);
                    NOBCException.throwEfsError(msg, e);
                })
                .map(mapper::map)
                .flatMap(a -> deviseService.addDeviseToAccount(a, user))
                .flatMap(a -> findCreditCardCount(user, codeRacine, a).map(a::setCreditcardCount));
    }

    Observable<Account> findAllSavingsDetailsByIban(AuthenticatedUser user, String codeRacine) {
        Objects.requireNonNull(user);
        String login = user.getLogin();
        LOGGER.info("Recherche des comptes d'épargne détaillée pour du client {} avec la racine {}", login, codeRacine);
        return compteClient.findCompteEpargneByLoginAndRacine(login, codeRacine, ClientConstants.PP, user.getToken())
                .doOnNext(accs -> LOGGER.info("Récupération de {} compte(s) d'épargne détaillée pour le client {} avec la racine {}",
                        accs.size(), login, codeRacine))
                .doOnError(e -> {
                    String msg = String.format("Erreur lors de la récupération des comptes épargnes détaillés de %s avec la racine %s", login,
                            codeRacine);
                    NOBCException.throwEfsError(msg, e);
                })
                .flatMap(Observable::from)
                .flatMap(compte -> findOneSavingDetailsByIban(compte.getIban(), user, codeRacine));
    }

    Observable<Account> findOneSavingDetailsByIban(String iban, AuthenticatedUser user, String codeRacine) {
        Objects.requireNonNull(user);
        Objects.requireNonNull(iban);
        String login = user.getLogin();
        LOGGER.info("Recherche du détail d'un compte épargne pour du client {} avec la racine {}", login, codeRacine);
        EfsToPibMapper<CompteEpargne, Account> mapper = new CompteEpargneToAccountMapper();
        return compteClient.findDetailCompteEpargneByIban(iban, login, codeRacine, user.getToken())
                .doOnNext(accs -> LOGGER.info("Récupération du détail d'un compte épargne {} pour le client {} avec la racine {}",
                        iban, login, codeRacine))
                .doOnError(err -> {
                    String msg = String.format("Erreur lors de la récupération du detail du compte épargne %s du client %s de la racine %s",
                            iban, login, codeRacine);
                    NOBCException.throwEfsError(msg, err);
                })
                .map(mapper::map)
                .flatMap(a -> deviseService.addDeviseToAccount(a, user))
                .flatMap(a -> findCreditCardCount(user, codeRacine, a).map(a::setCreditcardCount));
    }

    Observable<Account> findDetailsAllDatByIban(AuthenticatedUser user, String codeRacine) {
        Objects.requireNonNull(user);
        String login = user.getLogin();
        LOGGER.info("Recherche des DATs détaillée  pour le client {} avec la racine {}", login, codeRacine);
        return compteClient.findDepotATermeByLoginAndRacine(login, codeRacine, ClientConstants.PP, user.getToken())
                .doOnNext(accs -> LOGGER.info("Récupération de {} DATs détaillée  pour le client {} avec la racine {}", accs.size(),
                        login, codeRacine))
                .doOnError(err -> {
                    String msg = String.format("Erreur lors de la récupération des DATs détaillés de %s avec la racine %s", login, codeRacine);
                    NOBCException.throwEfsError(msg, err);
                })
                .flatMap(Observable::from)
                .flatMap(compte -> findDetailsOneDatByNum(compte.getNumero(), user, codeRacine));
    }

    Observable<Account> findDetailsOneDatByNum(String numero, AuthenticatedUser user, String codeRacine) {
        Objects.requireNonNull(user);
        Objects.requireNonNull(numero);
        String login = user.getLogin();
        LOGGER.info("Recherche des détails du DAT {} pour le client {} avec la racine {}", numero, login, codeRacine);
        DepotsATermeToAccountMapper mapper = new DepotsATermeToAccountMapper();
        return compteClient.findDetailDatByNbr(numero, login, codeRacine, user.getToken())
                .doOnNext(accs -> LOGGER.info("Récupération des détails du DAT {} pour le client {} avec la racine {}",
                        numero, login, codeRacine))
                .doOnError(e -> {
                    String msg = String.format("Erreur lors de la récupération du détail du DAT %s du client %s de la racine %s",
                            numero, login, codeRacine);
                    NOBCException.throwEfsError(msg, e);
                })
                .map(mapper::map)
                .flatMap(account -> deviseService.addDeviseToAccount(account, user));
    }

    private Observable<Integer> findCreditCardCount(AuthenticatedUser user, String codeRacine, Account account) {
        String iban = account.getIban();
        return carteBancaireClient.findCarteBancaireByIban(iban, user.getLogin(), codeRacine, user.getToken())
                .doOnNext(cards -> LOGGER.info("Récupération des cartes bancaires pour l'iban {}", iban))
                .doOnError(e -> {
                    String msg = String.format("Erreur lors de la récupération du détail d'une CB avec l'iban %s", iban);
                    NOBCException.throwEfsError(msg, e);
                })
                .map(Set::size);
    }
}