package com.github.rjansem.microservices.training.profile.service;

import com.github.rjansem.microservices.training.apisecurity.AuthenticatedUser;
import com.github.rjansem.microservices.training.exception.NOBCException;
import com.github.rjansem.microservices.training.profile.client.AccountClient;
import com.github.rjansem.microservices.training.profile.domain.efs.Abonne;
import com.github.rjansem.microservices.training.profile.domain.pib.Client;
import com.github.rjansem.microservices.training.profile.domain.pib.ClientList;
import com.github.rjansem.microservices.training.profile.mapper.client.RacineToClientMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rx.Observable;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * Service gérant les {@link Client} PIB
 *
 * @author jntakpe
 * @author aazzerrifi
 */
@Service
public class ClientService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientService.class);

    private static final String CREDIT = "Crédit";
    private static final String ENGAGEMENT_PAR_SIGNATURE = "Engagement par signature";

    private final RacineService racineService;

    private final AccountClient accountClient;

    private final RoleService roleService;

    private final RacineToClientMapper racineToClientMapper = new RacineToClientMapper();

    @Autowired
    public ClientService(RacineService racineService, AccountClient accountClient, RoleService roleService) {
        this.racineService = racineService;
        this.accountClient = accountClient;
        this.roleService = roleService;
    }

    public Observable<ClientList> findClientsWithAccountsByLogin(AuthenticatedUser user) {
        Objects.requireNonNull(user);
        Observable<List<Client>> clients = findClientsByLogin(user).flatMap(Observable::from)
                .flatMap(client -> findAndAddAccountsToClient(user, client)).toList()
                .flatMap(clientsNotSorted -> findOwnerRacine(user, clientsNotSorted));
        return Observable.zip(clients, roleService.findAuthoritiesByLogin(user), ClientList::new);
    }

    public Observable<List<Client>> findOwnerRacine(AuthenticatedUser user, List<Client> sortRacine) {
        return racineService.abonneFromEfs(user)
                .map((Abonne abonne) -> {
                    String racine = abonne.getNom() + " " + abonne.getPrenom();
                    LOGGER.info("Trier les comptes de l'utilisateur {}", racine);
                    return sortRacine.stream()
                            .filter(client -> !client.getAccounts().isEmpty())
                            .sorted(clientsListComparator(racine)).collect(Collectors.toList());
                });
    }

    private Comparator<Client> clientsListComparator(String racine) {
        return (o1, o2) -> {
            if (!o1.isMandateFlag() && o2.isMandateFlag()) {
                return -1;
            }
            if (!o1.isMandateFlag() && !o2.isMandateFlag()) {

                if (o1.getPoids() > o2.getPoids()) {
                    return -1;
                }
                if (o1.getPoids() < o2.getPoids()) {
                    return 1;
                }

                if (o1.getPoids() == o2.getPoids()) {
                    if (o1.getAccounts().size() == o2.getAccounts().size()) {
                        return o1.getClientTechnicalId().compareTo(o2.getClientTechnicalId());
                    }
                    if (o1.getAccounts().size() > o2.getAccounts().size()) {
                        return 1;
                    }
                    return 1;
                }
                return o1.getLastName().compareTo(o2.getLastName());

                /*
                if (o1.getLastName().equals(racine) && !o2.getLastName().equals(racine)) {
                    return -1;
                }
                if (!o1.getLastName().equals(racine) && o2.getLastName().equals(racine)) {
                    return 1;
                }
                 if (o1.getLastName().equals(racine) && o2.getLastName().equals(racine)) {
                    if (o1.getAccounts().size() == o2.getAccounts().size()) {
                        return o1.getClientTechnicalId().compareTo(o2.getClientTechnicalId());
                    }
                    if (o1.getAccounts().size() > o2.getAccounts().size()) {
                        return -1;
                    }
                    return 1;
                }
                return o1.getLastName().compareTo(o2.getLastName());
                */
            }
            if (o1.isMandateFlag() && o2.isMandateFlag()) {
                if (o1.getLastName().equals(o2.getLastName())) {
                    return o1.getClientTechnicalId().compareTo(o2.getClientTechnicalId());
                }
                return o1.getLastName().compareTo(o2.getLastName());
            }
            //o1.isMandateFlag() && !o2.isMandateFlag()
            return 1;
        };
    }

    private Observable<Client> findAndAddAccountsToClient(AuthenticatedUser user, Client client) {
        String codeRacine = client.getClientTechnicalId();
        String login = user.getLogin();
        LOGGER.info("Recherche des comptes de l'utilisateur {} avec la racine {}", login, codeRacine);
        return accountClient.findAccountsByRacine(codeRacine, user.getToken())
                .doOnNext(accounts -> LOGGER.info("Récupération de {} compte(s) pour l'utilisateur {} avec la racine {}",
                        accounts.size(), login, codeRacine))
                .doOnError(err -> {
                    String msg = String.format("Impossible de récupérer les comptes de %s avec la racine %s", login, codeRacine);
                    NOBCException.throwEfsError(msg, err);
                })
                .map(accounts -> {
                    if(client.isCreditBulocFlag())
                        return accounts;
                    else
                        return accounts.stream().filter(account -> !account.getType().equals(CREDIT) && !account.getType().equals(ENGAGEMENT_PAR_SIGNATURE)).collect(Collectors.toList());
                })
                .map(client::setAccounts);
    }

    private Observable<Set<Client>> findClientsByLogin(AuthenticatedUser user) {
        String login = user.getLogin();
        LOGGER.info("Recherche de la liste des clients pour l'utilisateur {}", login);
        return racineService.findByLogin(user)
                .map(racines -> racines.stream().map(racineToClientMapper::map).collect(Collectors.toSet()));
    }
}
