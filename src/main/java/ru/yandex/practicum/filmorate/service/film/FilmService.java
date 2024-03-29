package ru.yandex.practicum.filmorate.service.film;

import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;
import java.util.Set;

public interface FilmService {
    Film createFilm(Film film);

    List<Film> getAllFilms();

    Film getFilm(Long id) throws NotFoundException;

    Film updateFilm(Film film) throws NotFoundException;

    void deleteFilm(Long id) throws NotFoundException;

    void putLikesFilm(Long filmId, Long userId) throws NotFoundException;

    void deleteLikesFilm(Long filmId, Long userId) throws NotFoundException;

    Set<Film> getPopularFilm(Long count);
}
