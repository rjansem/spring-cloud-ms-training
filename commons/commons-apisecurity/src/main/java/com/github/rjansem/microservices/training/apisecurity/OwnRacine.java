package com.github.rjansem.microservices.training.apisecurity;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotNull;
import java.lang.annotation.*;


/**
 * Annotation permettant d'indiquer qu'une propriété ou un paramètre doit être une racine appartenant à l'utilisateur courant
 *
 * @author jntakpe
 * @see OwnRacineValidator
 */
@NotNull
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Constraint(validatedBy = OwnRacineValidator.class)
public @interface OwnRacine {

    String message() default "La racine '${validatedValue}' n'appartient pas à l'utilisateur connecté";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
