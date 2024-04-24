package ru.yandex.practicum.filmorate.storage.mapper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;
import ru.yandex.practicum.filmorate.model.Friends;

import java.sql.ResultSet;
import java.sql.SQLException;

@Slf4j
public class FriendsMapper implements RowMapper<Friends> {
    private static final String TAG = "FRIENDS MAPPER";

    @Override
    public Friends mapRow(ResultSet rs, int rowNum) throws SQLException {
        log.info("{} - Начат mapping сущности friends", TAG);
        Friends friends = new Friends();
        Long firstUserId = rs.getLong("first_user_id");
        Long secondUserId = rs.getLong("second_user_id");
        friends.setFirstUserId(firstUserId);
        friends.setSecondUserId(secondUserId);
        log.info("{} - Завершен mapping сущности friends", TAG);
        return friends;
    }
}
