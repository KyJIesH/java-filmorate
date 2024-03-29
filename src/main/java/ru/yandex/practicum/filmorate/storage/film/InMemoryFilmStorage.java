package ru.yandex.practicum.filmorate.storage.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class InMemoryFilmStorage implements FilmStorage {
    private Map<Long, Film> films = new HashMap<>();
    private static final String TAG = "FILM STORAGE";
    private long idGenerator = 1;

    @Override
    public Film createFilm(Film film) {
        log.info("{} - Попытка добавления фильма {}", TAG, film);
        film.setId(idGenerator++);
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public List<Film> getAllFilms() {
        log.info("{} - Попытка получения всех фильмов", TAG);
        List<Film> filmList = new ArrayList<>(films.values());
        log.info("{} - Попытка получения всех фильмов завершена {}", TAG, filmList);
        return filmList;
    }

    @Override
    public Film getFilm(Long id) {
        log.info("{} - Попытка получения фильма по id {}", TAG, id);
        if (id == null || id <= 0 || !films.containsKey(id)) {
            log.error("{} - некорректный id {}", TAG, id);
            return null;
        }
        return films.get(id);
    }

    @Override
    public Film updateFilm(Film film) {
        log.info("{} - Попытка обновления фильма {}", TAG, film);
        if (!films.containsKey(film.getId())) {
            throw new ValidationException("id не найден"); // поменять на другой
        }
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public void deleteFilm(Long id) {
        log.info("{} - Попытка удаления фильма по id {}", TAG, id);
        if (id == null || id <= 0) {
            log.error("{} - некорректный id {}", TAG, id);
            throw new ValidationException("id не найден"); // поменять на другой
        }
        if (films.containsKey(id)) {
            log.error("{} - по данному id {} нет фильма", TAG, id);
            throw new ValidationException("id не найден"); // поменять на другой
        }
        films.remove(id);
    }
}