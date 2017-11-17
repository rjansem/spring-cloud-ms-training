package com.github.rjansem.microservices.training.account.service;

import com.github.rjansem.microservices.training.account.domain.efs.compte.CompteType;
import com.github.rjansem.microservices.training.account.domain.pbi.account.RetrieveBalance;
import com.github.rjansem.microservices.training.account.mapper.account.AccountToRetrieveBalanceMapper;
import com.github.rjansem.microservices.training.apisecurity.AuthenticatedUser;
import com.github.rjansem.microservices.training.commons.domain.GenericContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rx.Observable;

import javax.validation.ValidationException;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

/**
 * Service pour la gestion des retrieve balance
 *
 * @author mbouhamyd
 */
@Service
public class RetrieveBalanceService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RetrieveBalanceService.class);

    private final CheckingAndSavingDetailsService checkingAndSavingDetailsService;

    private final CartesBancairesService cartesBancairesService;

    @Autowired
    public RetrieveBalanceService(CheckingAndSavingDetailsService checkingAndSavingDetailsService,
                                  CartesBancairesService cartesBancairesService) {
        this.checkingAndSavingDetailsService = checkingAndSavingDetailsService;
        this.cartesBancairesService = cartesBancairesService;
    }

    public Observable<RetrieveBalance> findOneRetrieveBalanceByIdAccount(String accountId,
                                                                         String accountType,
                                                                         AuthenticatedUser user,
                                                                         String codeRacine) {
        Objects.requireNonNull(user);
        Objects.requireNonNull(accountId);
        LOGGER.info("Transformation du compte {} en retrieve balance", accountId);
        AccountToRetrieveBalanceMapper mapper = new AccountToRetrieveBalanceMapper();
        CompteType compteType = CompteType.fromId(accountType);
        if (CompteType.COURANT.getId().equals(accountType)) {
            return checkingAndSavingDetailsService.findOneChekingDetailsByIban(accountId, user, codeRacine)
                    .map(mapper::map)
                    .flatMap(balance -> addCardsToBalance(user, codeRacine, balance));
        } else if (CompteType.EPARGNE.getId().equals(accountType)) {
            return checkingAndSavingDetailsService.findOneSavingDetailsByIban(accountId, user, codeRacine)
                    .map(mapper::map);
        }
        throw new ValidationException(String.format("accountType : %s non valide", accountType));
    }


    public Observable<GenericContent<RetrieveBalance>> findRetrieveBalanceContent(AuthenticatedUser user, String codeRacine) {
        Objects.requireNonNull(user);
        return findRetrieveBalanceByRacine(user, codeRacine)
                .flatMap(ocs -> {
                    GenericContent<RetrieveBalance> retrievesbalance = new GenericContent<RetrieveBalance>();
                    retrievesbalance.setContent(ocs);
                    return Observable.just(retrievesbalance);
                });

    }

    public Observable<List<RetrieveBalance>> findRetrieveBalanceByRacine(AuthenticatedUser user, String codeRacine) {
        Objects.requireNonNull(user);
        LOGGER.info("Recherche des retrieves balances du client {} avec la racine {}", user, codeRacine);
        AccountToRetrieveBalanceMapper mapper = new AccountToRetrieveBalanceMapper();
        Observable<RetrieveBalance> csAccObs = checkingAndSavingDetailsService.findAllChekingsDetailsByIban(user, codeRacine)
                .map(mapper::map)
                .flatMap(balance -> addCardsToBalance(user, codeRacine, balance));
        Observable<RetrieveBalance> sAccObs = checkingAndSavingDetailsService.findAllSavingsDetailsByIban(user, codeRacine)
                .map(mapper::map);
        return Observable.merge(csAccObs, sAccObs)
                .toSortedList();
    }

    private Observable<RetrieveBalance> addCardsToBalance(AuthenticatedUser user, String codeRacine, RetrieveBalance balance) {
        return cartesBancairesService.findAllCCWithDetailByIban(balance.getIban(), codeRacine, user)
                .flatMap(Observable::from)
                .map(c -> c.setCardBalanceDate(balance.getCreditcardTotalBalanceDate()))
                .toList()
                .map(HashSet::new)
                .map(balance::setCreditCards);
    }
}
