package com.github.rjansem.microservices.training.account.client;

import com.github.rjansem.microservices.training.account.domain.efs.compte.CompteCourant;
import com.github.rjansem.microservices.training.account.domain.efs.compte.CompteEpargne;
import com.github.rjansem.microservices.training.account.domain.efs.compte.DepotATerme;
import com.github.rjansem.microservices.training.account.domain.efs.investissement.AssuranceVie;
import com.github.rjansem.microservices.training.account.domain.efs.investissement.PortefeuilleTitres;
import com.github.rjansem.microservices.training.apisecurity.EfsFeignClient;
import org.springframework.web.bind.annotation.*;
import rx.Observable;

import java.util.Set;

import static com.github.rjansem.microservices.training.account.client.ClientConstants.*;

/**
 * Repository gérant les manipulations relatives aux comptes
 *
 * @author rjansem
 * @author aazzerrifi
 * @author mbouhamyd
 */
@EfsFeignClient
public interface CompteClient {

    @RequestMapping(value = COMPTES_COURANT, method = RequestMethod.GET)
    Observable<Set<CompteCourant>> findCompteCourantByLoginAndRacine(@RequestParam(LOGIN_PARAM) String login,
                                                                     @RequestParam(CODE_RACINE_PARAM) String codeRacine,
                                                                     @RequestParam(CODE_PROFIL_PARAM) String codeProfil);

    @RequestMapping(value = COMPTES_EPARGNE, method = RequestMethod.GET)
    Observable<Set<CompteEpargne>> findCompteEpargneByLoginAndRacine(@RequestParam(LOGIN_PARAM) String login,
                                                                     @RequestParam(CODE_RACINE_PARAM) String codeRacine,
                                                                     @RequestParam(CODE_PROFIL_PARAM) String codeProfil);

    @RequestMapping(value = DEPOTS_A_TERME, method = RequestMethod.GET)
    Observable<Set<DepotATerme>> findDepotATermeByLoginAndRacine(@RequestParam(LOGIN_PARAM) String login,
                                                                 @RequestParam(CODE_RACINE_PARAM) String codeRacine,
                                                                 @RequestParam(CODE_PROFIL_PARAM) String codeProfil);

    @RequestMapping(value = PORTEFEUILLES_TITRES, method = RequestMethod.GET)
    Observable<Set<PortefeuilleTitres>> findPortefeuilleTitresByLoginAndRacine(@RequestParam(LOGIN_PARAM) String login,
                                                                               @RequestParam(CODE_RACINE_PARAM) String codeRacine,
                                                                               @RequestParam(CODE_PROFIL_PARAM) String codeProfil);

    @RequestMapping(value = PORTEFEUILLES_TITRES_DETAIL, method = RequestMethod.GET)
    Observable<PortefeuilleTitres> findDetailPortefeuilleTitresByLoginAndRacine(@PathVariable(NUM_PARAM) String numero,
                                                                                @RequestParam(LOGIN_PARAM) String login,
                                                                                @RequestParam(CODE_RACINE_PARAM) String codeRacine);


    @RequestMapping(value = ASSURANCES_VIE, method = RequestMethod.GET)
    Observable<Set<AssuranceVie>> findAssuranceVieByLoginAndRacine(@RequestParam(LOGIN_PARAM) String login,
                                                                   @RequestParam(CODE_RACINE_PARAM) String codeRacine);

    @RequestMapping(value = ASSURANCES_VIE_DETAIL, method = RequestMethod.GET)
    Observable<AssuranceVie> findDetailAssuranceVieByLoginAndRacine(@PathVariable(NUM_PARAM) String numero,
                                                                    @RequestParam(LOGIN_PARAM) String login,
                                                                    @RequestParam(CODE_RACINE_PARAM) String codeRacine,
                                                                    @RequestParam(DATE_VALORISATION_PARAM) String datevalorisation);

    @RequestMapping(value = ASSURANCES_VIE_DETAIL, method = RequestMethod.GET)
    Observable<AssuranceVie> findDetailAssuranceVieByLoginAndRacine(@PathVariable(NUM_PARAM) String numero,
                                                                    @RequestParam(LOGIN_PARAM) String login,
                                                                    @RequestParam(CODE_RACINE_PARAM) String codeRacine);

    @RequestMapping(value = ASSURANCES_VIE_NOBC, method = RequestMethod.GET)
    Observable<AssuranceVie> findDetailAssuranceVieNobcByLoginAndRacine(@PathVariable(NUM_PARAM) String numero,
                                                                        @RequestParam(LOGIN_PARAM) String login,
                                                                        @RequestParam(CODE_RACINE_PARAM) String codeRacine,
                                                                        @RequestParam(DATE_VALORISATION_PARAM) String datevalorisation);

    @RequestMapping(value = ASSURANCES_VIE_NOBC, method = RequestMethod.GET)
    Observable<AssuranceVie> findDetailAssuranceVieNobcByLoginAndRacine(@PathVariable(NUM_PARAM) String numero,
                                                                        @RequestParam(LOGIN_PARAM) String login,
                                                                        @RequestParam(CODE_RACINE_PARAM) String codeRacine);


    @RequestMapping(value = COMPTE_DETAIL, method = RequestMethod.GET)
    Observable<CompteCourant> findDetailCompteCourantByIban(@PathVariable(IBAN_PARAM) String iban,
                                                            @RequestParam(LOGIN_PARAM) String login,
                                                            @RequestParam(CODE_RACINE_PARAM) String codeRacine);

    @RequestMapping(value = DAT_DETAIL, method = RequestMethod.GET)
    Observable<DepotATerme> findDetailDatByNbr(@PathVariable(NUM_PARAM) String numero,
                                               @RequestParam(LOGIN_PARAM) String login,
                                               @RequestParam(CODE_RACINE_PARAM) String codeRacine);

    @RequestMapping(value = COMPTE_DETAIL, method = RequestMethod.GET)
    Observable<CompteEpargne> findDetailCompteEpargneByIban(@PathVariable(IBAN_PARAM) String iban,
                                                            @RequestParam(LOGIN_PARAM) String login,
                                                            @RequestParam(CODE_RACINE_PARAM) String codeRacine);
}