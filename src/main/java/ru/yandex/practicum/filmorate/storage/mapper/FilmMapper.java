package ru.yandex.practicum.filmorate.storage.mapper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.MpaRating;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDate;

@Slf4j
public class FilmMapper implements RowMapper<Film> {
    private static final String TAG = "FILM MAPPER";

    @Override
    public Film mapRow(ResultSet rs, int rowNum) throws SQLException {
        log.info("{} - Начат mapping сущности film", TAG);
        MpaRating mpaRating = new MpaRating();
        Long mpaRatingId = rs.getLong("mpa_rating_id");
        mpaRating.setId(mpaRatingId);

        Film film = new Film();
        Long id = rs.getLong("film_id");
        String name = rs.getString("name");
        String description = rs.getString("description");
        LocalDate releaseDate = rs.getDate("release_date").toLocalDate();
        Duration duration = Duration.ofSeconds(rs.getInt("duration_minutes"));
        film.setId(id);
        film.setName(name);
        film.setDescription(description);
        film.setReleaseDate(releaseDate);
        film.setDuration(duration);
        film.setMpa(mpaRating);
        log.info("{} - Завершен mapping сущности film", TAG);
        return film;
    }
}
