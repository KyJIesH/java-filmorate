package ru.yandex.practicum.filmorate.storage.mpaRating;

import ru.yandex.practicum.filmorate.model.MpaRating;

import java.util.List;

public interface MpaRatingDao {

    MpaRating getRatingFilm(Integer id);

    List<MpaRating> getRatingsFilms();
}
