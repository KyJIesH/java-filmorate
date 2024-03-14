package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import ru.yandex.practicum.filmorate.annotation.LoginValidator;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;

/**
 * User.
 */
@Data
@NotNull
public class User {
    private int id;
    @NotBlank(message = "Пустой email")
    @Email(message = "Некорректный email")
    private String email;
    @NotBlank(message = "Логин не может быть пустым")
    @LoginValidator
    private String login;
    private String name;
    @NotNull(message = "Дата рождения не может быть не заполнена")
    @Past(message = "Дата рождения не может быть в будущем")
    private LocalDate birthday;
}
