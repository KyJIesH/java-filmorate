package ru.yandex.practicum.filmorate.service.genre;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;

public interface GenreService {
    Genre getGenre(Integer id);

    List<Genre> getAll();
}
