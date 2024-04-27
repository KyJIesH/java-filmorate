package ru.yandex.practicum.filmorate.storage.like;

import ru.yandex.practicum.filmorate.model.Like;

import java.util.List;
import java.util.Set;

public interface LikeDao {

    void addLike(Long filmId, Long userId);

    Set<Long> getLikesFilm(Long filmId);

    List<Like> getLikes();

    void deleteLike(Long filmId, Long userId);
}
