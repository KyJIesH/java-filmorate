package ru.yandex.practicum.filmorate.storage.film;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.mapper.FilmMapper;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

@Slf4j
@Component
@Primary
@AllArgsConstructor
public class FilmDbStorage implements FilmStorage {
    private static final String TAG = "FILM DB STORAGE";
    private JdbcTemplate jdbcTemplate;
    private final FilmMapper mapper = new FilmMapper();

    @Override
    public Film createFilm(Film film) {
        log.info("{} - Создание фильма", TAG);
        String query = "INSERT INTO films (name, description, release_date, duration_minutes) VALUES (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, film.getName());
            ps.setString(2, film.getDescription());
            ps.setDate(3, Date.valueOf(film.getReleaseDate()));
            ps.setInt(4, (int) film.getDuration().toMinutes());
            return ps;
        }, keyHolder);

        Long id = (Long) keyHolder.getKey();
        log.info("{} - Получен фильм с id: {}", TAG, id);
        return getFilm(id);
    }

    @Override
    public List<Film> getAllFilms() {
        log.info("{} - Получение всех фильмов", TAG);
        List<Film> films = jdbcTemplate.query(""
                + "SELECT film_id, name, description, release_date, duration_minutes "
                + "FROM films", mapper);
        log.info("{} - Получены фильмы: {}", TAG, films);
        return films;
    }

    @Override
    public Film getFilm(Long id) {
        log.info("{} - Получение фильма с id: {}",TAG, id);
        Film film = jdbcTemplate.queryForObject(format(""
                + "SELECT film_id, name, description, release_date, duration_minutes "
                + "FROM films "
                + "WHERE film_id = %d", id), mapper);
        log.info("{} - Получен фильм: {}", TAG, film);
        return film;
    }

    @Override
    public List<Film> getFilmsByIds(List<Long> ids) {
        String query = "SELECT * FROM films WHERE film_id = ANY(?)";
        List<Object> requestParam = new ArrayList<>();
        requestParam.add(ids.toArray());
        return jdbcTemplate.query(query, mapper, requestParam);
    }

    @Override
    public Film updateFilm(Film film) {
        log.info("{} - Обновление фильма: {}", TAG, film);
        String query = "UPDATE films SET name = ?, description = ?, release_date = ?, duration_minutes = ? WHERE film_id = ?";
        int updatedRow = jdbcTemplate.update(query, film.getName(), film.getDescription(), film.getReleaseDate(),
                film.getDuration(), film.getId());
        if (updatedRow > 0) {
            log.info("{} - Фильм {} обновлен", TAG, film);
        } else {
            log.error("{} - Фильм {} НЕ обновлен", TAG, film);
        }
        return getFilm(film.getId());
    }

    @Override
    public void deleteFilm(Long id) {
        log.info("{} - Удаление фильма с id: {}", TAG, id);
        String query = "DELETE FROM films WHERE film_id = ?";
        int deleteRow = jdbcTemplate.update(query, id);
        if (deleteRow > 0) {
            log.info("{} - Фильм с id: {} удален", TAG, id);
        } else {
            log.error("{} - Фильм с id: {} НЕ удален", TAG, id);
        }
    }
}
