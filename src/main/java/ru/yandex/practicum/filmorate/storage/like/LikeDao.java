package ru.yandex.practicum.filmorate.storage.like;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Set;

public interface LikeDao {

    void addLike(Long filmId, Long userId);

    Set<User> getLikesFilm(Long filmId);

    Set<Film> getLikesUser(Long userId);

    void deleteLike(Long filmId, Long userId);
}
