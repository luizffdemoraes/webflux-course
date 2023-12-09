package br.com.luizffdemoraes.webfluxcourse.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = { TrimStringValidator.class }) // Indica uma restrição
@Target(FIELD) // Informa o alvo da restrição
@Retention(RUNTIME) // Indica que deve ser mantida em tempo de execução
public @interface TrimString {

    String message() default "field cannot have blank spaces at the beginning or at end";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
