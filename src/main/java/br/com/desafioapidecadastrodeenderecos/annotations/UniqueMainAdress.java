package br.com.desafioapidecadastrodeenderecos.annotations;

import br.com.desafioapidecadastrodeenderecos.annotations.validators.UniqueMainAdressValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueMainAdressValidator.class)
public @interface UniqueMainAdress {
    String message() default "must have one and only one main address";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
