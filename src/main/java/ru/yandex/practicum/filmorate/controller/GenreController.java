package ru.yandex.practicum.filmorate.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.genre.GenreService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/genres")
@AllArgsConstructor
public class GenreController {
    private static final String TAG = "GENRE CONTROLLER";
    private final GenreService genreService;

    @GetMapping("/{id}")
    public ResponseEntity<Genre> getGenre(@PathVariable Integer id) {
        log.info("{} -  Пришел запрос на получение жанра фильма с id {}", TAG, id);
        return new ResponseEntity<>(genreService.getGenre(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Genre>> getGenres() {
        log.info("{} -  Пришел запрос на получение всех жанров", TAG);
        return new ResponseEntity<>(genreService.getAll(), HttpStatus.OK);
    }
}
