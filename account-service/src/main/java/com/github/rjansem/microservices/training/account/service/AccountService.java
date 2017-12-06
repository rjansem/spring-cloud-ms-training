package com.github.rjansem.microservices.training.account.service;

import com.github.rjansem.microservices.training.account.domain.pbi.account.Account;
import com.github.rjansem.microservices.training.commons.domain.utils.FindCompteType;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rx.Observable;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Services associés à la gestion des comptes
 *
 * @author rjansem
 * @author aazzerrifi
 */
@Service
public class AccountService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountService.class);

    private final ChekingAndSavingService chekingAndSavingService;

    private final InvestmentService investissementService;

    static final String type_non_connu = FindCompteType.COMPTES_INCONNU.getType();

    @Autowired
    public AccountService(ChekingAndSavingService chekingAndSavingService,
                          InvestmentService investissementService) {
        this.chekingAndSavingService = chekingAndSavingService;
        this.investissementService = investissementService;
    }


    public Observable<List<Account>> findAllAccountsByLogin(String userId, String codeRacine) {
        Objects.requireNonNull(userId);
        LOGGER.info("Recherche des comptes du client {} avec la racine {}", userId, codeRacine);

        return chekingAndSavingService.findAllAccountsByRacine(userId, codeRacine)
                //.toList()
                .map(accounts -> {
                    //ne pas afficher les comptes avec un type qui n'est pas définit dans la liste com.github.rjansem.microservices.training.commons.domain.utils
                    return accounts.stream()
                            .filter(account -> {
                                if (StringUtils.isNotBlank(account.getSubtype()))
                                    return !account.getSubtype().equals(type_non_connu) ? true : false;
                                else
                                    return true;
                            })
                            .collect(Collectors.toList());
                })
                .toList()
                .map(l -> l.stream().flatMap(List::stream).sorted().collect(Collectors.toList()));
    }


}

