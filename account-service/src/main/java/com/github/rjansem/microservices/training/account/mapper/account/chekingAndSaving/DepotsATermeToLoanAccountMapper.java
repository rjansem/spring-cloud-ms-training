package com.github.rjansem.microservices.training.account.mapper.account.chekingAndSaving;

import com.github.rjansem.microservices.training.account.domain.efs.compte.DepotATerme;
import com.github.rjansem.microservices.training.account.domain.pbi.account.LoanAccount;
import com.github.rjansem.microservices.training.commons.domain.EfsToPibMapper;
import com.github.rjansem.microservices.training.commons.domain.utils.DateUtils;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.Objects;

import static com.github.rjansem.microservices.training.account.domain.efs.compte.CompteType.DEPOT_A_TERME;

/**
 * Mapper transformant un {@link DepotATerme} en {@link LoanAccount}.
 *
 * @author rjansem
 * @see DepotATerme
 * @see LoanAccount
 */
public class DepotsATermeToLoanAccountMapper implements EfsToPibMapper<DepotATerme, LoanAccount> {

    @Override
    public LoanAccount map(DepotATerme input) {
        Objects.requireNonNull(input);
        LoanAccount account = new LoanAccount();
        account.setId(input.getNumero());
        account.setAccountNumber(input.getNumeroCompte());
        account.setType(DEPOT_A_TERME.getLibelle());
        account.setTypeId(DEPOT_A_TERME.getId());
        account.setOpeningDate(DateUtils.convertEfsDateToLocalDate(input.getDateDebut()));
        account.setExpirationDate(DateUtils.convertEfsDateToLocalDate(input.getDateFin()));
        Integer devise = input.getDevise();
        if (devise != null) {
            account.setBookedBalanceCurrency(devise.toString());
            account.setCurrency(devise.toString());
        }
        String montant = input.getMontant();
        if (StringUtils.isNotBlank(montant)) {
            account.setBookedBalance(new BigDecimal(montant));
        }
        String tauxNominal = input.getTauxNominal();
        if (tauxNominal != null) {
            account.setInterest(tauxNominal);
        }
        String nomTitulaire = input.getNomTitulaire();
        if (nomTitulaire != null) {
            account.setClientLastName(nomTitulaire);
        }
        String numeroCompte = input.getNumeroCompte();
        if (numeroCompte != null) {
            account.setTargetNumber(numeroCompte);
        }
        return account;
    }
}
