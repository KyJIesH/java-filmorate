package ru.yandex.practicum.filmorate.storage.mpaRating;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
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
    public MpaRating getRatingFilm(Integer id) {
        log.info("{} - Получение рейтинга фильма по id: {}", TAG, id);
        MpaRating mpaRating = jdbcTemplate.queryForObject(format(""
                + "SELECT * "
                + "FROM mpa_ratings "
                + "WHERE rating_id = %d", id), mpaRatingMapper);
        log.info("{} - Получен рейтинга фильма: {}", TAG, mpaRating);
        return mpaRating;
    }

    @Override
    public List<MpaRating> getRatingsFilms() {
        log.info("{} - Получение всех рейтингов", TAG);
        List<MpaRating> mpaRatings = jdbcTemplate.query(""
                + "SELECT * "
                + "FROM mpa_ratings", mpaRatingMapper);
        log.info("{} - Получены рейтинги: {}", TAG, mpaRatings);
        return mpaRatings;
    }
}
