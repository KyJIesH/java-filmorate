package ru.yandex.practicum.filmorate.service.film;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.genre.GenreDao;
import ru.yandex.practicum.filmorate.storage.like.LikeDao;
import ru.yandex.practicum.filmorate.storage.mpaRating.MpaRatingDao;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class FilmServiceImpl implements FilmService {
    private static final String TAG = "FILM SERVICE";
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;
    private final GenreDao genreDao;
    private final MpaRatingDao mpaRatingDao;
    private final LikeDao likeDao;

    @Override
    public Film createFilm(Film film) {
        log.info("{} - Обработка запроса на добавление фильма", TAG);
        Film result = filmStorage.createFilm(film);
        genreDao.addGenreFilm(result.getId(), film.getGenres());
        result.setGenres(genreDao.getAllGenresFilms(result.getId()));
        return result;
    }

    @Override
    public List<Film> getAllFilms() {
        log.info("{} - Обработка запроса на получение всех фильмов", TAG);
        List<Film> films = filmStorage.getAllFilms();
        for (Film film : films) {
            film.setLikes(likeDao.getLikesFilm(film.getId()));
            film.setGenres(genreDao.getAllGenresFilms(film.getId()));
            film.setMpa(mpaRatingDao.getRatingFilm(film.getMpa().getId()));
        }
        return films;
    }

    @Override
    public Film getFilm(Long id) {
        log.info("{} - Обработка запроса на получение фильма по id {}", TAG, id);
        Film film = filmStorage.getFilm(id);
        film.setGenres(genreDao.getAllGenresFilms(id));
        film.setMpa(mpaRatingDao.getRatingFilm(film.getMpa().getId()));
        return film;
    }

    @Override
    public Film updateFilm(Film film) {
        log.info("{} - Обработка запроса на обновление фильма {}", TAG, film);
        return filmStorage.updateFilm(film);
    }

    @Override
    public void deleteFilm(Long id) {
        log.info("{} - Обработка запроса на удаление фильма по id {}", TAG, id);
        filmStorage.deleteFilm(id);
    }

    @Override
    public void putLikesFilm(Long filmId, Long userId) {
        log.info("{} - Обработка запроса на добавление лайка фильма {} пользователем с id {}", TAG, filmId, userId);
        Film film = filmStorage.getFilm(filmId);
        User user = userStorage.getUser(userId); //Получение пользователя для проверки на исключение
        likeDao.addLike(filmId, userId);
        film.getLikes().add(user.getId());
    }

    @Override
    public void deleteLikesFilm(Long filmId, Long userId) {
        log.info("{} - Обработка запроса на удаление лайка фильма {} пользователем с id {}", TAG, filmId, userId);
        Film film = filmStorage.getFilm(filmId);
        User user = userStorage.getUser(userId); //Получение пользователя для проверки на исключение
        likeDao.deleteLike(filmId, userId);
        film.getLikes().remove(user.getId());
    }

    @Override
    public Set<Film> getPopularFilm(Long count) {
        log.info("{} - Обработка запроса на получение {} наиболее популярных фильмов по количеству лайков", TAG, count);
        Set<Film> temp = new HashSet<>(getAllFilms());
        Set<Film> filmsLikes = new TreeSet<>(new Comparator<Film>() {
            @Override
            public int compare(Film f1, Film f2) {
                if (f1.getLikes().size() == f2.getLikes().size() && Objects.equals(f1.getId(), f2.getId())) {
                    return 0;
                } else if (f1.getLikes().size() < f2.getLikes().size()) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });
        filmsLikes.addAll(
                temp.stream()
                        .skip(temp.size() > count ? temp.size() - count : 0)
                        .filter(film -> !film.getLikes().isEmpty())
                        .limit(count)
                        .collect(Collectors.toSet()));
        return filmsLikes;
    }

    public void checkFilmId(Long id) {
        if (id == null || id <= 0) {
            throw new ValidationException("Некорректный формат id фильма");
        }
    }
}