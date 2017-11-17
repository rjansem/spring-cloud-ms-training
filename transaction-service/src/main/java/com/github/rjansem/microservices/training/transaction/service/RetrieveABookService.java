package com.github.rjansem.microservices.training.transaction.service;

import com.github.rjansem.microservices.training.apisecurity.AuthenticatedUser;
import com.github.rjansem.microservices.training.commons.domain.GenericContent;
import com.github.rjansem.microservices.training.commons.domain.utils.FindCompteType;
import com.github.rjansem.microservices.training.exception.NOBCException;
import com.github.rjansem.microservices.training.transaction.client.BeneficiaireClient;
import com.github.rjansem.microservices.training.transaction.domain.efs.beneficiaires.BeneficiaireBook;
import com.github.rjansem.microservices.training.transaction.domain.efs.beneficiaires.Tier;
import com.github.rjansem.microservices.training.transaction.domain.efs.ordre.CompteOrdre;
import com.github.rjansem.microservices.training.transaction.domain.pbi.AddressBook;
import com.github.rjansem.microservices.training.transaction.mapper.BenefciairesToAddressBookMapper;
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
 * Service pour la gestion des addresses books
 *
 * @author aazzerrifi
 */
@Service
public class RetrieveABookService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RetrieveABookService.class);

    private static final String CODE_APPLICATION = "WBSCT3";

    static final String type_non_connu= FindCompteType.COMPTES_INCONNU.getType();

    private final BeneficiaireClient beneficiaireClient;

    private final DeviseService deviseService;

    private static final String PROFIL_RACINE_PARTICULIER = "P";

    @Autowired
    public RetrieveABookService(BeneficiaireClient beneficiaireClient, DeviseService deviseService){
        this.beneficiaireClient = beneficiaireClient;
        this.deviseService = deviseService;
    }

    public Observable<GenericContent<AddressBook>> findAllBeneficiaires(AuthenticatedUser user) {
        Objects.requireNonNull(user);
        String login = user.getLogin();
        LOGGER.info("Recherche des beneficiares du client {} de l'application ", login);
        return beneficiaireClient.findComptesBeneficiares(login, CODE_APPLICATION, user.getToken())
                .doOnNext(accs -> LOGGER.info("Récupération de {} beneficiares pour le client {} de l'application {}",
                        accs.getComptes().size() + accs.getTiers().size(), login, CODE_APPLICATION))
                .doOnError(err -> {
                    String msg = String.format("Erreur lors de la récupération des bénéficiaires du client %s avec le code de l'application %s", login, CODE_APPLICATION);
                    NOBCException.throwEfsError(msg, err);

                })
                .flatMap(beneficiaireBook -> findRetrieveByBeneciaireBook(beneficiaireBook, user.getToken()));
    }

    private Observable<GenericContent<AddressBook>> findRetrieveByBeneciaireBook(BeneficiaireBook beneficiaireBook, String token) {
        GenericContent<AddressBook> retrieveABook = new GenericContent<AddressBook>();
        Set<CompteOrdre> comptesOrdres = beneficiaireBook.getComptes()
                .stream()
                .filter(comptesBeneficiaire -> comptesBeneficiaire.getProfil().equals(PROFIL_RACINE_PARTICULIER))
                .collect(Collectors.toSet());
        Observable<List<AddressBook>> comptes = getMapCompteOrder(comptesOrdres, token);
        Observable<List<AddressBook>> teirs = getMapTiers(beneficiaireBook.getTiers(), token);
        comptes.map(addressBooks -> addressBooks.addAll(teirs.toBlocking().first()));
        return comptes.zipWith(teirs, (addressBooks, addressBooks2) -> {
            addressBooks.addAll(addressBooks2);
            retrieveABook.setContent(addressBooks);
            return retrieveABook;
        }).onErrorReturn(throwable -> new GenericContent<AddressBook>());
    }

    private Observable<List<AddressBook>> getMapCompteOrder(Set<CompteOrdre> compteOrdres, String token) {
        BenefciairesToAddressBookMapper mapper = new BenefciairesToAddressBookMapper();
        return Observable.just(compteOrdres).flatMap(Observable::from).map(mapper::map)
                .sorted((addressBook, addressBook2) -> addressBook.getBeneficiaryLastname().compareTo(addressBook2.getBeneficiaryLastname()))
                .map(addressBook -> deviseService.getAddressBookWithDevise(addressBook, token))
                .filter(addressBook -> !addressBook.getSubtype().equals(type_non_connu))
                .toList();
    }

    private Observable<List<AddressBook>> getMapTiers(Set<Tier> tiers, String token) {
        BenefciairesToAddressBookMapper mapper = new BenefciairesToAddressBookMapper();
        return Observable.just(tiers).flatMap(Observable::from).map(mapper::map)
                .sorted((addressBook, addressBook2) -> addressBook.getBeneficiaryLastname().compareTo(addressBook2.getBeneficiaryLastname()))
                .map(addressBook -> deviseService.getAddressBookWithDevise(addressBook, token)).toList();
    }
}
