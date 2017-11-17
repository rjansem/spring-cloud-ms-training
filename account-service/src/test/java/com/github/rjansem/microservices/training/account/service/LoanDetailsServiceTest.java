package com.github.rjansem.microservices.training.account.service;

import com.github.rjansem.microservices.training.account.CasDeTestsUtils;
import com.github.rjansem.microservices.training.account.domain.pbi.account.LoanAccount;
import com.github.rjansem.microservices.training.commons.testing.CustomTestSubscriber;
import com.github.rjansem.microservices.training.commons.testing.UserUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests associés au service {@link LoanDetailsService}
 *
 * @author aazzerrifi
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureWireMock(stubs = "classpath:/stubs")
public class LoanDetailsServiceTest {

    @Autowired
    LoanDetailsService loanDetailsService;
    LoanAccount loanAccount = CasDeTestsUtils.getLoanAccount_A5R1_0023514();
    LoanAccount loanAccountBuloc = CasDeTestsUtils.getBulocAccount_A5R1_24531();
    LoanAccount loanAccountDat = CasDeTestsUtils.getDatAccount_A5R1_35964();

    @Test
    public void findLoanDetailsByLoanId() throws Exception {
        CustomTestSubscriber<LoanAccount> subscriber = new CustomTestSubscriber<>();
        loanDetailsService.findLoanDetailsByLoanId("0023514", UserUtils.A5_USER.getRacines().get(0), UserUtils.A5_USER)
                .subscribe(subscriber);
        assertThat(subscriber.getOnNextEvents().isEmpty());
        LoanAccount account = subscriber.assertNoErrorsThenWaitAndGetValue();
        assertThat(account).isNotNull();
        assertThat(account).isEqualToComparingFieldByField(loanAccount);
    }

    @Test
    public void findLoanDetailsByLoanId_BULOC() throws Exception {
        CustomTestSubscriber<LoanAccount> subscriber = new CustomTestSubscriber<>();
        loanDetailsService.findLoanDetailsByLoanId("24531", UserUtils.A5_USER.getRacines().get(0), UserUtils.A5_USER)
                .subscribe(subscriber);
        assertThat(subscriber.getOnNextEvents().isEmpty());
        LoanAccount account = subscriber.assertNoErrorsThenWaitAndGetValue();
        assertThat(account).isNotNull();
        assertThat(account).isEqualToComparingFieldByField(loanAccountBuloc);
    }

    @Test
    public void findLoanDetailsByLoanId_DAT() throws Exception {
        CustomTestSubscriber<LoanAccount> subscriber = new CustomTestSubscriber<>();
        loanDetailsService.findLoanDetailsByLoanId("35964", UserUtils.A5_USER.getRacines().get(0), UserUtils.A5_USER)
                .subscribe(subscriber);
        assertThat(subscriber.getOnNextEvents().isEmpty());
        LoanAccount account = subscriber.assertNoErrorsThenWaitAndGetValue();
        assertThat(account).isNotNull();
        assertThat(account).isEqualToComparingFieldByField(loanAccountDat);
    }

    @Test
    public void findLoanDetailsByLoanId_shouldNotFindUserThenReturnEmpty() {
        CustomTestSubscriber<LoanAccount> subscriber = new CustomTestSubscriber<>();
        loanDetailsService.findLoanDetailsByLoanId("23322444", "", UserUtils.UNKNOWN_USER)
                .subscribe(subscriber);
        assertThat(subscriber.getOnNextEvents().isEmpty());
        //LoanAccount accounts = subscriber.assertNoErrorsThenWaitAndGetValue();
        // assertThat(accounts).isEqualToComparingFieldByFieldRecursively(new LoanAccount());
    }

    @Test
    public void findOneLoanByNumero() throws Exception {
        CustomTestSubscriber<LoanAccount> subscriber = new CustomTestSubscriber<>();
        loanDetailsService.findOneLoanByNumero("0023514", UserUtils.A5_USER.getRacines().get(0), UserUtils.A5_USER)
                .subscribe(subscriber);
        assertThat(subscriber.getOnNextEvents().isEmpty());
        LoanAccount account = subscriber.assertNoErrorsThenWaitAndGetValue();
        assertThat(account).isNotNull();
        assertThat(account).isEqualToComparingFieldByField(loanAccount);
    }

    @Test
    public void findOneLoanByNumero_shouldNotFindUserThenReturnEmpty() {
        CustomTestSubscriber<LoanAccount> subscriber = new CustomTestSubscriber<>();
        loanDetailsService.findOneLoanByNumero("", "", UserUtils.UNKNOWN_USER)
                .subscribe(subscriber);
        assertThat(subscriber.getOnNextEvents().isEmpty());
        //LoanAccount accounts = subscriber.assertNoErrorsThenWaitAndGetValue();
        //assertThat(accounts.getId()).isNull();
    }

    @Test
    public void findOneBulocByNum() throws Exception {
        CustomTestSubscriber<LoanAccount> subscriber = new CustomTestSubscriber<>();
        loanDetailsService.findOneBulocByNum("24531", UserUtils.A5_USER.getRacines().get(0), UserUtils.A5_USER)
                .subscribe(subscriber);
        assertThat(subscriber.getOnNextEvents().isEmpty());
        LoanAccount account = subscriber.assertNoErrorsThenWaitAndGetValue();
        assertThat(account).isNotNull();
        assertThat(account).isEqualToComparingFieldByField(loanAccountBuloc);
    }

    @Test
    public void findOneBulocByNum_shouldNotFindUserThenReturnEmpty() {
        CustomTestSubscriber<LoanAccount> subscriber = new CustomTestSubscriber<>();
        loanDetailsService.findOneBulocByNum("", "", UserUtils.UNKNOWN_USER)
                .subscribe(subscriber);
        assertThat(subscriber.getOnNextEvents().isEmpty());
        //LoanAccount accounts = subscriber.assertNoErrorsThenWaitAndGetValue();
        //assertThat(accounts.getAccountNumber()).isNull();
    }

}