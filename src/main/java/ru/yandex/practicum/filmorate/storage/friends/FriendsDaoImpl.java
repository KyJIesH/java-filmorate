package ru.yandex.practicum.filmorate.storage.friends;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.storage.mapper.FriendsMapper;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import static java.lang.String.format;

@Slf4j
@Component
@AllArgsConstructor
public class FriendsDaoImpl implements FriendsDao {
    private static final String TAG = "FRIENDS DAO IMPL";
    private final FriendsMapper friendsMapper = new FriendsMapper();
    private JdbcTemplate jdbcTemplate;

    @Override
    public void addFriend(Long firstUserId, Long secondUserId) {
        log.info("{} - Добавление пользователя с id: {} в друзья к пользователю с id: {}", TAG, firstUserId, secondUserId);
        String query = "INSERT INTO friends (first_user_id, second_user_id) VALUES (?, ?)";
        try {
            int updatedRow = jdbcTemplate.update(query, firstUserId, secondUserId);
            if (updatedRow > 0) {
                log.info("{} - Пользователь с id: {} добавлен в друзья к пользователю с id: {}", TAG, firstUserId, secondUserId);
            } else {
                log.error("{} - Пользователь с id: {} НЕ добавлен в друзья к пользователю с id: {}", TAG, firstUserId, secondUserId);
            }
        } catch (DataAccessException e) {
            throw new NotFoundException("При добавлени друга пользователь не найден");
        }
    }

    @Override
    public void deleteFriend(Long firstUserId, Long secondUserId) {
        log.info("{} - Удаление пользователя с id: {} из друзей у пользователя с id: {}", TAG, firstUserId, secondUserId);
        String query = "DELETE friends WHERE first_user_id = ? AND second_user_id = ?";
        int updatedRow = jdbcTemplate.update(query, firstUserId, secondUserId);
        if (updatedRow > 0) {
            log.info("{} - Пользователь с id: {} удален из друзей у пользователя с id: {}", TAG, firstUserId, secondUserId);
        } else {
            log.error("{} - Пользователь с id: {} НЕ удален из друзей у пользователя с id: {}", TAG, firstUserId, secondUserId);
        }
    }

    @Override
    public Set<Long> getFriends(Long id) {
        log.info("{} - Получение всех друзей пользователя с id: {}", TAG, id);
        try {
            List<Long> friends = jdbcTemplate.queryForList(format(""
                    + "SELECT second_user_id "
                    + "FROM friends "
                    + "WHERE first_user_id = %d", id), Long.class);
            log.info("{} - Получены все друзья пользователя с id: {}", TAG, id);
            return new TreeSet<>(friends);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException("Пользователь не найден");
        }
    }
}