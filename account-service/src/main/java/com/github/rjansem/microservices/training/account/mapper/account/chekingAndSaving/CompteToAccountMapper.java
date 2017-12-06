package com.github.rjansem.microservices.training.account.mapper.account.chekingAndSaving;

import com.github.rjansem.microservices.training.account.domain.efs.compte.Compte;
import com.github.rjansem.microservices.training.account.domain.efs.compte.CompteCourant;
import com.github.rjansem.microservices.training.account.domain.efs.compte.CompteEpargne;
import com.github.rjansem.microservices.training.account.domain.pbi.account.Account;
import com.github.rjansem.microservices.training.commons.domain.EfsToPibMapper;
import com.github.rjansem.microservices.training.commons.domain.utils.DateUtils;
import com.github.rjansem.microservices.training.commons.domain.utils.FindCompteType;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Mapper transformant un {@link Compte} en {@link Account}.
 * Les implémentations {@link CompteCourantToAccountMapper} et {@link CompteEpargneToAccountMapper} permettent de mapper respectivement
 * les {@link CompteCourant} et {@link CompteEpargne}
 *
 * @param <I> type du compte en entrée provenant d'EFS
 * @author rjansem
 * @see Compte
 * @see Account
 * @see CompteCourantToAccountMapper
 * @see CompteEpargneToAccountMapper
 */
public abstract class CompteToAccountMapper<I extends Compte> implements EfsToPibMapper<I, Account> {

    @Override
    public Account map(I input) {
        Objects.requireNonNull(input);
        Account account = getAccount(input);
        account.setId(input.getIban());
        account.setIban(input.getIban());
        account.setAccountNumber(input.getNumero());
        account.setId(input.getIban());
        if(input.getDevise()!=null) {
            account.setCurrency(String.valueOf(input.getDevise()));
        }
        if (input.getDevise() != null) {
            account.setCurrency(String.valueOf(input.getDevise()));
        }
        if (input.getType() != null) {
            FindCompteType compteType = FindCompteType.findLibelleById(input.getType());
            account.setSubtypeId(compteType.getId());
            account.setSubtype(compteType.getLibelle());
        }
        if (StringUtils.isNotBlank(input.getSolde())) {
            account.setBookedBalance(new BigDecimal(input.getSolde()));
        }
        if(StringUtils.isNotBlank(input.getDateSolde())) {
            account.setBookedBalanceDate(DateUtils.convertEfsDateToPibDate(input.getDateSolde()));
        }
        return account;
    }

    /**
     * Récupère le compte (courant ou épargne) (Assurance Vie ou Portefeuille titres) initialiser
     *
     * @return le type d'investissement
     */
    protected abstract Account getAccount(I input);

}
