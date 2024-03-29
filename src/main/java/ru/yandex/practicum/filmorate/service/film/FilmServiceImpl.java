package ru.yandex.practicum.filmorate.service.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FilmServiceImpl implements FilmService {
    private static final String TAG = "FILM SERVICE";
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    public FilmServiceImpl(FilmStorage filmStorage, UserStorage userStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
    }

    @Override
    public Film createFilm(Film film) {
        log.info("{} - Обработка запроса на добавление фильма", TAG);
        return filmStorage.createFilm(film);
    }

    @Override
    public List<Film> getAllFilms() {
        log.info("{} - Обработка запроса на получение всех фильмов", TAG);
        return filmStorage.getAllFilms();
    }

    @Override
    public Film getFilm(Long id) throws NotFoundException {
        log.info("{} - Обработка запроса на получение фильма по id {}", TAG, id);
        return filmStorage.getFilm(id);
    }

    @Override
    public Film updateFilm(Film film) throws NotFoundException {
        log.info("{} - Обработка запроса на обновление фильма {}", TAG, film);
        return filmStorage.updateFilm(film);
    }

    @Override
    public void deleteFilm(Long id) throws NotFoundException {
        log.info("{} - Обработка запроса на удаление фильма по id {}", TAG, id);
        filmStorage.deleteFilm(id);
    }

    @Override
    public void putLikesFilm(Long filmId, Long userId) throws NotFoundException {
        log.info("{} - Обработка запроса на добавление лайка фильма {} пользователем с id {}", TAG, filmId, userId);
        Film film = filmStorage.getFilm(filmId);
        User user = userStorage.getUser(userId); //Получение пользователя для проверки на исключение
        film.getLikes().add(user.getId());
    }

    @Override
    public void deleteLikesFilm(Long filmId, Long userId) throws NotFoundException {
        log.info("{} - Обработка запроса на удаление лайка фильма {} пользователем с id {}", TAG, filmId, userId);
        Film film = filmStorage.getFilm(filmId);
        User user = userStorage.getUser(userId); //Получение пользователя для проверки на исключение
        film.getLikes().remove(user.getId());
    }

    @Override
    public Set<Film> getPopularFilm(Long count) {
        log.info("{} - Обработка запроса на получение {} наиболее популярных фильмов по количеству лайков", TAG, count);
        Set<Film> temp = new HashSet<>();
        temp.addAll(filmStorage.getAllFilms());
        Set<Film> filmsLikes = new TreeSet<>(new Comparator<Film>() {
            @Override
            public int compare(Film f1, Film f2) {
                if (f1.getLikes().size() == f2.getLikes().size()) {
                    return 0;
                } else if (f1.getLikes().size() < f2.getLikes().size()) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });

        filmsLikes.addAll(temp.stream()
                .skip(temp.size() > count ? temp.size() - count : 0)
                .filter(film -> !film.getLikes().isEmpty())
                .limit(count)
                .collect(Collectors.toSet()));
        return filmsLikes;

    }
}