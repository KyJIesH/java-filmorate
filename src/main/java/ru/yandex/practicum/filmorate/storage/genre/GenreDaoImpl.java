package ru.yandex.practicum.filmorate.storage.genre;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.mapper.GenreMapper;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.lang.String.format;

@Slf4j
@Component
@AllArgsConstructor
public class GenreDaoImpl implements GenreDao {
    private static final String TAG = "GENRE DAO IMPL";
    private JdbcTemplate jdbcTemplate;
    private final GenreMapper genreMapper = new GenreMapper();

    @Override
    public Genre add(Integer id) {
        log.info("{} - Добавление жанра", TAG);
        Genre genre = jdbcTemplate.queryForObject(format(""
                + "SELECT * "
                + "FROM genres "
                + "WHERE genre_id=%d", id), genreMapper);
        log.info("{} - Добавлен жанр c id {}.", TAG, id);
        return genre;
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
            jdbcTemplate.update(""
                    + "INSERT INTO film_genres (film_id, genre_id) "
                    + "VALUES (?, ?)", filmId, genre.getId());
            log.info("{} - Фильму c id {} добавлен жанр c id {}.", TAG, filmId, genre.getId());
        }
    }

    @Override
    public Set<Genre> getAllGenresFilms(Long filmId) {
        log.info("{} - Получение всех жанров фильма по id: {}", TAG, filmId);
        Set<Genre> genres = new HashSet<>(jdbcTemplate.query(format(""
                + "SELECT f.genre_id, g.name "
                + "FROM film_genres AS f "
                + "LEFT OUTER JOIN genres AS g ON f.genre_id = g.genre_id "
                + "WHERE f.film_id=%d "
                + "ORDER BY g.genre_id", filmId), genreMapper));
        log.info("{} - Получены жанры: {}", TAG, genres);
        return genres;
    }
}
