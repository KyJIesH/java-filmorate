package ru.yandex.practicum.filmorate.storage.mapper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;
import ru.yandex.practicum.filmorate.model.MpaRating;

import java.sql.ResultSet;
import java.sql.SQLException;

@Slf4j
public class MpaRatingMapper implements RowMapper<MpaRating> {
    private static final String TAG = "MPA RATING MAPPER";

    @Override
    public MpaRating mapRow(ResultSet rs, int rowNum) throws SQLException {
        log.info("{} - Начат mapping сущности mpaRating", TAG);
        MpaRating mpaRating = new MpaRating();
        Long id = rs.getLong("rating_id");
        String name = rs.getString("name");
        mpaRating.setId(id);
        mpaRating.setName(name);
        log.info("{} - Завершен mapping сущности mpaRating", TAG);
        return mpaRating;
    }
}
