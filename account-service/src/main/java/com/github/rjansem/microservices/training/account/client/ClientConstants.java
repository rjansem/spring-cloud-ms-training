package com.github.rjansem.microservices.training.account.client;

/**
 * Constantes d'URIs pour les appels aux clients
 *
 * @author rjansem
 */
public final class ClientConstants {

    public static final String PREFIXE_EFS = "/webank/v1.0";

    public static final String PP = "P";

    public static final String EURO = "EUR";

    static final String LOGIN_PARAM = "loginwebabonne";

    static final String CODE_RACINE_PARAM = "coderacine";

    static final String CODE_PROFIL_PARAM = "codeprofil";

    static final String IBAN_PARAM = "iban";

    static final String NUM_PARAM = "numero";

    static final String DATE_VALORISATION_PARAM = "datevalorisation";

    static final String PORTEFEUILLES_TITRES = PREFIXE_EFS + "/portefeuillesTitres";

    static final String PORTEFEUILLES_TITRES_DETAIL = PORTEFEUILLES_TITRES + "/{numero}";

    static final String ASSURANCES_VIE = PREFIXE_EFS + "/assurancesVie";

    static final String ASSURANCES_VIE_DETAIL = ASSURANCES_VIE + "/{numero}";

    static final String DEPOTS_A_TERME = PREFIXE_EFS + "/depotsATerme";

    static final String DAT_DETAIL = DEPOTS_A_TERME + "/{numero}";

    static final String CREDITS = PREFIXE_EFS + "/credits";

    static final String CREDITS_DETAIL = CREDITS + "/{numero}";

    static final String ENGAGEMENTS_PAR_SIGNATURE = PREFIXE_EFS + "/engagementsParSignature";

    static final String ENGAGEMENTS_PAR_SIGNATURE_DETAIL = ENGAGEMENTS_PAR_SIGNATURE + "/{numero}";

    static final String IBAN = "iban";

    static final String ID = "id";

    static final String DEVISE = PREFIXE_EFS + "/devises/{id}";

    static final String OPERATIONS_CARTEBANCAIRE = PREFIXE_EFS + "/cartesBancaires/{id}/operations";

    static final String CARTE_BANCAIRE_DETAIL = PREFIXE_EFS + "/cartesBancaires/{id}";

    private static final String COMPTES = PREFIXE_EFS + "/comptes";

    static final String COMPTE_DETAIL = COMPTES + "/{iban}";

    static final String COMPTES_COURANT = COMPTES + "/courant";

    static final String COMPTES_EPARGNE = COMPTES + "/epargne";

    static final String RIB = COMPTES + "/{iban}/rib";

    static final String CARTE_BANCAIRE_IBAN = COMPTES + "/{iban}/cartesBancaires";

    static final String OPERATIONS_IBAN = COMPTES + "/{iban}/operations";

    static final String RACINE_BY_ID = "/api/racines/{idRacine}";
    static final String ID_RACINE = "idRacine";


}
