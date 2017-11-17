package com.github.rjansem.microservices.training.apisecurity;

import com.github.rjansem.microservices.training.apisecurity.utils.WithMockRacines;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests associés aux utilitaires de sécurité {@link SecurityUtils}
 *
 * @author jntakpe
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class SecurityUtilsTests {

    @Test
    @WithMockRacines
    public void currentUserHasRacine_shouldBeFalseCuzUserWithoutRacines() {
        assertThat(SecurityUtils.currentUserHasRacine("some")).isFalse();
    }

    @Test
    @WithMockRacines("some")
    public void currentUserHasRacine_shouldBeFalseCuzNoRacineParam() {
        assertThat(SecurityUtils.currentUserHasRacine(null)).isFalse();
    }

    @Test
    @WithMockRacines({"some", "another"})
    public void currentUserHasRacine_shouldBeFalseCuzUnknownRacine() {
        assertThat(SecurityUtils.currentUserHasRacine("anotherone")).isFalse();
    }

    @Test
    @WithMockRacines({"some"})
    public void currentUserHasRacine_shouldBeTrueExactlyMatching() {
        assertThat(SecurityUtils.currentUserHasRacine("some")).isTrue();
    }

    @Test
    @WithMockRacines({"some", "another"})
    public void currentUserHasRacine_shouldBeTrueMatchingOneOfMany() {
        assertThat(SecurityUtils.currentUserHasRacine("another")).isTrue();
    }
}
