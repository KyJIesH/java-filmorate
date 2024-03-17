package ru.yandex.practicum.filmorate.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.Duration;

public class DurationValid implements ConstraintValidator<DurationValidator, Duration> {

    @Override
    public boolean isValid(Duration duration, ConstraintValidatorContext context) {

        if (duration.toMinutes() > 0) {
            return true;
        } else {
            return false;
        }
    }
}
