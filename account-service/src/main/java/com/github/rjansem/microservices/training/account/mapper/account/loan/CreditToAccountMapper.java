package com.github.rjansem.microservices.training.account.mapper.account.loan;

import com.github.rjansem.microservices.training.account.domain.efs.credit.Credit;
import com.github.rjansem.microservices.training.account.domain.pbi.account.Account;
import com.github.rjansem.microservices.training.commons.domain.utils.DateUtils;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.Objects;

import static com.github.rjansem.microservices.training.account.domain.efs.compte.CompteType.CREDIT;

/**
 * Mapper transformant un {@link Credit} en {@link Account}
 *
 * @author rjansem
 * @see CompteCommunToAccountMapper
 */
public class CreditToAccountMapper extends CompteCommunToAccountMapper<Credit> {

    @Override
    protected Account getAccount(Credit input) {
        Objects.requireNonNull(input);
        Account account = new Account();
        account.setTypeId(CREDIT.getId());
        account.setType(CREDIT.getLibelle());
        if (StringUtils.isNotBlank(input.getMontantProchaineEcheance())) {
            account.setNextWriteOffAmount(new BigDecimal(input.getMontantProchaineEcheance()));
        }
        account.setDate(DateUtils.convertEfsDateToLocalDate(input.getDateProchaineEcheance()));
        return account;
    }
}
