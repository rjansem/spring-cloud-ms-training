package com.github.rjansem.microservices.training.transaction.mapper;


import com.github.rjansem.microservices.training.transaction.mapper.commun.Utils;
import com.github.rjansem.microservices.training.transaction.domain.efs.ordre.CompteSubType;
import com.github.rjansem.microservices.training.transaction.domain.efs.ordre.OrdreDetail;
import com.github.rjansem.microservices.training.transaction.domain.pbi.AddressBook;
import com.github.rjansem.microservices.training.transaction.domain.pbi.transaction.Transaction;
import com.github.rjansem.microservices.training.transaction.mapper.commun.Utils;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;

/**
 * Mapper transformant un ordre en transaction
 *
 * @author mbouhamyd
 */
public class OrdreDetailToTransactionMapper {

    static final String PAYEMENT_MODE_RECURRING = "RECURRING";

    static final String PAYEMENT_PRMANENT_EXTERN = "PERE";

    static final String PAYEMENT_PRMANENT_INTERN = "PERI";

    private static String creditDebitIndicator(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) >= 0) {
            return "CRDT";
        } else {
            return "DBIT";
        }

    }

    public Transaction map(OrdreDetail input, String paymentMode, AddressBook benef) {
        Transaction transaction = new Transaction();
        transaction.setId(input.getId());
        transaction.setStatus(input.getStatut());
        transaction.setAccountId(input.getCompteEmetteur().getIban());
        transaction.setAccountNumber(input.getCompteEmetteur().getNumero());
        transaction.setType(CompteSubType.findLibelleById(input.getCompteEmetteur().getType()).getType());
        transaction.setTypeId(CompteSubType.findLibelleById(input.getCompteEmetteur().getType()).getTypeId());
        transaction.setSubtype(input.getCompteEmetteur().getType());
        transaction.setSubtypeId(CompteSubType.findLibelleById(input.getCompteEmetteur().getType()).getId());
        transaction.setClientName(input.getCompteEmetteur().getIntitule());
        if (input.getDateCreation() != null) {
            transaction.setInitiationDate(Utils.convertEfsDateToPibDate(input.getDateCreation()));
        }
        if (input.getDateExecutionSouhaitee() != null) {
            transaction.setBookingDateTime(Utils.convertEfsDateToPibDate(input.getDateExecutionSouhaitee()));
        }
        if (!input.getBeneficiaires().isEmpty()) {
            transaction.setCounterpartyIban(input.getBeneficiaires().get(0).getIban());
            //TODO à décommenter et supprimer la ligne 78 (demande PBI: mettre type dans counterpartyId solution temporaire)
            //transaction.setCounterpartyId(input.getBeneficiaires().get(0).getIban());
            transaction.setCounterpartyLastName(input.getBeneficiaires().get(0).getLibelle());
        }
        if (StringUtils.isNotBlank(input.getMontant())) {
            BigDecimal amount = new BigDecimal(input.getMontant());
            transaction.setInstructedAmount(amount);
            transaction.setCreditDebitIndicator("DBIT");
        }
        transaction.setInstructedCurrency(input.getCodeDevise());
        transaction.setTransactionType((input.getCodeOperation().isEmpty() || input.getCodeOperation().equals(PAYEMENT_PRMANENT_EXTERN)) ? "Externe" : "Interne");
        transaction.setPaymentMode(paymentMode);

        if (input.getCodeOperation().equals(PAYEMENT_PRMANENT_INTERN) || input.getCodeOperation().equals(PAYEMENT_PRMANENT_EXTERN))
            transaction.setPaymentMode(PAYEMENT_MODE_RECURRING);

        if (!input.getBeneficiaires().isEmpty()) {
            transaction.setDescription(input.getBeneficiaires().stream().findFirst().get().getMotif());
        }
        if (benef!=null) {
            transaction.setCounterpartyAccountNumber(benef.getAccountNumber());
            transaction.setCounterpartyAccountType(benef.getType());
            transaction.setCounterpartyAccountTypeId(benef.getTypeId());
            transaction.setCounterpartySubtype(benef.getSubtype());
            transaction.setCounterpartySubtypeId(benef.getSubtypeId());
            //TODO à supprimer lorsque la ligne 54 est décommentrée (demande PBI : mettre type dans counterpartyId solution temporaire)
            transaction.setCounterpartyId(benef.getSubtype());
        }
        return transaction;
    }
}
