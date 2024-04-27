package ru.yandex.practicum.filmorate.storage.like;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Like;
import ru.yandex.practicum.filmorate.storage.mapper.LikesMapper;

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
    public Set<Long> getLikesFilm(Long filmId) {
        log.info("{} - Получение всех пользователей лайкнувших фильм с id: {}", TAG, filmId);
        List<Long> usersIds = jdbcTemplate.queryForList(format(""
                + "SELECT user_id "
                + "FROM likes "
                + "WHERE film_id = %d", filmId), Long.class);
        log.info("{} - Получены пользователи лайкнувшие фильм с id: {}", TAG, filmId);
        Set<Long> result = new TreeSet<>();
        result.addAll(usersIds);
        return result;
    }

    @Override
    public List<Like> getLikes() {
        log.info("{} - Получение всех лайков фильма", TAG);
        String query = "SELECT * FROM likes";
        List<Like> likes = jdbcTemplate.query(query, likesMapper);
        log.info("{} - Получены все лайки фильма", TAG);
        return likes;
    }

    @Override
    public void deleteLike(Long filmId, Long userId) {
        log.info("{} - Удаление лайка у фильма с id {} пользователем с id {}", TAG, filmId, userId);
        jdbcTemplate.update(format(""
                + "DELETE FROM likes "
                + "WHERE film_id  = %d "
                + "AND user_id  = %d ", filmId, userId));
        log.info("{} - У фильма с id {} удален лайк пользователем с id {}", TAG, filmId, userId);
    }
}
