package com.github.rjansem.microservices.training.account.service;

import com.github.rjansem.microservices.training.account.client.ProfileClient;
import com.github.rjansem.microservices.training.account.domain.pbi.account.Account;
import com.github.rjansem.microservices.training.account.domain.pbi.account.AccountGroup;
import com.github.rjansem.microservices.training.account.domain.pbi.account.AccountOverview;
import com.github.rjansem.microservices.training.account.mapper.account.AccountsToAccountOverviewMapper;
import com.github.rjansem.microservices.training.apisecurity.AuthenticatedUser;
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
 * @author jntakpe
 * @author aazzerrifi
 */
@Service
public class AccountService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountService.class);

    private final ChekingAndSavingService chekingAndSavingService;

    private final CheckingAndSavingDetailsService checkingAndSavingDetailsService;

    private final InvestmentDetailsService investmentDetailsService;

    private final InvestmentService investissementService;

    private final LoanService loanService;

    private final LoanOverviewDetailsService loanOverviewDetailsService;

    private final BulocDetailsService bulocDetailsService;

    private final BulocService bulocService;

    private final ProfileClient profileClient;

    private AccountsToAccountOverviewMapper accountsToAccountOverviewMapper = new AccountsToAccountOverviewMapper();

    private static final String CREDIT = "Crédit";

    private static final String ENGAGEMENT_PAR_SIGNATURE = "Engagement par signature";

    static final String type_non_connu= FindCompteType.COMPTES_INCONNU.getType();

    @Autowired
    public AccountService(ChekingAndSavingService chekingAndSavingService,
                          CheckingAndSavingDetailsService checkingAndSavingDetailsService,
                          InvestmentService investissementService,
                          InvestmentDetailsService investmentDetailsService,
                          LoanService loanService,
                          LoanOverviewDetailsService loanOverviewDetailsService,
                          BulocDetailsService bulocDetailsService,
                          BulocService bulocService, ProfileClient profileClient) {
        this.chekingAndSavingService = chekingAndSavingService;
        this.checkingAndSavingDetailsService = checkingAndSavingDetailsService;
        this.investissementService = investissementService;
        this.investmentDetailsService = investmentDetailsService;
        this.loanService = loanService;
        this.loanOverviewDetailsService = loanOverviewDetailsService;
        this.bulocService = bulocService;
        this.bulocDetailsService = bulocDetailsService;
        this.profileClient = profileClient;
    }

    public Observable<AccountOverview> findAccountOverviewByLogin(AuthenticatedUser user, String codeRacine) {
        Objects.requireNonNull(user);
        String login = user.getLogin();
        LOGGER.info("Recherche de la synthèse des comptes pour le client {} et la racine {}", login, codeRacine);
        return createGroups(user, codeRacine).flatMap(groups -> {
            LOGGER.info("Aggrégation des différents groups ({}) du client {} avec la racine {}", groups.size(), login, codeRacine);
            return Observable.just(accountsToAccountOverviewMapper.map(groups));
        });
    }

    public Observable<List<Account>> findAllAccountsByLogin(AuthenticatedUser user, String codeRacine) {
        Objects.requireNonNull(user);
        LOGGER.info("Recherche des comptes du client {} avec la racine {}", user.getLogin(), codeRacine);

        return Observable.merge(
                chekingAndSavingService.findAllAccountsByRacine(user, codeRacine),
                investissementService.findAllInvestmentsByLoginAndRacine(user, codeRacine),
                loanService.findAllLoansByLoginAndRacine(user, codeRacine),
                bulocService.findAllBulocsByLogin(user, codeRacine))
                //.toList()
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
                })
                .toList()
                .map(l -> l.stream().flatMap(List::stream).sorted().collect(Collectors.toList()));
    }

    private Observable<List<AccountGroup>> createGroups(AuthenticatedUser user, String codeRacine) {
        Objects.requireNonNull(user);
        String login = user.getLogin();
        LOGGER.info("Recherche des groupes des comptes de l'utilisateur {} avec la racine {}", login, codeRacine);
        Observable<AccountGroup> chekingAndSavingGroup = checkingAndSavingDetailsService.findAccountGroupByRacine(user, codeRacine);
        Observable<AccountGroup> investissementGroup = investmentDetailsService.findAccountGroupByRacine(user, codeRacine);
        // pour ne pas afficher les crédits et les engagements par signatures
        Observable<Boolean> droitCredit = profileClient.findRacineById(codeRacine, user.getToken()).map(racine -> racine.getDroitCredits());
        Observable<AccountGroup> accountGroupObservable = droitCredit.flatMap(flag -> {
            if (!flag) {
                Observable<AccountGroup> loanGroup = Observable.empty();
                Observable<AccountGroup> bulocsGroup = Observable.empty();
                return Observable.merge(loanGroup, bulocsGroup);
            }
            Observable<AccountGroup> loanGroup = loanOverviewDetailsService.findAccountGroupByRacine(user, codeRacine);
            Observable<AccountGroup> bulocsGroup = bulocDetailsService.findAccountGroupByRacine(user, codeRacine);
            return Observable.merge(loanGroup, bulocsGroup);
        });
        return Observable.merge(chekingAndSavingGroup, investissementGroup, accountGroupObservable)
                .toSortedList()
                .map(grps -> grps.stream().filter(group -> !group.getAccounts().isEmpty()).collect(Collectors.toList()));
    }
}

