package com.github.rjansem.microservices.training.exception;

import java.util.List;

/**
 * Bean renvoyé suite à une erreur
 *
 * @author rjansem
 */
public class ListCodeMessageErrorDTO {

    private List<CodeMessageErrorDTO> errors;

    public ListCodeMessageErrorDTO() {
    }

    public ListCodeMessageErrorDTO(List<CodeMessageErrorDTO> errors) {
        this.errors = errors;
    }

    public List<CodeMessageErrorDTO> getErrors() {
        return errors;
    }

    public void setErrors(List<CodeMessageErrorDTO> errors) {
        this.errors = errors;
    }
}
