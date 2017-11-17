package com.github.rjansem.microservices.training.transaction.domain.pbi;

import com.github.rjansem.microservices.training.commons.domain.PbiBean;

import java.util.List;


/**
 * Bean représentant une liste des beneficiaires
 * @author aazzerrifi
 */
public class RetrieveABook implements PbiBean {

    private List<AddressBook> content;

    public RetrieveABook() {
        // for jackson
    }

    public List<AddressBook> getContent() {
        return content;
    }

    public void setContent(List<AddressBook> content) {
        this.content = content;
    }
}
