package com.github.rjansem.microservices.training.exception;

/**
 * Bean renvoyé suite à une erreur
 *
 * @author rjansem
 * @author aazzerrifi
 */
public class CodeMessageErrorDTO {

    private String code;

    private String label;

    public CodeMessageErrorDTO() {
    }

    public CodeMessageErrorDTO(String code, String label) {
        this.code = code;
        this.label = label;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
