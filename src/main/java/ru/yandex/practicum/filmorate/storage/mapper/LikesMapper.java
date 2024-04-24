package ru.yandex.practicum.filmorate.storage.mapper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;
import ru.yandex.practicum.filmorate.model.Like;

import java.sql.ResultSet;
import java.sql.SQLException;

@Slf4j
public class LikesMapper implements RowMapper<Like> {
    private static final String TAG = "LIKES MAPPER";

    @Override
    public Like mapRow(ResultSet rs, int rowNum) throws SQLException {
        log.info("{} - Начат mapping сущности likes", TAG);
        Like like = new Like();
        Long filmId = rs.getLong("film_id");
        Long userId = rs.getLong("user_id");
        like.setFilmId(filmId);
        like.setUserId(userId);
        log.info("{} - Завершен mapping сущности likes", TAG);
        return like;
    }
}
