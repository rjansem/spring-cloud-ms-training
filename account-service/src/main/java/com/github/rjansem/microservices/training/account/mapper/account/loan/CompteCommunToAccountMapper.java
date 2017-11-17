package com.github.rjansem.microservices.training.account.mapper.account.loan;

import com.github.rjansem.microservices.training.account.mapper.account.buloc.EngagementSignatureToAccountMapper;
import com.github.rjansem.microservices.training.account.domain.efs.credit.CompteCommun;
import com.github.rjansem.microservices.training.account.domain.efs.credit.Credit;
import com.github.rjansem.microservices.training.account.domain.efs.credit.EngagementSignature;
import com.github.rjansem.microservices.training.account.domain.pbi.account.Account;
import com.github.rjansem.microservices.training.commons.domain.EfsToPibMapper;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Mapper transformant un {@link CompteCommun} en {@link Account}.
 * Les implémentations {@link CreditToAccountMapper} et {@link EngagementSignatureToAccountMapper} permettent de mapper respectivement
 * les {@link Credit} et {@link EngagementSignature}
 *
 * @param <I> type du compte en entrée provenant d'EFS
 * @author jntakpe
 * @see CompteCommun
 * @see Account
 * @see CreditToAccountMapper
 * @see EngagementSignatureToAccountMapper
 */
public abstract class CompteCommunToAccountMapper<I extends CompteCommun> implements EfsToPibMapper<I, Account> {

    @Override
    public Account map(I input) {
        Objects.requireNonNull(input);
        Account account = getAccount(input);
        account.setId(input.getNumero());
        account.setAccountNumber(input.getNumero());
        account.setSubtype(input.getNature());
        account.setSubtypeId(input.getNature());
        account.setCurrency(input.getCodeDevise());
        account.setNextWriteOffCurrency(input.getCodeDevise());
        if (StringUtils.isNotBlank(input.getCapitalRestantDu())) {
            account.setBookedBalance(new BigDecimal(input.getCapitalRestantDu()));
        }
        account.setBookedBalanceCurrency(account.getCurrency());
        return account;
    }

    /**
     * Récupère les objects spécifiques du compte (crédit ou engagement par signature)
     *
     * @return un compte par des objects spécifiques
     */
    protected abstract Account getAccount(I input);
}
