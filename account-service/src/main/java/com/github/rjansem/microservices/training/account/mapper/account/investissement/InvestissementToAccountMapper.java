package com.github.rjansem.microservices.training.account.mapper.account.investissement;

import com.github.rjansem.microservices.training.account.domain.efs.investissement.AssuranceVie;
import com.github.rjansem.microservices.training.account.domain.efs.investissement.Investissement;
import com.github.rjansem.microservices.training.account.domain.efs.investissement.PortefeuilleTitres;
import com.github.rjansem.microservices.training.account.domain.pbi.account.Account;
import com.github.rjansem.microservices.training.commons.domain.EfsToPibMapper;
import com.github.rjansem.microservices.training.commons.domain.utils.DateUtils;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Mapper transformant un {@link Investissement} en {@link Account}.
 * Les impl&eacute;mentations {@link PortefeuillesTitresToAccountMapper} et {@link AssuranceVieToAccountMapper} permettent de mapper respectivement
 * les {@link PortefeuilleTitres} et {@link AssuranceVie}
 *
 * @param <I> type du compte d'investissement en entr&eacute;e provenant d'EFS
 * @author aazzerrifi
 * @see Investissement
 * @see Account
 * @see PortefeuillesTitresToAccountMapper
 * @see AssuranceVieToAccountMapper
 */
public abstract class InvestissementToAccountMapper<I extends Investissement> implements EfsToPibMapper<I, Account> {

    @Override
    public Account map(I input) {
        Objects.requireNonNull(input);
        Account account = getAccount(input);
        account.setId(input.getNumero());
        account.setAccountNumber(input.getNumero());
        if (input.getCodeDevise() != null) {
            account.setCurrency(input.getCodeDevise());
            account.setBookedBalanceCurrency(input.getCodeDevise());
        }
        if (StringUtils.isNotBlank(input.getValorisation())) {
            account.setBookedBalance(new BigDecimal(input.getValorisation()));
        }
        if (input.getDateValorisation() != null) {
            account.setBookedBalanceDate(DateUtils.convertEfsDateToPibDate(input.getDateValorisation()));
        }
        return account;
    }

    /**
     * Récupère le compte d'investissement (Assurance Vie ou Portefeuille titres) initialiser
     *
     * @return le type d'investissement
     */
    protected abstract Account getAccount(I input);


}
