package ru.yandex.practicum.filmorate.service.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;
import java.util.Set;

public interface FilmService {
    Film createFilm(Film film);

    List<Film> getAllFilms();

    Film getFilm(Long id);

    Film updateFilm(Film film);

    void deleteFilm(Long id);

    void putLikesFilm(Long filmId, Long userId);

    void deleteLikesFilm(Long filmId, Long userId);

    Set<Film> getPopularFilm(Long count);
}
