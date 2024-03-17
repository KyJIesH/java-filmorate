package ru.yandex.practicum.filmorate.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotEmpty;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@NotEmpty
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {LoginValid.class})
public @interface LoginValidator {

    String message() default "В логине не может быть пробелов";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
