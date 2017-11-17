package com.github.rjansem.microservices.training.account.service;

import com.github.rjansem.microservices.training.account.client.OperationClient;
import com.github.rjansem.microservices.training.account.domain.efs.cartebancaire.CarteBancaire;
import com.github.rjansem.microservices.training.account.domain.efs.compte.CompteType;
import com.github.rjansem.microservices.training.account.domain.efs.operation.OperationCarte;
import com.github.rjansem.microservices.training.account.domain.pbi.operation.Operation;
import com.github.rjansem.microservices.training.account.mapper.operation.OperationCarteToOperationMapper;
import com.github.rjansem.microservices.training.account.mapper.operation.OperationCompteToOperationMapper;
import com.github.rjansem.microservices.training.apisecurity.AuthenticatedUser;
import com.github.rjansem.microservices.training.exception.NOBCException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rx.Observable;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service de gestion des opérations
 *
 * @author rjansem
 * @author mbouhamyd
 */
@Service
public class OperationService {

    public static final String EFS_DATE_FORMAT = "dd-MM-yyyy";

    private static final Logger LOGGER = LoggerFactory.getLogger(OperationService.class);

    private final OperationClient operationClient;

    private final DeviseService deviseService;

    private final CartesBancairesService cartesBancairesService;

    @Autowired
    public OperationService(OperationClient operationClient, DeviseService deviseService, CartesBancairesService cartesBancairesService) {
        this.operationClient = operationClient;
        this.deviseService = deviseService;
        this.cartesBancairesService = cartesBancairesService;
    }

    public Observable<Set<Operation>> accountOperationByIban(String iban, String productType, String codeRacine, AuthenticatedUser user) {
        Objects.requireNonNull(user);
        Objects.requireNonNull(iban);
        String login = Objects.requireNonNull(user.getLogin());
        OperationCompteToOperationMapper mapper = new OperationCompteToOperationMapper();
        CompteType compteType = CompteType.fromId(productType);
        if (CompteType.EPARGNE.equals(compteType) || CompteType.COURANT.equals(compteType)) {
            LOGGER.info("Recherche des operations du compte {}", iban);
            return operationClient.findOperationsByIban(iban, login, codeRacine, user.getToken())
                    .doOnError(err -> {
                        String msg = String.format("Impossible de récupérer les opérations du compte %s du client %s de la racine %s",
                                iban, login, codeRacine);
                        NOBCException.throwEfsError(msg, err);
                    })
                    .flatMap(Observable::from)
                    .map(operation -> mapper.map(operation, iban))
                    .toList()
                    .map(HashSet::new)
                    .flatMap(operations -> findTransactionCurrency(user, operations));
        }
        throw new IllegalArgumentException(String.format("Type de compte '%s' invalide", productType));
    }

    public Observable<Set<Operation>> accountOperationForAllCreditCardsByIban(String iban, String codeRacine, AuthenticatedUser user) {
        Objects.requireNonNull(user);
        return cartesBancairesService.findAllCBWithDetailsByIban(iban, codeRacine, user)
                .flatMap(Observable::from)
                .flatMap(c -> findOperationsWithCB(c, codeRacine, user))
                .toList()
                .map(l -> l.stream().flatMap(Set::stream).collect(Collectors.toSet()));
    }

    public Observable<Set<Operation>> accountOperationByIdCard(String idCard, String codeRacine, AuthenticatedUser user) {
        Objects.requireNonNull(user);
        Objects.requireNonNull(idCard);
        Observable<Set<OperationCarte>> operationsCard = findOperationsByIdCard(idCard, codeRacine, user);
        return cartesBancairesService.findDetailCBById(idCard, codeRacine, user)
                .flatMap(cb -> operationsCard.map(o -> addCreditCardToOperation(o, cb)));
    }

    private Observable<Set<Operation>> findOperationsWithCB(CarteBancaire carteBancaire, String codeRacine, AuthenticatedUser user) {
        return findOperationsByIdCard(String.valueOf(carteBancaire.getId()), codeRacine, user)
                .map(op -> addCreditCardToOperation(op, carteBancaire));

    }

    private Observable<Set<OperationCarte>> findOperationsByIdCard(String idCard, String codeRacine, AuthenticatedUser user) {
        Objects.requireNonNull(user);
        Objects.requireNonNull(idCard);
        String login = Objects.requireNonNull(user.getLogin());
        LOGGER.info("Recherche des operations de la carte {}", idCard);
        return operationClient.findOperationsByIdCard(idCard, login, codeRacine, user.getToken())
                .doOnNext(cards -> LOGGER.info("Récupération des operations pour la carte {} {}", idCard, cards))
                .doOnError(err -> {
                    String msg = String.format("Impossible de récupérer les opérations de la CB %s du client %s de la racine %s",
                            idCard, login, codeRacine);
                    NOBCException.throwEfsError(msg, err);
                });
    }

    private Set<Operation> addCreditCardToOperation(Set<OperationCarte> opesCartes, CarteBancaire carteBancaire) {
        OperationCarteToOperationMapper mapper = new OperationCarteToOperationMapper();
        return opesCartes.stream()
                .map(o -> mapper.map(carteBancaire, o))
                .collect(Collectors.toSet());
    }

    private Observable<Set<Operation>> findTransactionCurrency(AuthenticatedUser user, Set<Operation> operations) {
        return operations.stream()
                .findFirst()
                .map(operation -> deviseService.codeDeviseToCurrency(operation.getTransactionCurrency(), user)
                        .map(d -> operations.stream().map(o -> o.setTransactionCurrency(d)).collect(Collectors.toSet())))
                .orElseGet(() -> Observable.just(Collections.emptySet()));
    }

}
