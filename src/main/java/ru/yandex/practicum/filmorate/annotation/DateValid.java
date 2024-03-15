package ru.yandex.practicum.filmorate.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class DateValid implements ConstraintValidator<DateValidator, LocalDate> {

    private LocalDate birthdayMovie;

    public void initialize(DateValidator annotation) {
        birthdayMovie = LocalDate.parse(annotation.value());
    }

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        return (value == null) || value.isAfter(birthdayMovie);
    }
}
