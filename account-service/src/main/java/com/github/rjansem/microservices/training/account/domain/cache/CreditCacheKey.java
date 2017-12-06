package com.github.rjansem.microservices.training.account.domain.cache;

import com.github.rjansem.microservices.training.account.domain.efs.credit.Credit;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.StringJoiner;

/**
 * Bean représentant la clé de cache d'un {@link Credit}
 *
 * @author rjansem
 */
public class CreditCacheKey implements CacheKey {

    private String login;

    private String codeRacine;

    public CreditCacheKey(String login, String codeRacine) {
        this.login = login;
        this.codeRacine = codeRacine;
    }

    @Override
    public String key() {
        return new StringJoiner("_").add(login).add(codeRacine).toString();
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getCodeRacine() {
        return codeRacine;
    }

    public void setCodeRacine(String codeRacine) {
        this.codeRacine = codeRacine;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("login", login)
                .append("codeRacine", codeRacine)
                .toString();
    }
}
