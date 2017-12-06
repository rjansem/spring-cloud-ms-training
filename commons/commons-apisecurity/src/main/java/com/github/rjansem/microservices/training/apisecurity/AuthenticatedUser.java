package com.github.rjansem.microservices.training.apisecurity;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * Bean représentant l'utilisateur courant récupéré à partir du token JWT
 *
 * @author rjansem
 */
public class AuthenticatedUser {

    private final String token;

    private final String login;

    private final List<String> racines;

    //TODO pluriel
    private final Collection<Authority> authorities;

    public AuthenticatedUser(String token, String login, List<String> racines, Collection<Authority> authorities) {
        this.token = toBearer(token);
        this.login = login;
        this.racines = racines;
        this.authorities = authorities;
    }

    public String getToken() {
        return token;
    }

    public String getLogin() {
        return login;
    }

    public List<String> getRacines() {
        return racines;
    }

    public Collection<Authority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AuthenticatedUser)) {
            return false;
        }
        AuthenticatedUser that = (AuthenticatedUser) o;
        return Objects.equals(login, that.login);
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

    private String toBearer(String token) {
        return SecurityConstants.BEARER_PREFIX + token;
    }

}
