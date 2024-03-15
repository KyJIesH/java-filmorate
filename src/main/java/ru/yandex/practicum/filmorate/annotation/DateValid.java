package ru.yandex.practicum.filmorate.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

@SuppressWarnings("checkstyle:Regexp")
public class DateValid implements ConstraintValidator<DateValidator, LocalDate> {
    LocalDate birthdayMovie = LocalDate.of(1895, 12, 28);

    @Override
    public boolean isValid(LocalDate data, ConstraintValidatorContext context) {

        if (data.isAfter(birthdayMovie)) {
            return true;
        } else {
            return false;
        }
    }
}
