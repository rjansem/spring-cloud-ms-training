package com.github.rjansem.microservices.training.commons.domain;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * Bean pour mettre le contenu dans une balise content
 *
 * @author mbouhamyd
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GenericContent<T> implements PbiBean {

    private List<T> content;

    public GenericContent() {
        // for jackson
    }

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }
}