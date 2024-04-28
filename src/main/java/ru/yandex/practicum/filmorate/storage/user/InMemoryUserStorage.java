package ru.yandex.practicum.filmorate.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Deprecated
@Slf4j
@Component
public class InMemoryUserStorage implements UserStorage {
    private Map<Long, User> users = new HashMap<>();
    private static final String TAG = "USER STORAGE";
    private long idGenerator = 1;

    @Override
    public User createUser(User user) {
        log.info("{} - Попытка создания пользователя {}", TAG, user);
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        user.setId(idGenerator++);
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        log.info("{} - Попытка получения списка всех пользователей", TAG);
        List<User> userList = new ArrayList<>(users.values());
        log.info("{} - Попытка получения всех пользователей завершена {}", TAG, userList);
        return userList;
    }

    @Override
    public User getUser(Long id) {
        log.info("{} - Попытка получения пользователя по id {}", TAG, id);
        if (!users.containsKey(id)) {
            log.error("{} - Пользоваетель с id {} не найден", TAG, id);
            throw new NotFoundException("Пользователь не найден");
        }
        return users.get(id);
    }

    @Override
    public List<User> getUsersByIds(List<Long> ids) {
        return null;
    }

    @Override
    public User updateUser(User user) {
        log.info("{} - Попытка обновления пользователя {}", TAG, user);
        // Проверка на исключение
        getUser(user.getId());
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public void deleteUser(Long id) {
        log.info("{} - Попытка удаления пользователя по id {}", TAG, id);
        if (users.containsKey(id)) {
            log.error("{} - По данному id {} нет пользователя", TAG, id);
            throw new NotFoundException("Удаляемый пользователь не найден");
        }
        users.remove(id);
    }
}
