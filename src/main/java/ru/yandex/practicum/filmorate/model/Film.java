package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import ru.yandex.practicum.filmorate.annotation.DateValidator;
import ru.yandex.practicum.filmorate.annotation.DurationValidator;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Duration;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.NUMBER_INT;

@Data
public class Film {
    private Long id;
    @NotBlank(message = "Название не может быть пустым")
    private String name;
    @NotBlank(message = "Описание не может быть пустым")
    @Size(max = 200, message = "Максимальная длина описания 200 символов")
    private String description;
    @DateValidator
    private LocalDate releaseDate;
    @DurationValidator
    @JsonFormat(shape = NUMBER_INT)
    private Duration duration;
    @NotNull(message = "Рейтинг не может быть пустым")
    private MpaRating mpaRating;
    private Set<Genre> genres = new HashSet<>();
    private Set<Long> likes = new TreeSet<>();
}