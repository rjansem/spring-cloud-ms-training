package com.github.rjansem.microservices.training.profile.service;

import com.github.rjansem.microservices.training.profile.domain.efs.Racine;
import com.github.rjansem.microservices.training.commons.testing.CustomTestSubscriber;
import com.github.rjansem.microservices.training.commons.testing.UserUtils;
import com.github.rjansem.microservices.training.profile.domain.efs.Racine;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests associés au service {@link RacineService}
 *
 * @author jntakpe
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureWireMock(stubs = "classpath:/stubs")
public class RacineServiceTests {

    @Autowired
    private RacineService racineService;

    @Test
    public void findCodesRacinesByLogin_shouldReturnEmptySetCuzUnknownLogin() {
        CustomTestSubscriber<Set<String>> subscriber = new CustomTestSubscriber<>();
        racineService.findCodesRacinesByLogin(UserUtils.UNKNOWN_USER).subscribe(subscriber);
        assertThat(subscriber.getOnNextEvents().isEmpty());
        Set<String> codesRacines = subscriber.assertNoErrorsThenWaitAndGetValue();
        assertThat(codesRacines).isEmpty();
    }

    @Test
    public void findCodesRacinesByLogin_shouldReturnEmptySetCuzNoRacines() {
        CustomTestSubscriber<Set<String>> subscriber = new CustomTestSubscriber<>();
        racineService.findCodesRacinesByLogin(UserUtils.NO_X_USER).subscribe(subscriber);
        assertThat(subscriber.getOnNextEvents().isEmpty());
        Set<String> codesRacines = subscriber.assertNoErrorsThenWaitAndGetValue();
        assertThat(codesRacines).isEmpty();
    }

    @Test
    public void findCodesRacinesByLogin_shouldFindSingleCode() {
        CustomTestSubscriber<Set<String>> subscriber = new CustomTestSubscriber<>();
        racineService.findCodesRacinesByLogin(UserUtils.SINGLE_RACINE_USER).subscribe(subscriber);
        assertThat(subscriber.getOnNextEvents().isEmpty());
        Set<String> codesRacines = subscriber.assertNoErrorsThenWaitAndGetValue();
        assertThat(codesRacines).isNotEmpty().hasSize(1).contains("A1R1");
    }

    @Test
    public void findCodesRacinesByLogin_shouldFindCoupleCode() {
        CustomTestSubscriber<Set<String>> subscriber = new CustomTestSubscriber<>();
        racineService.findCodesRacinesByLogin(UserUtils.COUPLE_RACINE_USER).subscribe(subscriber);
        assertThat(subscriber.getOnNextEvents().isEmpty());
        Set<String> codesRacines = subscriber.assertNoErrorsThenWaitAndGetValue();
        assertThat(codesRacines).isNotEmpty().hasSize(2).contains("A5R1", "A5R2");
    }

    @Test
    public void findByLogin_shouldReturnEmptySetCuzUnknownLogin() {
        CustomTestSubscriber<Set<Racine>> subscriber = new CustomTestSubscriber<>();
        racineService.findByLogin(UserUtils.UNKNOWN_USER).subscribe(subscriber);
        assertThat(subscriber.getOnNextEvents().isEmpty());
        Set<Racine> racines = subscriber.assertNoErrorsThenWaitAndGetValue();
        assertThat(racines).isEmpty();
    }

    @Test
    public void findByLogin_shouldReturnEmptySetCuzNoRacines() {
        CustomTestSubscriber<Set<Racine>> subscriber = new CustomTestSubscriber<>();
        racineService.findByLogin(UserUtils.NO_X_USER).subscribe(subscriber);
        assertThat(subscriber.getOnNextEvents().isEmpty());
        Set<Racine> racines = subscriber.assertNoErrorsThenWaitAndGetValue();
        assertThat(racines).isEmpty();
    }

    @Test
    public void findByLogin_shouldFindSingleRacine() {
        CustomTestSubscriber<Set<Racine>> subscriber = new CustomTestSubscriber<>();
        racineService.findByLogin(UserUtils.SINGLE_RACINE_USER).subscribe(subscriber);
        assertThat(subscriber.getOnNextEvents().isEmpty());
        Set<Racine> racines = subscriber.assertNoErrorsThenWaitAndGetValue();
        assertThat(racines).isNotEmpty().hasSize(1);
        Racine racine = racines.stream().findAny().orElseThrow(() -> new IllegalStateException("Impossible"));
        assertThat(racine.getCode()).isEqualTo("A1R1");
        assertThat(racine.getTitulaire()).isTrue();
        assertThat(racine.getDematerialisation()).isFalse();
    }

    @Test
    public void findByLogin_shouldFindCoupleRacine() {
        CustomTestSubscriber<Set<Racine>> subscriber = new CustomTestSubscriber<>();
        racineService.findByLogin(UserUtils.COUPLE_RACINE_USER).subscribe(subscriber);
        assertThat(subscriber.getOnNextEvents().isEmpty());
        Set<Racine> racines = subscriber.assertNoErrorsThenWaitAndGetValue();
        assertThat(racines).isNotEmpty().hasSize(2)
                .contains(new Racine("A5R1", null, null), new Racine("A5R2", null, null));
    }

}
