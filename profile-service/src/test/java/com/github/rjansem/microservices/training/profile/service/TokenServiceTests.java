package com.github.rjansem.microservices.training.profile.service;

import com.github.rjansem.microservices.training.profile.domain.apigateway.Token;
import com.github.rjansem.microservices.training.commons.testing.CustomTestSubscriber;
import com.github.rjansem.microservices.training.commons.testing.UserUtils;
import com.github.rjansem.microservices.training.profile.domain.apigateway.Token;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests associés au service {@link TokenService}
 *
 * @author jntakpe
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureWireMock(stubs = "classpath:/stubs")
public class TokenServiceTests {

    @Autowired
    private TokenService tokenService;

    @Test
    public void findTokenInfosByLogin_shouldReturnEmptyRacinesAndRolesCuzUnknownUser() {
        CustomTestSubscriber<Token> subscriber = new CustomTestSubscriber<>();
        tokenService.findTokenInfosByLogin(UserUtils.UNKNOWN_USER).subscribe(subscriber);
        assertThat(subscriber.getOnNextEvents().isEmpty());
        Token token = subscriber.assertNoErrorsThenWaitAndGetValue();
        assertThat(token).isNotNull();
        assertThat(token.getLogin()).isEqualTo(UserUtils.UNKNOWN_USER.getLogin());
        assertThat(token.getRacines()).isEmpty();
        assertThat(token.getAuthorities()).isEmpty();
    }

    @Test
    public void findTokenInfosByLogin_shouldReturnEmptyRacinesAndRolesCuzUserHaveNone() {
        CustomTestSubscriber<Token> subscriber = new CustomTestSubscriber<>();
        tokenService.findTokenInfosByLogin(UserUtils.NO_X_USER).subscribe(subscriber);
        assertThat(subscriber.getOnNextEvents().isEmpty());
        Token token = subscriber.assertNoErrorsThenWaitAndGetValue();
        assertThat(token).isNotNull();
        assertThat(token.getLogin()).isEqualTo(UserUtils.NO_X_USER.getLogin());
        assertThat(token.getRacines()).isEmpty();
        assertThat(token.getAuthorities()).isEmpty();
    }

    @Test
    public void findTokenInfosByLogin_shouldReturnFullLoginWithRacinesAndRoles() {
        CustomTestSubscriber<Token> subscriber = new CustomTestSubscriber<>();
        tokenService.findTokenInfosByLogin(UserUtils.A1_USER).subscribe(subscriber);
        assertThat(subscriber.getOnNextEvents().isEmpty());
        Token token = subscriber.assertNoErrorsThenWaitAndGetValue();
        assertThat(token).isNotNull();
        assertThat(token.getLogin()).isEqualTo(UserUtils.A1_USER.getLogin());
        assertThat(token.getRacines()).isNotEmpty();
        assertThat(token.getAuthorities()).isNotEmpty();
    }

}
