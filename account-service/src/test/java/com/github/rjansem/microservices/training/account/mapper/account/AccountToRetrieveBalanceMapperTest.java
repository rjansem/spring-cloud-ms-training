package com.github.rjansem.microservices.training.account.mapper.account;

import com.github.rjansem.microservices.training.account.CasDeTestsUtils;
import com.github.rjansem.microservices.training.account.domain.pbi.account.Account;
import com.github.rjansem.microservices.training.account.domain.pbi.account.RetrieveBalance;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by aazzerrifi on 16/02/2017.
 */
public class AccountToRetrieveBalanceMapperTest {

    @Test
    public void map() throws Exception {
        Account input = CasDeTestsUtils.getAccountCC();
        input.setCurrency("USD");
        input.setCountervalueCurrency("USD");
        RetrieveBalance retrieve = new RetrieveBalance();
        retrieve.setType(input.getType());
        retrieve.setTypeId(input.getTypeId());
        retrieve.setSubtype(input.getSubtype());
        retrieve.setSubtypeId(input.getSubtypeId());
        retrieve.setId(input.getId());
        retrieve.setAcountNumber(input.getAccountNumber());
        retrieve.setIban(input.getId());
        retrieve.setBookedBalance(input.getBookedBalance());
        retrieve.setBookedBalanceDate(input.getBookedBalanceDate());
        retrieve.setBookedBalanceCurrency(input.getCurrency());
        if (input.getType().equals("Compte courant") && input.getCreditcardsTotalBalance() != null) {
            retrieve.setCreditcardTotalBalance(input.getCreditcardsTotalBalance());
            retrieve.setCreditcardTotalCurrency(input.getBookedBalanceCurrency());
            retrieve.setCreditcardTotalBalanceDate(input.getCreditcardsTotalDate());
        }
        if (!input.getCurrency().equals("EUR")) {
            retrieve.setCountervalueBalance(input.getCountervalueBalance());
            retrieve.setCountervalueCurrency(input.getCountervalueCurrency());
            retrieve.setCountervalueRate(input.getCountervalueRate());
            input.setCountervalueDate(LocalDateTime.now());
            retrieve.setCountervalueDate(input.getCountervalueDate().toString());
        }
        assertThat(new AccountToRetrieveBalanceMapper().map(input)).isEqualToComparingFieldByField(retrieve);
    }

}