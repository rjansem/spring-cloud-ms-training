package com.github.rjansem.microservices.training.account.service;

import com.github.rjansem.microservices.training.account.client.CompteClient;
import com.github.rjansem.microservices.training.account.domain.efs.compte.CompteCourant;
import com.github.rjansem.microservices.training.account.domain.efs.compte.CompteType;
import com.github.rjansem.microservices.training.account.domain.efs.investissement.AssuranceVie;
import com.github.rjansem.microservices.training.account.domain.efs.investissement.InvestissementTitre;
import com.github.rjansem.microservices.training.account.domain.pbi.account.Account;
import com.github.rjansem.microservices.training.account.domain.pbi.portfolioAndLI.AssetClass;
import com.github.rjansem.microservices.training.account.domain.pbi.portfolioAndLI.AssociatedAccount;
import com.github.rjansem.microservices.training.account.domain.pbi.portfolioAndLI.InvestissementDetail;
import com.github.rjansem.microservices.training.account.domain.pbi.portfolioAndLI.Position;
import com.github.rjansem.microservices.training.account.mapper.account.chekingAndSaving.CompteCourantToAccountMapper;
import com.github.rjansem.microservices.training.account.mapper.portfolioAndLI.AccountToAssociatedAccountMapper;
import com.github.rjansem.microservices.training.account.mapper.portfolioAndLI.AccountToInvestissementDetailMapper;
import com.github.rjansem.microservices.training.account.mapper.portfolioAndLI.GroupTitresToGroupPositionsMapper;
import com.github.rjansem.microservices.training.account.mapper.portfolioAndLI.InvestissementTitreToPositionMapper;
import com.github.rjansem.microservices.training.apisecurity.AuthenticatedUser;
import com.github.rjansem.microservices.training.commons.domain.EfsToPibMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rx.Observable;

import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * Service de gestion des portfolioAndLI et LI
 *
 * @author mbouhamyd
 */
