package com.github.rjansem.microservices.training.profile.domain.efs;

import com.github.rjansem.microservices.training.apisecurity.Authority;
import com.github.rjansem.microservices.training.commons.domain.EfsBean;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Objects;

/**
 * Bean représentant les services d'un utilisateur
 *
 * @author jntakpe
 */
public class Role implements EfsBean {

    private Long id;

    private Authority libelle;

    private String login;

    private String profil;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Authority getLibelle() {
        return libelle;
    }

    public void setLibelle(Authority libelle) {
        this.libelle = libelle;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getProfil() {
        return profil;
    }

    public void setProfil(String profil) {
        this.profil = profil;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Role)) {
            return false;
        }
        Role role = (Role) o;
        return Objects.equals(id, role.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("libelle", libelle)
                .append("login", login)
                .append("profil", profil)
                .toString();
    }
}
