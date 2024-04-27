package ru.yandex.practicum.filmorate.storage.mpaRating;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.MpaRating;
import ru.yandex.practicum.filmorate.storage.mapper.MpaRatingMapper;

import java.util.List;

import static java.lang.String.format;

@Slf4j
@Component
@AllArgsConstructor
public class MpaRattingDaoImpl implements MpaRatingDao {
    private static final String TAG = "MPA RATTING DAO IMPL";
    private JdbcTemplate jdbcTemplate;
    private final MpaRatingMapper mpaRatingMapper = new MpaRatingMapper();

    @Override
    public MpaRating getRatingFilm(Long id) {
        log.info("{} - Получение рейтинга фильма по id: {}", TAG, id);
        try {
            MpaRating mpaRating = jdbcTemplate.queryForObject(format(""
                    + "SELECT * "
                    + "FROM mpa_ratings "
                    + "WHERE rating_id = %d", id), mpaRatingMapper);
            log.info("{} - Получен рейтинга фильма: {}", TAG, mpaRating);
            return mpaRating;
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException("Рейтинг не найден в базе");
        }
    }

    @Override
    public List<MpaRating> getRatingsFilms() {
        log.info("{} - Получение всех рейтингов", TAG);
        List<MpaRating> mpaRatings = jdbcTemplate.query(""
                + "SELECT * "
                + "FROM mpa_ratings "
                + "ORDER BY rating_id", mpaRatingMapper);
        log.info("{} - Получены рейтинги: {}", TAG, mpaRatings);
        return mpaRatings;
    }
}
