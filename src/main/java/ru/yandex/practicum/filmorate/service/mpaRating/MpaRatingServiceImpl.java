package ru.yandex.practicum.filmorate.service.mpaRating;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.MpaRating;
import ru.yandex.practicum.filmorate.storage.mpaRating.MpaRatingDao;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class MpaRatingServiceImpl implements MpaRatingService {
    private static final String TAG = "MPA RATING SERVICE IMPL";
    private final MpaRatingDao mpaRatingDao;

    @Override
    public MpaRating getRatingFilm(Long id) {
        return mpaRatingDao.getRatingFilm(id);
    }

    @Override
    public List<MpaRating> getRatingsFilms() {
        return mpaRatingDao.getRatingsFilms();
    }
}
