package com.github.rjansem.microservices.training.profile.domain.apigateway;

import com.github.rjansem.microservices.training.apisecurity.Authority;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Bean représentant les informations à insérer dans le token généré par l'API Gateway
 *
 * @author rjansem
 */
public class Token {

    private String login;

    private Set<String> racines = new HashSet<>();

    private Set<Authority> authorities = new HashSet<>();

    public Token() {
    }

    public Token(String login, Set<String> racines, Set<Authority> authorities) {
        this.login = login;
        this.racines = racines;
        this.authorities = authorities;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Set<String> getRacines() {
        return racines;
    }

    public void setRacines(Set<String> racines) {
        this.racines = racines;
    }

    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Token)) {
            return false;
        }
        Token token = (Token) o;
        return Objects.equals(login, token.login);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("login", login)
                .append("racines", racines)
                .append("authorities", authorities)
                .toString();
    }

}
