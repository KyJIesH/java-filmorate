package ru.yandex.practicum.filmorate.storage.genre;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.BadRequestException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.mapper.GenreMapper;

import java.util.*;

import static java.lang.String.format;

@Slf4j
@Component
@AllArgsConstructor
public class GenreDaoImpl implements GenreDao {
    private static final String TAG = "GENRE DAO IMPL";
    private JdbcTemplate jdbcTemplate;
    private final GenreMapper genreMapper = new GenreMapper();

    @Override
    public Genre getGenre(Integer id) {
        log.info("{} - Добавление жанра", TAG);
        try {
            Genre genre = jdbcTemplate.queryForObject(format(""
                    + "SELECT * "
                    + "FROM genres "
                    + "WHERE genre_id=%d", id), genreMapper);
            log.info("{} - Добавлен жанр c id {}.", TAG, id);
            return genre;
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException("Жанр отсутствует в базе");
        }

    }

    @Override
    public List<Genre> getAll() {
        log.info("{} - Получение всех жанров", TAG);
        List<Genre> genres = jdbcTemplate.query(""
                + "SELECT genre_id, name "
                + "FROM genres "
                + "ORDER BY genre_id", genreMapper);
        log.info("{} - Жанры возвращены.", TAG);
        return genres;
    }

    @Override
    public void addGenreFilm(Long filmId, Set<Genre> genres) {
        log.info("{} - Добавление жанров фильму по id: {}", TAG, filmId);
        for (Genre genre : genres) {
            try {
                jdbcTemplate.update(""
                        + "INSERT INTO film_genres (film_id, genre_id) "
                        + "VALUES (?, ?)", filmId, genre.getId());
                log.info("{} - Фильму c id {} добавлен жанр c id {}.", TAG, filmId, genre.getId());
            } catch (DataIntegrityViolationException e) {
                throw new BadRequestException("Указан некорректный жанр");
            }
        }
    }

    @Override
    public Set<Genre> getAllGenresFilms(Long filmId) {
        log.info("{} - Получение всех жанров фильма по id: {}", TAG, filmId);
        Set<Genre> genres = new LinkedHashSet<>(jdbcTemplate.query(format(""
                + "SELECT f.genre_id, g.name "
                + "FROM film_genres AS f "
                + "LEFT OUTER JOIN genres AS g ON f.genre_id = g.genre_id "
                + "WHERE f.film_id=%d "
                + "ORDER BY g.genre_id", filmId), genreMapper));
        log.info("{} - Получены жанры: {}", TAG, genres);
        return genres;
    }
}
