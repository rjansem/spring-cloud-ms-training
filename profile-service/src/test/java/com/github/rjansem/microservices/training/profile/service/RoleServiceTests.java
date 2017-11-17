package com.github.rjansem.microservices.training.profile.service;

import com.github.rjansem.microservices.training.apisecurity.Authority;
import com.github.rjansem.microservices.training.commons.testing.CustomTestSubscriber;
import com.github.rjansem.microservices.training.commons.testing.UserUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests associés au service {@link RoleService}
 *
 * @author jntakpe
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureWireMock(stubs = "classpath:/stubs")
public class RoleServiceTests {

    @Autowired
    private RoleService roleService;

    @Test
    public void findAuthoritiesByLogin_shouldReturnEmptySetCuzUnknownLogin() {
        CustomTestSubscriber<Set<Authority>> subscriber = new CustomTestSubscriber<>();
        roleService.findAuthoritiesByLogin(UserUtils.UNKNOWN_USER).subscribe(subscriber);
        assertThat(subscriber.getOnNextEvents().isEmpty());
        Set<Authority> authorities = subscriber.assertNoErrorsThenWaitAndGetValue();
        assertThat(authorities).isEmpty();
    }

    @Test
    public void findAuthoritiesByLogin_shouldReturnEmptySetCuzNoRacines() {
        CustomTestSubscriber<Set<Authority>> subscriber = new CustomTestSubscriber<>();
        roleService.findAuthoritiesByLogin(UserUtils.NO_X_USER).subscribe(subscriber);
        assertThat(subscriber.getOnNextEvents().isEmpty());
        Set<Authority> authorities = subscriber.assertNoErrorsThenWaitAndGetValue();
        assertThat(authorities).isEmpty();
    }

    @Test
    public void findAuthoritiesByLogin_shouldFindSingleCode() {
        CustomTestSubscriber<Set<Authority>> subscriber = new CustomTestSubscriber<>();
        roleService.findAuthoritiesByLogin(UserUtils.SINGLE_ROLE_USER).subscribe(subscriber);
        assertThat(subscriber.getOnNextEvents().isEmpty());
        Set<Authority> authorities = subscriber.assertNoErrorsThenWaitAndGetValue();
        assertThat(authorities).isNotEmpty().hasSize(1).contains(Authority.CONSULTATION);
    }

    @Test
    public void findAuthoritiesByLogin_shouldFindCoupleCode() {
        CustomTestSubscriber<Set<Authority>> subscriber = new CustomTestSubscriber<>();
        roleService.findAuthoritiesByLogin(UserUtils.COUPLE_ROLE_USER).subscribe(subscriber);
        assertThat(subscriber.getOnNextEvents().isEmpty());
        Set<Authority> authorities = subscriber.assertNoErrorsThenWaitAndGetValue();
        assertThat(authorities).isNotEmpty().hasSize(2).contains(Authority.CONSULTATION, Authority.TRANSACTION);
    }
}
