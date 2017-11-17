package com.github.rjansem.microservices.training.account.domain.pbi.account;

import java.util.HashSet;
import java.util.Set;

/**
 * Wrapper de {@link Rib}
 *
 * @author jntakpe
 */
public class RibWrapper {

    private Set<Rib> accounts = new HashSet<>();

    public RibWrapper() {
    }

    public RibWrapper(Set<Rib> accounts) {
        this.accounts = accounts;
    }

    public Set<Rib> getAccounts() {
        return accounts;
    }

    public RibWrapper setAccounts(Set<Rib> accounts) {
        this.accounts = accounts;
        return this;
    }
}
