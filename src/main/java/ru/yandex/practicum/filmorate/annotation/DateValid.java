package ru.yandex.practicum.filmorate.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class DateValid implements ConstraintValidator<DateValidator, LocalDate> {

    @Override
    public boolean isValid(LocalDate data, ConstraintValidatorContext context) {

        LocalDate birthdayMovie = LocalDate.of(1895, 12, 28);

        if (data.isAfter(birthdayMovie)) {
            return true;
        } else {
            return false;
        }
    }
}
