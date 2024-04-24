package ru.yandex.practicum.filmorate.storage.mapper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;
import ru.yandex.practicum.filmorate.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;

@Slf4j
public class GenreMapper implements RowMapper<Genre> {
    private static final String TAG = "GENRE MAPPER";

    @Override
    public Genre mapRow(ResultSet rs, int rowNum) throws SQLException {
        log.info("{} - Начат mapping сущности genre", TAG);
        Genre genre = new Genre();
        Integer id = rs.getInt("genre_id");
        String name = rs.getString("name");
        genre.setId(id);
        genre.setName(name);
        log.info("{} - Завершен mapping сущности genre", TAG);
        return genre;
    }
}
