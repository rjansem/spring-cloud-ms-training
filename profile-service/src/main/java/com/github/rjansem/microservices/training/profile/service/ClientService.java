package com.github.rjansem.microservices.training.profile.service;

import com.github.rjansem.microservices.training.exception.NOBCException;
import com.github.rjansem.microservices.training.profile.client.AccountClient;
import com.github.rjansem.microservices.training.profile.domain.pib.Client;
import com.github.rjansem.microservices.training.profile.domain.pib.ClientList;
import com.github.rjansem.microservices.training.profile.mapper.client.RacineToClientMapper;
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
 * Service gérant les {@link Client} PIB
 *
 * @author rjansem
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

    public Observable<ClientList> findClientsWithAccountsByLogin(String userId) {
        Objects.requireNonNull(userId);
        Observable<List<Client>> clients = findClientsByLogin(userId).flatMap(Observable::from)
                .flatMap(client -> findAndAddAccountsToClient(userId, client)).toList();
        return Observable.zip(clients, roleService.findAuthoritiesByLogin(userId), ClientList::new);
    }


    private Observable<Client> findAndAddAccountsToClient(String userId, Client client) {
        String codeRacine = client.getClientTechnicalId();
        String login = userId;
        LOGGER.info("Recherche des comptes de l'utilisateur {} avec la racine {}", login, codeRacine);
        return accountClient.findAccountsByRacine(userId, codeRacine)
                .doOnNext(accounts -> LOGGER.info("Récupération de {} compte(s) pour l'utilisateur {} avec la racine {}",
                        accounts.size(), login, codeRacine))
                .doOnError(err -> {
                    String msg = String.format("Impossible de récupérer les comptes de %s avec la racine %s", login, codeRacine);
                    NOBCException.throwEfsError(msg, err);
                })
                .map(accounts -> {
                    if (client.isCreditBulocFlag())
                        return accounts;
                    else
                        return accounts.stream().filter(account -> !account.getType().equals(CREDIT) && !account.getType().equals(ENGAGEMENT_PAR_SIGNATURE)).collect(Collectors.toList());
                })
                .map(client::setAccounts);
    }

    private Observable<Set<Client>> findClientsByLogin(String userId) {
        String login = userId;
        LOGGER.info("Recherche de la liste des clients pour l'utilisateur {}", login);
        return racineService.findByLogin(userId)
                .map(racines -> racines.stream().map(racineToClientMapper::map).collect(Collectors.toSet()));
    }
}
