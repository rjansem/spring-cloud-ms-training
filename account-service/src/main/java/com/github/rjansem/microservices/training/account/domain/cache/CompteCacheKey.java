package com.github.rjansem.microservices.training.account.domain.cache;

import com.github.rjansem.microservices.training.account.domain.efs.compte.Compte;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.StringJoiner;

/**
 * Bean représentant la clé en cache d'un {@link Compte}
 *
 * @author jntakpe
 */
public class CompteCacheKey implements CacheKey {

    private String login;

    private String codeRacine;

    private String codeProfil;

    public CompteCacheKey(String login, String codeRacine, String codeProfil) {
        this.login = login;
        this.codeRacine = codeRacine;
        this.codeProfil = codeProfil;
    }

    @Override
    public String key() {
        return new StringJoiner("_").add(login).add(codeRacine).add(codeProfil).toString();
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

    public String getCodeProfil() {
        return codeProfil;
    }

    public void setCodeProfil(String codeProfil) {
        this.codeProfil = codeProfil;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("login", login)
                .append("codeRacine", codeRacine)
                .append("codeProfil", codeProfil)
                .toString();
    }
}
