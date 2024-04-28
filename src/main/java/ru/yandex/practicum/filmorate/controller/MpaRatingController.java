package ru.yandex.practicum.filmorate.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.MpaRating;
import ru.yandex.practicum.filmorate.service.mpaRating.MpaRatingService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/mpa")
@AllArgsConstructor
public class MpaRatingController {
    private static final String TAG = "MPA RATING CONTROLLER";
    private final MpaRatingService mpaRatingService;

    @GetMapping("/{id}")
    public ResponseEntity<MpaRating> getRatingFilm(@PathVariable Long id) {
        log.info("{} -  Пришел запрос на получение рейтинга фильма с id {}", TAG, id);
        return new ResponseEntity<>(mpaRatingService.getRatingFilm(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<MpaRating>> getRatingsFilms() {
        log.info("{} -  Пришел запрос на получение всех рейтингов", TAG);
        return new ResponseEntity<>(mpaRatingService.getRatingsFilms(), HttpStatus.OK);
    }
}