@Service
public class PortfolioAndLIService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PortfolioAndLIService.class);

    private final InvestmentTitresService investmentTitresService;

    private final CheckingAndSavingDetailsService checkingAndSavingDetailsService;

    private final InvestmentDetailsService investmentDetailsService;

    private final CompteClient compteClient;

    private final DeviseService deviseService;

    @Autowired
    public PortfolioAndLIService(InvestmentTitresService investmentTitresService,
                                 CheckingAndSavingDetailsService checkingAndSavingDetailsService,
                                 InvestmentDetailsService investmentDetailsService, CompteClient compteClient, DeviseService deviseService) {
        this.investmentTitresService = investmentTitresService;
        this.checkingAndSavingDetailsService = checkingAndSavingDetailsService;
        this.investmentDetailsService = investmentDetailsService;
        this.compteClient = compteClient;
        this.deviseService = deviseService;
    }

    public Observable<InvestissementDetail> findPortfolioOrLifeInsurance(String codeRacine, String investmentId, String investmentType, AuthenticatedUser user) {
        Objects.requireNonNull(user);
        Objects.requireNonNull(investmentId);
        Objects.requireNonNull(investmentType);
        if (CompteType.PORTEFEUILLE_TITRES.getId().equals(investmentType)) {
            return findPortfolio(investmentId, codeRacine, user);
        } else {
            return findLifeInsurance(investmentId, codeRacine, user);
        }
    }

    Observable<InvestissementDetail> findLifeInsurance(String numeroAssurance, String codeRacine, AuthenticatedUser user) {
        AccountToInvestissementDetailMapper mapper = new AccountToInvestissementDetailMapper();
        return investmentDetailsService.findLifeInsuranceById(numeroAssurance, user, codeRacine)
                .map(mapper::map)
                .flatMap(assuranceVie -> titresAssuranceVieGroupByClass(assuranceVie, numeroAssurance, codeRacine, user));
    }

    Observable<InvestissementDetail> findPortfolio(String numeroPorfolio, String codeRacine, AuthenticatedUser user) {
        Observable<Set<AssetClass>> titres = titresPortfolioGroupByClass(numeroPorfolio, codeRacine, user);
        Observable<InvestissementDetail> portefeuilles = findPortfolioWithAccountByNumero(numeroPorfolio, codeRacine, user);
        return Observable.zip(titres, portefeuilles, (tit, port) -> port.setAssetClassIdentification(tit));
    }

    private Observable<InvestissementDetail> findPortfolioWithAccountByNumero(String numeroPortfolio, String codeRacine, AuthenticatedUser user) {
        AccountToInvestissementDetailMapper mapper = new AccountToInvestissementDetailMapper();
        return investmentDetailsService.findOnePortfolioDetailsByNumero(numeroPortfolio, user, codeRacine)
                .map(mapper::map)
                .flatMap(portfolio -> findAssociatedAccs(codeRacine, portfolio.getIban(), user).map(portfolio::setAssociatedAccounts));
    }

    private Observable<Set<AssociatedAccount>> findAssociatedAccs(String codeRacine, String iban, AuthenticatedUser user) {
        AccountToAssociatedAccountMapper mapper = new AccountToAssociatedAccountMapper();
        if (iban != null && !iban.isEmpty()) {
            return findOneChekingDetailsByIban(iban, user, codeRacine)
                    .filter(account -> account.getId()!=null)
                    .map(mapper::map)
                    .toList()
                    .map(HashSet::new);
        } else {
            return Observable.just(new HashSet<>());
        }
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
                    String msg = String.format("Erreur lors de la récupération du compte courant %s du client %s avec la racine %s : %s", iban,
                            login, codeRacine, e.getCause());
                    //NOBCException.throwEfsError(msg, e);
                })
                .onErrorReturn(err -> new CompteCourant())
                .map(mapper::map)
                .flatMap(a -> {
                    if(a.getAccountNumber()!=null)
                    return deviseService.addDeviseToAccount(a, user);
                    else
                        return Observable.just(a);
                });
                //.flatMap(a -> findCreditCardCount(user, codeRacine, a).map(a::setCreditcardCount));
    }

    private Observable<Set<AssetClass>> titresPortfolioGroupByClass(String numeroPorfolio, String codeRacine, AuthenticatedUser user) {
        InvestissementTitreToPositionMapper investMapper = new InvestissementTitreToPositionMapper();
        GroupTitresToGroupPositionsMapper titreMapper = new GroupTitresToGroupPositionsMapper();
        return investmentTitresService.findAllTitresPortefeuillesByNumero(numeroPorfolio, codeRacine, user)
                .map(titres -> groupByInvestmentClasse(investMapper, titres))
                .map(titreMapper::mapTitreToPosition);
    }

    private Observable<InvestissementDetail> titresAssuranceVieGroupByClass(InvestissementDetail assurance, String numeroAssurance, String codeRacine, AuthenticatedUser user) {
        InvestissementTitreToPositionMapper investMapper = new InvestissementTitreToPositionMapper();
        GroupTitresToGroupPositionsMapper titreMapper = new GroupTitresToGroupPositionsMapper();
        Observable<Set<InvestissementTitre>> investments;
        if (AssuranceVie.EXTERNE_TYPE.equals(assurance.getSubtypeId())) {
            investments = investmentTitresService.findAllTitresAssuranceVieByNumero(numeroAssurance, codeRacine, user);
        } else {
            investments = investmentTitresService.findAllTitresAssuranceVieNOBCByNumero(numeroAssurance, codeRacine, user);
        }
        return investments
                .map(titres -> groupByInvestmentClasse(investMapper, titres))
                .map(titreMapper::mapTitreToPosition)
                .map(assurance::setAssetClassIdentification);
    }

    private Map<String, Set<Position>> groupByInvestmentClasse(InvestissementTitreToPositionMapper mapper, Set<InvestissementTitre> titres) {
        return titres.stream().sorted().collect(Collectors.groupingBy(InvestissementTitre::getClasse, Collectors.mapping(mapper::map, Collectors.toSet())));
    }
}
