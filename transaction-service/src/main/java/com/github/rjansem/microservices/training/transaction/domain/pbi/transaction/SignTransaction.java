package com.github.rjansem.microservices.training.transaction.domain.pbi.transaction;

import com.github.rjansem.microservices.training.commons.domain.PbiBean;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Bean représentant la signature d'une transaction
 *
 * @author rjansem
 */
public class SignTransaction implements PbiBean {

    @NotNull
    private String encryptedPassword;
    @NotNull
    private String keyboardIdentifier;
    @NotEmpty
    @Size(min = 1, max = 1, message = " doit contenir un seul ordre")
    private List<String> transactionIds;

    public SignTransaction() {
        //for jackson
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    public String getKeyboardIdentifier() {
        return keyboardIdentifier;
    }

    public void setKeyboardIdentifier(String keyboardIdentifier) {
        this.keyboardIdentifier = keyboardIdentifier;
    }

    public List<String> getTransactionIds() {
        return transactionIds;
    }

    public void setTransactionIds(List<String> transactionIds) {
        this.transactionIds = transactionIds;
    }
}
