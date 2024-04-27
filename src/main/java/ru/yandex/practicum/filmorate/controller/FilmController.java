package ru.yandex.practicum.filmorate.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.film.FilmService;
import ru.yandex.practicum.filmorate.service.user.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/films")
@Slf4j
@AllArgsConstructor
public class FilmController {
    private static final String TAG = "FILM CONTROLLER";
    private final FilmService filmService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<Film> createFilm(@RequestBody @Valid Film film) {
        log.info("{} - Пришел запрос на добавление фильма {}", TAG, film);
        return new ResponseEntity<>(filmService.createFilm(film), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Film>> getAllFilms() {
        log.info("{} - Пришел запрос на получение всех фильмов", TAG);
        return new ResponseEntity<>(filmService.getAllFilms(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Film> getFilm(@PathVariable Long id) {
        log.info("{} - Пришел запрос на получение фильма по id {}", TAG, id);
        filmService.checkFilmId(id);
        return new ResponseEntity<>(filmService.getFilm(id), HttpStatus.OK);
    }

    @GetMapping("/popular")
    public ResponseEntity<List<Film>> getPopularFilm(@RequestParam(defaultValue = "10") Long count) {
        log.info("{} - Пришел запрос на получение первых {} фильмов по количеству лайков", TAG, count);
        return new ResponseEntity<>(filmService.getPopularFilm(count), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Film> updateFilm(@RequestBody @Valid Film film) {
        log.info("{} - Пришел запрос на обновление фильма {}", TAG, film);
        return new ResponseEntity<>(filmService.updateFilm(film), HttpStatus.OK);
    }

    @PutMapping("/{id}/like/{userId}")
    public ResponseEntity<String> putLikesFilm(@PathVariable Long id,
                                               @PathVariable Long userId) {
        log.info("{} - Пришел запрос на на добавление лайка фильма {} пользователем с id {}", TAG, id, userId);
        filmService.checkFilmId(id);
        userService.checkUserId(userId);
        filmService.putLikesFilm(id, userId);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFilm(@PathVariable Long id) {
        log.info("{} - Пришел запрос на удаление фильма по id {}", TAG, id);
        filmService.checkFilmId(id);
        filmService.deleteFilm(id);
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public ResponseEntity<String> deleteLikesFilm(@PathVariable Long id,
                                                  @PathVariable Long userId) {
        log.info("{} - Пришел запрос на на удаление лайка фильма {} пользователем с id {}", TAG, id, userId);
        filmService.checkFilmId(id);
        userService.checkUserId(userId);
        filmService.deleteLikesFilm(id, userId);
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
}