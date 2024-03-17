package ru.yandex.practicum.filmorate.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotNull;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@NotNull
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {DurationValid.class})
public @interface DurationValidator {
    String message() default "Продолжительность фильма должна быть положительной";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
