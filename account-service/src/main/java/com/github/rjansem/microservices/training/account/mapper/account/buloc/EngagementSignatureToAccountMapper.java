package com.github.rjansem.microservices.training.account.mapper.account.buloc;

import com.github.rjansem.microservices.training.account.domain.efs.credit.EngagementSignature;
import com.github.rjansem.microservices.training.account.domain.pbi.account.Account;
import com.github.rjansem.microservices.training.account.mapper.account.loan.CompteCommunToAccountMapper;
import com.github.rjansem.microservices.training.commons.domain.utils.DateUtils;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.Objects;

import static com.github.rjansem.microservices.training.account.domain.efs.compte.CompteType.ENGAGEMENT_PAR_SIGNATURE;

/**
 * Mapper transformant un {@link EngagementSignature} en {@link Account}
 *
 * @author aazzerrifi
 * @see EngagementSignatureToAccountMapper
 */
public class EngagementSignatureToAccountMapper extends CompteCommunToAccountMapper<EngagementSignature> {

    @Override
    protected Account getAccount(EngagementSignature input) {
        Objects.requireNonNull(input);
        Account account = new Account();
        account.setTypeId(ENGAGEMENT_PAR_SIGNATURE.getId());
        account.setType(ENGAGEMENT_PAR_SIGNATURE.getLibelle());
        String montantProchaineCommission = input.getMontantProchaineCommission();
        if (StringUtils.isNotBlank(montantProchaineCommission)) {
            account.setNextWriteOffAmount(new BigDecimal(montantProchaineCommission));
        }
        account.setDate(DateUtils.convertEfsDateToLocalDate(input.getMaturite()));
        return account;
    }

}
