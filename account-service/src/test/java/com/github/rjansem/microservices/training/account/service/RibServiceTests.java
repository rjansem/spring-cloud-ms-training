package com.github.rjansem.microservices.training.account.service;

import com.github.rjansem.microservices.training.account.domain.pbi.account.Rib;
import com.github.rjansem.microservices.training.commons.testing.CustomTestSubscriber;
import com.github.rjansem.microservices.training.commons.testing.UserUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;
import java.util.StringJoiner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Services associés à la gestion des ribs
 *
 * @author aazzerrifi
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureWireMock(stubs = "classpath:/stubs")
public class RibServiceTests {

    @Autowired
    private RibService ribService;

    @Test
    public void findRibsByLoginAndRacine_shouldThreeFindRibsWithA4() {
        CustomTestSubscriber<Set<Rib>> subscriber = new CustomTestSubscriber<>();
        ribService.findRibsByLoginAndRacine(UserUtils.A4_USER, UserUtils.A4_USER.getRacines().get(0)).subscribe(subscriber);
        assertThat(subscriber.getOnNextEvents().isEmpty());
        Set<Rib> ribs = subscriber.assertNoErrorsThenWaitAndGetValue();
        assertThat(ribs).isNotEmpty().hasSize(3);
        Rib expected = new Rib();
        expected.setType("Compte courant");
        expected.setTypeId("DAV");
        expected.setSubtype("COMPTE A VUE");
        expected.setSubtypeId("CAV");
        expected.setId("FR7630788001002504780000342");
        expected.setAccountNumber("10106895245");
        expected.setIban("FR7630788001002504780000342");
        expected.setBic("NSMBFRPPXXX");
        expected.setBankName("Banque Neuflize OBC - 00100");
        expected.setBankAddress(new StringJoiner(StringUtils.SPACE) //TODO vérifier
                .add("3, avenue Hoche")
                .add("75410 Paris Cedex 08")
                .add("")
                .add("FRANCE")
                .toString());
        expected.setClientLastName("AMIOT FABRICE");
        assertThat(ribs.stream().findFirst().orElseThrow(()-> new IllegalStateException("Impossible")))
                .isEqualToComparingFieldByField(expected);
    }

    @Test
    public void findRibsByLoginAndRacine_shouldFindRibsWithA5() {
        CustomTestSubscriber<Set<Rib>> subscriber = new CustomTestSubscriber<>();
        ribService.findRibsByLoginAndRacine(UserUtils.A5_USER, UserUtils.A5_USER.getRacines().get(0)).subscribe(subscriber);
        assertThat(subscriber.getOnNextEvents().isEmpty());
        Set<Rib> ribs = subscriber.assertNoErrorsThenWaitAndGetValue();
        assertThat(ribs).isNotEmpty().hasSize(2);
        Rib expected = new Rib();
        expected.setType("Compte épargne");
        expected.setTypeId("Epargne");
        expected.setSubtype("PEA");
        expected.setSubtypeId("PEA");
        expected.setId("FR7630788001002504780000353");
        expected.setAccountNumber("00000000003");
        expected.setIban("FR7630788001002504780000353");
        expected.setBic("NSMBFRPPXXX");
        expected.setBankName("Banque Neuflize OBC - 00100");
        expected.setBankAddress(new StringJoiner(StringUtils.SPACE) //TODO vérifier
                .add("3, avenue Hoche")
                .add("75410 Paris Cedex 08")
                .add("")
                .add("FRANCE")
                .toString());
        expected.setClientLastName("AMIOT FABRICE");
        assertThat(ribs.stream().findFirst().orElseThrow(()-> new IllegalStateException("Impossible")))
                .isEqualToComparingFieldByField(expected);
    }

    @Test()
    public void findRibsByLoginAndRacine_shouldFindRibsWithNORIB() {
        CustomTestSubscriber<Set<Rib>> subscriber = new CustomTestSubscriber<>();
        ribService.findRibsByLoginAndRacine(UserUtils.NO_RIB, UserUtils.NO_RIB.getRacines().get(0)).subscribe(subscriber);
        assertThat(subscriber.getOnNextEvents().isEmpty());
    }

    @Test()
    public void findRibsByLoginAndRacine_shouldReturnEmptySetCuzNoRacines() {
        CustomTestSubscriber<Set<Rib>> subscriber = new CustomTestSubscriber<>();
        ribService.findRibsByLoginAndRacine(UserUtils.UNKNOWN_USER,"").subscribe(subscriber);
        assertThat(subscriber.getOnNextEvents().isEmpty());
    }
}
