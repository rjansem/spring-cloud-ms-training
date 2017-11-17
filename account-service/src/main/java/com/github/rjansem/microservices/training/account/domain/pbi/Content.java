package com.github.rjansem.microservices.training.account.domain.pbi;

import java.util.HashSet;
import java.util.Set;

/**
 * @author mbouhamyd
 */
public class Content<T> {

    private Set<T> content = new HashSet<>();

    public Content() {
        //pour jackson
    }

    public Content(Set<T> content) {
        this.content = content;
    }

    public Set<T> getContent() {
        return content;
    }

    public void setContent(Set<T> content) {
        this.content = content;
    }
}
