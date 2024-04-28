package ru.yandex.practicum.filmorate.service.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmService {
    Film createFilm(Film film);

    List<Film> getAllFilms();

    Film getFilm(Long id);

    Film updateFilm(Film film);

    void deleteFilm(Long id);

    void putLikesFilm(Long filmId, Long userId);

    void deleteLikesFilm(Long filmId, Long userId);

    List<Film> getPopularFilm(Long count);

    void checkFilmId(Long id);
}
