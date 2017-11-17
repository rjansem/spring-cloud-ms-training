package com.github.rjansem.microservices.training.account.mapper.account.buloc;

import com.github.rjansem.microservices.training.account.domain.efs.credit.EngagementSignature;
import com.github.rjansem.microservices.training.account.domain.pbi.account.LoanAccount;
import com.github.rjansem.microservices.training.commons.domain.EfsToPibMapper;
import com.github.rjansem.microservices.training.commons.domain.utils.DateUtils;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.Objects;

import static com.github.rjansem.microservices.training.account.domain.efs.compte.CompteType.ENGAGEMENT_PAR_SIGNATURE;

/**
 * Mapper transformant un {@link EngagementSignature} en {@link LoanAccount}
 *
 * @author aazzerrifi
 * @see EngagementSignatureToLoanAccountMapper
 */
public class EngagementSignatureToLoanAccountMapper implements EfsToPibMapper<EngagementSignature, LoanAccount> {

    @Override
    public LoanAccount map(EngagementSignature input) {
        Objects.requireNonNull(input);
        LoanAccount account = new LoanAccount();
        account.setId(input.getNumero());
        account.setAccountNumber(input.getNumero());
        account.setType(ENGAGEMENT_PAR_SIGNATURE.getLibelle());
        account.setTypeId(ENGAGEMENT_PAR_SIGNATURE.getId());
        account.setSubtypeId(input.getNature());
        account.setSubtype(input.getNature());
        account.setOpeningDate(DateUtils.convertEfsDateToLocalDate(input.getDateDebut()));
        account.setExpirationDate(DateUtils.convertEfsDateToLocalDate(input.getMaturite()));
        account.setBookedBalanceCurrency(input.getCodeDevise());
        account.setCurrency(input.getCodeDevise());
        String capitalRestantDu = input.getCapitalRestantDu();
        if (StringUtils.isNotBlank(capitalRestantDu)) {
            account.setBookedBalance(new BigDecimal(capitalRestantDu));
        }
        String montantProchaineCommission = input.getMontantProchaineCommission();
        if (StringUtils.isNotBlank(montantProchaineCommission)) {
            account.setNextAmount(new BigDecimal(montantProchaineCommission));
        }
        String montantEmprunte = input.getMontantEmprunte();
        if (StringUtils.isNotBlank(montantEmprunte)) {
            account.setInitialBalance(new BigDecimal(montantEmprunte));
            account.setInitialBalanceCurrency(input.getCodeDevise());
        }
        String beneficiaire = input.getBeneficiaire();
        if (beneficiaire != null) {
            account.setBeneficiary(beneficiaire);
        }
        return account;
    }
}
