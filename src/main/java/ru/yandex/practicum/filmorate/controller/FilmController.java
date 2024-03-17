package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {
    private Map<Integer, Film> films = new HashMap<>();
    private static final String TAG = "FILM CONTROLLER";
    private int idGenerator = 1;

    @PostMapping
    public Film createFilm(@RequestBody @Valid Film film) { //Добавление фильма
        log.info("{} - Добавление фильма " + film, TAG);
        film.setId(idGenerator++);
        films.put(film.getId(), film);
        return film;
    }

    @PutMapping
    public Film updateFilm(@RequestBody @Valid Film film) { //Обновление фильма
        log.info("{} - Обновление фильма " + film, TAG);
        if (!films.containsKey(film.getId())) {
            throw new ValidationException("id не найден");
        }
        films.put(film.getId(), film);
        return film;
    }

    @GetMapping
    public List<Film> getAllFilms() { //Получение всех фильмов
        log.info("{} - получение всех фильмов " + films.values(), TAG);
        return new ArrayList<>(films.values());
    }
}
