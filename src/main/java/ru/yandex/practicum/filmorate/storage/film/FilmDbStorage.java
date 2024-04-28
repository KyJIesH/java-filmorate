package ru.yandex.practicum.filmorate.storage.film;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.BadRequestException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.mapper.FilmMapper;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
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
        String query = "INSERT INTO films (name, description, release_date, duration_minutes, mpa_rating_id) " +
                "VALUES (?, ?, ?, ?, ?) ";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection
                        .prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, film.getName());
                ps.setString(2, film.getDescription());
                ps.setDate(3, Date.valueOf(film.getReleaseDate()));
                ps.setLong(4, film.getDuration().toSeconds());
                ps.setLong(5, film.getMpa().getId());
                return ps;
            }, keyHolder);
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException("Некорректный рейтинг");
        }

        Long id = (Long) keyHolder.getKey();
        log.info("{} - Получен фильм с id: {}", TAG, id);
        return getFilm(id);
    }

    @Override
    public List<Film> getAllFilms() {
        log.info("{} - Получение всех фильмов", TAG);
        List<Film> films = jdbcTemplate.query(""
                + "SELECT * "
                + "FROM films", mapper);
        log.info("{} - Получены фильмы: {}", TAG, films);
        return films;
    }

    @Override
    public Film getFilm(Long id) {
        log.info("{} - Получение фильма с id: {}", TAG, id);
        try {
            Film film = jdbcTemplate.queryForObject(format(""
                    + "SELECT * "
                    + "FROM films "
                    + "WHERE film_id = %d", id), mapper);
            log.info("{} - Получен фильм: {}", TAG, film);
            return film;
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException("Фильм не найден");
        }
    }

    @Override
    public List<Film> getPopularFilms(Long count) {
        log.info("{} - Получение популярных фильмов", TAG);
        try {
            String query = "SELECT * FROM films " +
                    "INNER JOIN (SELECT film_id, COUNT(*) likes_count " +
                    "FROM likes " +
                    "GROUP BY film_id " +
                    "LIMIT ?) AS temp ON films.film_id = temp.film_id " +
                    "ORDER BY likes_count DESC ";
            return jdbcTemplate.query(query, mapper, count);
        } catch (DataAccessException e) {
            log.error("{} - Ошибка при получении списка популярных фильмов", TAG, e);
            throw e;
        }
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
