package ru.yandex.practicum.filmorate.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class LoginValid implements ConstraintValidator<LoginValidator, String> {
    @Override
    public boolean isValid(String str, ConstraintValidatorContext context) {
        if (str.indexOf(' ') > 0) {
            return false;
        } else {
            return true;
        }
    }
}
