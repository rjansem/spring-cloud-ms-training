package com.github.rjansem.microservices.training.profile.domain.pib;

import com.github.rjansem.microservices.training.apisecurity.Authority;
import com.github.rjansem.microservices.training.commons.domain.PbiBean;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Bean représentant une liste des clients PIB
 *
 * @author azzerrifi
 */
public class ClientList implements PbiBean {

    private List<Client> clients;

    private Set<Authority> authorisations = new HashSet<>();

    public ClientList() {
        //for jackson
    }

    public ClientList(List<Client> clients, Set<Authority> authorisations) {
        this.clients = clients;
        this.authorisations = authorisations;
    }

    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

    public Set<Authority> getAuthorisations() {
        return authorisations;
    }

    public void setAuthorisations(Set<Authority> authorisations) {
        this.authorisations = authorisations;
    }
}
