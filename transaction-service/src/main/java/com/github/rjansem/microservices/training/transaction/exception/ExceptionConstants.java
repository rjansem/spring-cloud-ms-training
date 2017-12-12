package com.github.rjansem.microservices.training.transaction.exception;

/**
 * Constantes liées aux Exceptions
 *
 * @author rjansem
 */
public class ExceptionConstants{

    final static String TECHNECAL_MSG = "En raison d'un problème technique, le service est momentanément " +
            "indisponible. Nous vous prions de bien vouloir nous en excuser et vous invitons à renouveler " +
            "votre demande de virement ultérieurement.";

    /*
     @liées com.github.rjansem.microservices.training.transaction.exception.NewTransferException
      */
    final static String CODE_EFS_100 = "invalid.right.compte.user";
    final static String CODE_PBI_100 = "invalid.right.compte.user.100";

    final static String CODE_EFS_101 = "invalid.right.beneficiaire.user";
    final static String CODE_PBI_101 = "invalid.right.beneficiaire.user.101";

    final static String CODE_EFS_102 = "invalid.montant.decimal";
    final static String CODE_PBI_102 = "invalid.montant.decimal.102";

    final static String CODE_EFS_103 = "invalid.montant.format";
    final static String CODE_PBI_103 = "invalid.montant.format.103";

    final static String CODE_PBI_104 = "invalid.montant.plafond.104";
    final static String CODE_EFS_104 = "invalid.montant.plafond";

    final static String CODE_EFS_105 = "invalid.reason.format";
    final static String MSG_FORMAT = "Le champ description accepte uniquement  les valeurs alphanumériques" +
            " sans accent ainsi que les caractères spéciaux suivants : / - ? : ( ) . , ' + ou l'espace." +
            " Il peut contenir jusqu'à 140 caractères. ";
    final static String CODE_PBI_105 = "invalid.reason.format.105";

    final static String CODE_EFS_106 = "invalid.reference.format";
    final static String CODE_PBI_106 = "invalid.reference.format.106";

    final static String CODE_EFS_107_FORMAT = "invalid.date.format";
    final static String CODE_EFS_107_EXECUTION = "invalid.date.execution";
    final static String CODE_PBI_107 = "invalid.date.107";

    final static String CODE_EFS_108 = "invalid.devise";
    final static String CODE_PBI_108 = "invalid.devise.108";

    final static String CODE_EFS_109 = "moyen.plafond.cumule.atteint";
    final static String CODE_PBI_109 = "moyen.plafond.cumule.atteint.109";

    final static String CODE_EFS_110 = "moyen.plafond.unitaire.atteint";
    final static String CODE_PBI_110 = "moyen.plafond.unitaire.atteint.110";
    final static String MSG_PBI_PLAFOND = "Le montant saisi dépasse le plafond maximum autorisé sur votre espace privé. " +
            "Pour plus de précisions, veuillez consulter la rubrique \"Aide\". ";

    final static String CODE_EFS_111 = "moyen.indisponible";
    final static String CODE_PBI_111 = "moyen.indisponible.111";

    final static String CODE_EFS_112 = "error.generic";
    final static String CODE_PBI_112 = "error.generic.112";

    final static String CODE_EFS_113 = "invalid.motif.format";
    final static String CODE_PBI_113 = "invalid.motif.format.113";

    final static String CODE_EFS_114 = "invalid.argument";
    final static String CODE_PBI_114 = "invalid.argument.114";

    final static String CODE_EFS_115 = "invalid.right.same.compte";
    final static String CODE_PBI_115 = "invalid.right.same.compte.115";
    final static String MSG_PBI_115 = "Impossible de faire un virement depuis et vers le même compte";


    /*
     @liées com.github.rjansem.microservices.training.transaction.exception.SignTransferException
      */
    final static String CODE_EFS_200 = "invalid.right.ordre.user";
    final static String CODE_PBI_200 = "invalid.right.ordre.user.200";

    final static String CODE_EFS_201 = "moyen.code.invalid";
    final static String CODE_PBI_201 = "moyen.code.invalid.201";

    final static String CODE_EFS_202 = "moyen.code.initial";
    final static String CODE_PBI_202 = "moyen.code.initial.202";

    final static String CODE_EFS_203 = "moyen.code.initial";
    final static String CODE_PBI_203 = "moyen.code.initial.203";

    final static String CODE_EFS_204 = "moyen.bloque";
    final static String CODE_PBI_204 = "moyen.bloque.204";

    final static String CODE_EFS_205 = "err.sign.generic";
    final static String CODE_PBI_205 = "err.sign.generic.205";
    final static String CODE_EFS_205_1 = "err.sign.generic.1";
    final static String CODE_EFS_205_3 = "err.sign.generic.3";
    final static String CODE_EFS_205_4 = "err.sign.generic.4";
    final static String CODE_EFS_205_5 = "err.sign.generic.5";
    final static String CODE_EFS_205_6 = "err.sign.generic.6";
    final static String CODE_EFS_205_7 = "err.sign.generic.7";
    final static String CODE_EFS_205_11 = "err.sign.generic.11";
    final static String CODE_EFS_205_12 = "err.sign.generic.12";
    final static String CODE_EFS_205_13 = "err.sign.generic.13";
    final static String CODE_EFS_205_34 = "err.sign.generic.34";
    final static String CODE_EFS_205_38 = "err.sign.generic.38";

    final static String MSG_201 = "Le code de connexion saisi est incorrect. Veuillez le saisir à nouveau.";
    final static String MSG_203 = "Votre code de connexion a expiré. Vous allez être déconnecté de votre espace privé.";
    final static String MSG_204 = "Vous avez saisi 3 fois un code de connexion erroné. Par mesure de sécurité, " +
            "l'accès à votre espace privé est temporairement bloqué. Il sera réactivé au cours de la nuit prochaine. " +
            "Vous allez être déconnecté de votre espace privé.";

    final static String CODE_EFS_206 = "moyen.code.initial.expire";
    final static String CODE_PBI_206 = "moyen.code.initial.expire.206";

    final static String CODE_EFS_300 = "ordre.detail.not.found";
    final static String CODE_PBI_300 = "ordre.detail.not.found.300";
    final static String MSG_PBI_300 = "En raison d'un problème technique, le service est momentanément indisponible.";

}
