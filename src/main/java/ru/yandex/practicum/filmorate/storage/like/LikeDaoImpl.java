package ru.yandex.practicum.filmorate.storage.like;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.FilmDbStorage;
import ru.yandex.practicum.filmorate.storage.mapper.LikesMapper;
import ru.yandex.practicum.filmorate.storage.user.UserDbStorage;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import static java.lang.String.format;

@Slf4j
@Component
@AllArgsConstructor
public class LikeDaoImpl implements LikeDao {
    private static final String TAG = "LIKE DAO IMPL";
    private JdbcTemplate jdbcTemplate;
    private final UserDbStorage userDbStorage;
    private final FilmDbStorage filmDbStorage;
    private final LikesMapper likesMapper = new LikesMapper();

    @Override
    public void addLike(Long filmId, Long userId) {
        log.info("{} - Добавление лайка фильму с id {} пользователем с id {}", TAG, filmId, userId);
        jdbcTemplate.update(""
                + "INSERT INTO likes (film_id, user_id) "
                + "VALUES (?, ?)", filmId, userId);
        log.info("{} - Фильму с id {} добавлен лайк пользователем с id {}", TAG, filmId, userId);
    }

    @Override
    public Set<User> getLikesFilm(Long filmId) {
        log.info("{} - Получение всех пользователей лайкнувших фильм с id: {}", TAG, filmId);
        List<Long> usersIds = jdbcTemplate.queryForList(format(""
                + "SELECT user_id "
                + "FROM likes "
                + "WHERE film_id = %d", filmId), Long.class);
        List<User> userList = userDbStorage.getUsersByIds(usersIds);
        log.info("{} - Получены пользователи лайкнувшие фильм с id: {}", TAG, filmId);
        return new TreeSet<>(userList);
    }

    @Override
    public Set<Film> getLikesUser(Long userId) {
        log.info("{} - Получение всех фильмов отмеченных пользователем с id: {}", TAG, userId);
        List<Long> filmsIds = jdbcTemplate.queryForList(format(""
                + "SELECT film_id "
                + "FROM likes "
                + "WHERE user_id = %d", userId), Long.class);
        List<Film> filmsList = filmDbStorage.getFilmsByIds(filmsIds);
        log.info("{} - Получены фильмы отмеченные пользователем с id: {}", TAG, userId);
        return new TreeSet<>(filmsList);
    }

    @Override
    public void deleteLike(Long filmId, Long userId) {
        log.info("{} - Удаление лайка у фильма с id {} пользователем с id {}", TAG, filmId, userId);
        jdbcTemplate.update(""
                + "DELETE FROM likes "
                + "WHERE film_id "
                + "AND user_id ", filmId, userId);
        log.info("{} - У фильма с id {} удален лайк пользователем с id {}", TAG, filmId, userId);
    }
}
