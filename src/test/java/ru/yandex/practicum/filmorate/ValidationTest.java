package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.Duration;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class ValidationTest {
    private Validator validator;
    private User user = new User();
    private Film film = new Film();

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        user.setEmail("email@mail.ru");
        user.setLogin("login");
        user.setName("name");
        user.setBirthday(LocalDate.of(1990, 12,1));

        film.setName("name");
        film.setDescription("description");
        film.setReleaseDate(LocalDate.of(2000, 1, 1));
        film.setDuration(Duration.ofMinutes(120));
    }

    @Test
    public void createUserEmailTest() {
        user.setEmail("");
        assertFalse(validator.validate(user).isEmpty());
        user.setEmail("email");
        assertFalse(validator.validate(user).isEmpty());
        user.setEmail("0 email");
        assertFalse(validator.validate(user).isEmpty());
    }

    @Test
    public void createUserLoginTest() {
        user.setLogin("");
        assertFalse(validator.validate(user).isEmpty());
        user.setLogin("1 1 1");
        assertFalse(validator.validate(user).isEmpty());
    }

    @Test
    public void createUserBirthdayTest() {
        user.setBirthday(LocalDate.of(2222,2,2));
        assertFalse(validator.validate(user).isEmpty());
        user.setBirthday(null);
        assertFalse(validator.validate(user).isEmpty());
    }

    @Test
    public void createFilmNameTest() {
        film.setName("");
        assertFalse(validator.validate(film).isEmpty());
        film.setName(null);
        assertFalse(validator.validate(film).isEmpty());
    }

    @Test
    public void createFilmDescriptionTest() {
        film.setDescription("");
        assertFalse(validator.validate(film).isEmpty());
        film.setDescription(null);
        assertFalse(validator.validate(film).isEmpty());
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < 202; i++) {
            sb.append("*");
        }
        film.setDescription(sb.toString());
        assertFalse(validator.validate(film).isEmpty());
    }

    @Test
    public void createFilmReleaseDateTest() {
        film.setReleaseDate(LocalDate.of(1895, 12, 26));
        assertFalse(validator.validate(film).isEmpty());
    }

    @Test
    public void createFilmDurationTest() {
        film.setDuration(Duration.ofMinutes(0));
        assertFalse(validator.validate(film).isEmpty());
    }
}
