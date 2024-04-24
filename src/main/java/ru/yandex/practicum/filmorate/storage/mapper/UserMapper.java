package ru.yandex.practicum.filmorate.storage.mapper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

@Slf4j
public class UserMapper implements RowMapper<User> {
    private static final String TAG = "USER MAPPER";

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        log.info("{} - Начат mapping сущности user", TAG);
        User user = new User();
        Long id = rs.getLong("user_id");
        String email = rs.getString("email");
        String login = rs.getString("login");
        String name = rs.getString("name");
        LocalDate birthday = rs.getDate("birthday").toLocalDate();
        user.setId(id);
        user.setEmail(email);
        user.setLogin(login);
        user.setName(name);
        user.setBirthday(birthday);
        log.info("{} - Завершен mapping сущности user", TAG);
        return user;
    }
}
