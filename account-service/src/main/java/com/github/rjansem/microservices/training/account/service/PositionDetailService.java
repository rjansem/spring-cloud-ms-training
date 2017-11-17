package com.github.rjansem.microservices.training.account.service;

import com.github.rjansem.microservices.training.account.domain.efs.compte.CompteType;
import com.github.rjansem.microservices.training.account.domain.efs.investissement.InvestissementTitre;
import com.github.rjansem.microservices.training.account.domain.pbi.account.Account;
import com.github.rjansem.microservices.training.account.domain.pbi.position.PositionDetail;
import com.github.rjansem.microservices.training.account.mapper.position.AssuranceToPositionDetail;
import com.github.rjansem.microservices.training.account.mapper.position.PortefeuillesToPositionDetail;
import com.github.rjansem.microservices.training.apisecurity.AuthenticatedUser;
import com.github.rjansem.microservices.training.exception.EfsCode;
import com.github.rjansem.microservices.training.exception.NOBCException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rx.Observable;

import java.util.Objects;

/**
 * Service de gestion des positionDetail
 *
 * @author mbouhamyd
 */
@Service
public class PositionDetailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PositionDetailService.class);

    private static final String EXTERNE_KEY = "Externe";

    private final InvestmentTitresService investmentTitresService;

    private final InvestmentDetailsService investmentDetailsService;

    @Autowired
    public PositionDetailService(InvestmentTitresService investmentTitresService, InvestmentDetailsService investmentDetailsService) {
        this.investmentTitresService = investmentTitresService;
        this.investmentDetailsService = investmentDetailsService;
    }

    public Observable<PositionDetail> findPositionDetail(String codeRacine,
                                                         String investmentId,
                                                         String positionId,
                                                         String investmentType,
                                                         AuthenticatedUser user) {
        Objects.requireNonNull(user);
        Objects.requireNonNull(investmentId);
        Objects.requireNonNull(investmentType);
        Objects.requireNonNull(positionId);
        if (CompteType.PORTEFEUILLE_TITRES.getId().equals(investmentType)) {
            return positionDetailPortfolio(codeRacine, investmentId, positionId, user);
        } else {
            return positionDetailLifeAssurance(codeRacine, investmentId, positionId, user);
        }
    }

    Observable<PositionDetail> positionDetailPortfolio(String codeRacine, String idPortfolio, String idTitre, AuthenticatedUser user) {
        PortefeuillesToPositionDetail mapper = new PortefeuillesToPositionDetail();
        Observable<InvestissementTitre> titre = investmentTitresService.findTitrePortefeuillesById(idPortfolio, idTitre, codeRacine, user)
                .doOnError(err -> {
                    String msg = String.format("le titre %s n'appartient pas au portefeuilles %s ", idTitre, idPortfolio);
                    throw new NOBCException(EfsCode.fromStatus(400))
                            .set(NOBCException.EFS_MESSAGE, msg).set(NOBCException.EFS_CODE, "position.not.found");
                });
        Observable<Account> portefeuilles = investmentDetailsService.findOnePortfolioDetailsByNumero(idPortfolio, user, codeRacine);
        return Observable.zip(titre, portefeuilles, (tit, account) -> mapper.map(account, tit));
    }

    Observable<PositionDetail> positionDetailLifeAssurance(String codeRacine, String idAssurance, String idTitre, AuthenticatedUser user) {
        AssuranceToPositionDetail mapper = new AssuranceToPositionDetail();
        return investmentDetailsService.findLifeInsuranceById(idAssurance, user, codeRacine)
                .flatMap(a -> findInvestTitre(idAssurance, idTitre, codeRacine, user, a.getSubtypeId()).map(i -> mapper.map(a, i)));
    }

    private Observable<InvestissementTitre> findInvestTitre(String idAssurance, String idTitre, String codeRacine, AuthenticatedUser user,
                                                            String typeAssurance) {
        LOGGER.info("Récupérer les titres de l'assurance {} : {}", idAssurance, typeAssurance);
        if (typeAssurance.equals(EXTERNE_KEY)) {
            return investmentTitresService.findTitreAssuranceVieById(idAssurance, idTitre, codeRacine, user)
                    .doOnError(err -> {
                        String msg = String.format("le titre %s  n'appartient pas à l'assurance vie %s ", idTitre, idAssurance);
                        throw new NOBCException(EfsCode.fromStatus(400))
                                .set(NOBCException.EFS_MESSAGE, msg).set(NOBCException.EFS_CODE, "position.not.found");
                    });
        }
        return investmentTitresService.findTitreAssuranceVieNOBCById(idAssurance, idTitre, codeRacine, user)
                .doOnError(err -> {
                    String msg = String.format("le titre %s n'appartient pas à l'assurance vie %s ", idTitre, idAssurance);
                    throw new NOBCException(EfsCode.fromStatus(400))
                            .set(NOBCException.EFS_MESSAGE, msg).set(NOBCException.EFS_CODE, "position.not.found");
                });
    }
}
