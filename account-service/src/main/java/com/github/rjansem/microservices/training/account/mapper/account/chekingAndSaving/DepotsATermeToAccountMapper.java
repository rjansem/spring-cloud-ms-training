package com.github.rjansem.microservices.training.account.mapper.account.chekingAndSaving;

import com.github.rjansem.microservices.training.account.domain.efs.compte.DepotATerme;
import com.github.rjansem.microservices.training.account.domain.pbi.account.Account;
import com.github.rjansem.microservices.training.commons.domain.EfsToPibMapper;
import com.github.rjansem.microservices.training.commons.domain.utils.DateUtils;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.Objects;

import static com.github.rjansem.microservices.training.account.domain.efs.compte.CompteType.DEPOT_A_TERME;

/**
 * Mapper transformant un {@link DepotATerme} en {@link Account}.
 *
 * @author rjansem
 * @see DepotATerme
 * @see Account
 */
public class DepotsATermeToAccountMapper implements EfsToPibMapper<DepotATerme, Account> {

    @Override
    public Account map(DepotATerme input) {
        Objects.requireNonNull(input);
        Account account = new Account();
        account.setId(input.getNumero());
        account.setDate(DateUtils.convertEfsDateToLocalDate(input.getDateFin()));
        if (input.getDevise() != null) {
            account.setCurrency(input.getDevise().toString());
        }
        account.setType(DEPOT_A_TERME.getLibelle());
        account.setTypeId(DEPOT_A_TERME.getId());
        if (StringUtils.isNotBlank(input.getMontant())) {
            account.setBookedBalance(new BigDecimal(input.getMontant()));
        }
        if (input.getDateDebut() != null) {
            account.setBookedBalanceDate(DateUtils.convertEfsDateToPibDate(input.getDateDebut()));
        }
        account.setAccountNumber(input.getNumeroCompte());
        return account;
    }
}
