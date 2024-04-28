package ru.yandex.practicum.filmorate.service.mpaRating;

import ru.yandex.practicum.filmorate.model.MpaRating;

import java.util.List;

public interface MpaRatingService {
    MpaRating getRatingFilm(Long id);

    List<MpaRating> getRatingsFilms();
}
