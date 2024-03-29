package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmStorage {

    Film createFilm(Film film);

    List<Film> getAllFilms();

    Film getFilm(Long id) throws NotFoundException;

    Film updateFilm(Film film) throws NotFoundException;

    void deleteFilm(Long id) throws NotFoundException;
}