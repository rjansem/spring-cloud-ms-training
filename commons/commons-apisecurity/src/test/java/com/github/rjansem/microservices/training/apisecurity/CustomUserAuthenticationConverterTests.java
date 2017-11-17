package com.github.rjansem.microservices.training.apisecurity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * Tests associés à la création du contexte de sécurité Spring
 *
 * @author jntakpe
 * @see CustomUserAuthenticationConverter
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class CustomUserAuthenticationConverterTests {

    @Autowired
    private CustomJwtAccessTokenConverter customJwtAccessTokenConverter;

    @Test
    public void extractAuthentication_shouldPutUserInAuthentication() {
        CustomUserAuthenticationConverter customUserAuthenticationConverter = new CustomUserAuthenticationConverter();
        Map<String, Object> map = customJwtAccessTokenConverter.decode(CustomJwtAccessTokenConverterTests.TOKEN);
        Authentication authentication = customUserAuthenticationConverter.extractAuthentication(map);
        assertThat(authentication).isInstanceOf(UsernamePasswordAuthenticationToken.class);
        UsernamePasswordAuthenticationToken upAuth = (UsernamePasswordAuthenticationToken) authentication;
        assertThat(upAuth.getPrincipal()).isInstanceOf(AuthenticatedUser.class);
        AuthenticatedUser user = (AuthenticatedUser) upAuth.getPrincipal();
        assertThat(user.getLogin()).isEqualTo("jntakpe");
        assertThat(user.getAuthorities()).containsOnly(Authority.CONSULTATION);
        assertThat(user.getRacines()).containsExactlyInAnyOrder("000007", "000008");
    }

}
