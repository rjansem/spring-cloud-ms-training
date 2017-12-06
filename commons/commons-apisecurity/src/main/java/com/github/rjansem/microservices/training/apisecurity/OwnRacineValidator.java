package com.github.rjansem.microservices.training.apisecurity;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Validateur Bean Validation permettant de vérifier que la racine appartient bien à l'utilisateur courant
 *
 * @author rjansem
 * @see ConstraintValidator
 */
public final class OwnRacineValidator implements ConstraintValidator<OwnRacine, String> {

    @Override
    public void initialize(OwnRacine constraintAnnotation) {
        //No initialization needed
    }

    @Override
    public boolean isValid(String racine, ConstraintValidatorContext context) {
        return SecurityUtils.currentUserHasRacine(racine);
    }
}
