package ru.yandex.practicum.filmorate.storage.user;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.friends.FriendsDao;
import ru.yandex.practicum.filmorate.storage.mapper.UserMapper;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

@Slf4j
@Component
@Primary
@AllArgsConstructor
public class UserDbStorage implements UserStorage {
    private static final String TAG = "USER DB STORAGE";
    private JdbcTemplate jdbcTemplate;
    private final FriendsDao friendsDao;
    private final UserMapper mapper = new UserMapper();

    @Override
    public User createUser(User user) {
        log.info("{} - Создание пользователя", TAG);
        String query = "INSERT INTO users (email, login, name, birthday) VALUES (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getLogin());
            ps.setString(3, user.getName());
            ps.setDate(4, Date.valueOf(user.getBirthday()));
            return ps;
        }, keyHolder);

        Long id = (Long) keyHolder.getKey();
        log.info("{} - Получен пользователь с id: {}", TAG, id);
        return getUser(id);
    }

    @Override
    public List<User> getAllUsers() {
        log.info("{} - Получение всех пользователей", TAG);
        List<User> users = jdbcTemplate.query(""
                + "SELECT user_id, email, login, name, birthday "
                + "FROM users", mapper);
        log.info("{} - Получены пользователи: {}", TAG, users);
        return users;
    }

    @Override
    public User getUser(Long id) {
        log.info("{} - Получение пользователя с id: {}", TAG, id);
        try {
            User user = jdbcTemplate.queryForObject(format(""
                    + "SELECT user_id, email, login, name, birthday "
                    + "FROM users "
                    + "WHERE user_id = %d", id), mapper);
            log.info("{} - Получен пользователь: {}", TAG, user);
            if (user != null) {
                user.setFriends(friendsDao.getFriends(id));
            }
            return user;
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException("Не найден пользовтель");
        }

    }

    @Override
    public List<User> getUsersByIds(List<Long> ids) {
        String query = "SELECT * FROM users WHERE user_id = ANY(?)";
        List<Object> requestParam = new ArrayList<>();
        requestParam.add(ids.toArray());
        return jdbcTemplate.query(query, mapper, requestParam);
    }

    @Override
    public User updateUser(User user) {
        log.info("{} - Обновление пользователя: {}", TAG, user);
        String query = "UPDATE users SET email = ?, login = ?, name = ?, birthday = ? WHERE user_id = ?";
        int updatedRow = jdbcTemplate.update(query, user.getEmail(), user.getLogin(), user.getName(),
                user.getBirthday(), user.getId());
        if (updatedRow > 0) {
            log.info("{} - Пользователь {} обновлен", TAG, user);
        } else {
            log.error("{} - Пользователь {} НЕ обновлен", TAG, user);
        }
        return getUser(user.getId());
    }

    @Override
    public void deleteUser(Long id) {
        log.info("{} - Удаление пользователя с id: {}", TAG, id);
        String query = "DELETE FROM users WHERE user_id = ?";
        int deleteRow = jdbcTemplate.update(query, id);
        if (deleteRow > 0) {
            log.info("{} - Пользователь с id: {} удален", TAG, id);
        } else {
            log.error("{} - Пользователь с id: {} НЕ удален", TAG, id);
        }
    }
}
