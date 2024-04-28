package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmStorage {

    Film createFilm(Film film);

    List<Film> getAllFilms();

    Film getFilm(Long id);

    Film updateFilm(Film film);

    void deleteFilm(Long id);

    List<Film> getPopularFilms(Long count);
}