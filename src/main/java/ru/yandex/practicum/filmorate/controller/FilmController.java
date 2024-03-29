package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.film.FilmService;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {
    private static final String TAG = "FILM CONTROLLER";
    private final FilmService filmService;

    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @PostMapping
    public ResponseEntity<Film> createFilm(@RequestBody @Valid Film film) { //Добавление фильма
        log.info("{} - Пришел запрос на добавление фильма {}",TAG, film);
        return new ResponseEntity<>(filmService.createFilm(film), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Film>> getAllFilms() { //Получение всех фильмов
        log.info("{} - Пришел запрос на получение всех фильмов", TAG);
        return new ResponseEntity<>(filmService.getAllFilms(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Film> getFilm(@PathVariable Long id) { //Получение конкретного фильма по id
        log.info("{} - Пришел запрос на получение фильма по id {}", TAG, id);
        return new ResponseEntity<>(filmService.getFilm(id), HttpStatus.OK);
    }

    @GetMapping("/popular")
    public ResponseEntity<Set<Film>> getPopularFilm(@RequestParam(required = false, defaultValue = "10") Long count) {
        log.info("{} - Пришел запрос на получение первых {} фильмов по количеству лайков", TAG, count);
        return new ResponseEntity<>(filmService.getPopularFilm(count), HttpStatus.OK);
    }


    @PutMapping
    public ResponseEntity<Film> updateFilm(@RequestBody @Valid Film film) { //Обновление фильма
        log.info("{} - Пришел запрос на обновление фильма {}", TAG, film);
        return new ResponseEntity<>(filmService.updateFilm(film), HttpStatus.OK);
    }

    @PutMapping("/{id}/like/{userId}")
    public ResponseEntity<String> putLikesFilm(@PathVariable Long id,
                                               @PathVariable Long userId) {
        log.info("{} - Пришел запрос на на добавление лайка фильма {} пользователем с id {}", TAG, id, userId);
        filmService.putLikesFilm(id, userId);
        return new ResponseEntity<>("Лайк фильму с id: " + id + " - проставлен пользователем с id: " + userId,
                HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFilm(@PathVariable Long id) {
        log.info("{} - Пришел запрос на удаление фильма по id {}", TAG, id);
        filmService.deleteFilm(id);
        return new ResponseEntity<>("Фильм с id: " + id + " - удален", HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public ResponseEntity<String> deleteLikesFilm(@PathVariable Long id,
                                                  @PathVariable Long userId) {
        log.info("{} - Пришел запрос на на удаление лайка фильма {} пользователем с id {}", TAG, id, userId);
        filmService.deleteLikesFilm(id, userId);
        return new ResponseEntity<>("Лайк фильму с id: " + id + " - удален пользователем с id: " + userId,
                HttpStatus.NO_CONTENT);
    }
}