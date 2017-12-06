package com.github.rjansem.microservices.training.profile.domain.pib;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.rjansem.microservices.training.commons.domain.PbiBean;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Bean représentant un client PIB
 *
 * @author rjansem
 */
public class Client implements PbiBean {

    @NotEmpty
    private String clientTechnicalId;

    @NotEmpty
    private String lastName;

    private boolean mandateFlag;

    @JsonIgnore
    private boolean creditBulocFlag;

    @JsonIgnore
    private Short poids;

    private List<Account> accounts = new ArrayList<>();

    public Client() {
    }

    public Client(String clientTechnicalId, String lastName, boolean mandateFlag) {
        this.clientTechnicalId = clientTechnicalId;
        this.lastName = lastName;
        this.mandateFlag = mandateFlag;
    }

    public Client(String clientTechnicalId, String lastName, boolean mandateFlag, boolean creditBulocFlag) {
        this.clientTechnicalId = clientTechnicalId;
        this.lastName = lastName;
        this.mandateFlag = mandateFlag;
        this.creditBulocFlag = creditBulocFlag;
    }

    public Client(String clientTechnicalId, String lastName, boolean mandateFlag, boolean creditBulocFlag, Short poids) {
        this.clientTechnicalId = clientTechnicalId;
        this.lastName = lastName;
        this.mandateFlag = mandateFlag;
        this.creditBulocFlag = creditBulocFlag;
        this.poids = poids;
    }

    public String getClientTechnicalId() {
        return clientTechnicalId;
    }

    public Client setClientTechnicalId(String clientTechnicalId) {
        this.clientTechnicalId = clientTechnicalId;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public Client setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public boolean isMandateFlag() {
        return mandateFlag;
    }

    public void setMandateFlag(boolean mandateFlag) {
        this.mandateFlag = mandateFlag;
    }

    public boolean isCreditBulocFlag() {
        return creditBulocFlag;
    }

    public void setCreditBulocFlag(boolean creditBulocFlag) {
        this.creditBulocFlag = creditBulocFlag;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public Client setAccounts(List<Account> accounts) {
        this.accounts = accounts;
        return this;
    }

    public Short getPoids() {
        return poids;
    }

    public void setPoids(Short poids) {
        this.poids = poids;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Client)) {
            return false;
        }
        Client client = (Client) o;
        return Objects.equals(clientTechnicalId, client.clientTechnicalId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientTechnicalId);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("clientTechnicalId", clientTechnicalId)
                .append("lastName", lastName)
                .append("mandateFlag", mandateFlag)
                .append("creditBulocFlag", creditBulocFlag)
                .toString();
    }
}
