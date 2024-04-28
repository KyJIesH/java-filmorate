package ru.yandex.practicum.filmorate.storage.genre;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;
import java.util.Set;

public interface GenreDao {

    Genre getGenre(Integer id);

    List<Genre> getAll();

    void addGenreFilm(Long filmId, Set<Genre> genres);

    Set<Genre> getAllGenresFilms(Long filmId);
}
