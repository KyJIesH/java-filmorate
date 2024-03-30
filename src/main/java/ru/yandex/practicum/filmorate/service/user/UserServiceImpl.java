package ru.yandex.practicum.filmorate.service.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    private static final String TAG = "USER SERVICE";
    private final UserStorage userStorage;

    public UserServiceImpl(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    @Override
    public User createUser(User user) {
        log.info("{} - Обработка запроса на добавление пользователя", TAG);
        return userStorage.createUser(user);
    }

    @Override
    public List<User> getAllUsers() {
        log.info("{} - Обработка запроса на получение всех пользователей", TAG);
        return userStorage.getAllUsers();
    }

    @Override
    public User getUser(Long id) throws NotFoundException {
        log.info("{} - Обработка запроса на получение пользователя по id {}", TAG, id);
        return userStorage.getUser(id);
    }

    @Override
    public User updateUser(User user) throws NotFoundException {
        log.info("{} - Обработка запроса на обновление пользователя {}", TAG, user);
        return userStorage.updateUser(user);
    }

    @Override
    public void deleteUser(Long id) throws NotFoundException {
        log.info("{} - Обработка запроса на удаление пользователя по id {}", TAG, id);
        Set<Long> friendsUser = userStorage.getUser(id).getFriends();
        for (Long idUser : friendsUser) {
            userStorage.getUser(idUser).getFriends().remove(id);
        }
        userStorage.deleteUser(id);
    }

    @Override
    public void putFriendsUser(Long firstUserId, Long secondUserId) throws NotFoundException {
        log.info("{} - Обработка запроса на добавление пользователя {} в друзья к пользователю {}",
                TAG, firstUserId, secondUserId);
        userStorage.getUser(firstUserId).getFriends().add(secondUserId);
        userStorage.getUser(secondUserId).getFriends().add(firstUserId);
    }

    @Override
    public void deleteFriendsUser(Long firstUserId, Long secondUserId) throws NotFoundException {
        log.info("{} - Обработка запроса на удаление пользователя {} из друзей у пользователя {}",
                TAG, firstUserId, secondUserId);
        userStorage.getUser(firstUserId).getFriends().remove(secondUserId);
        userStorage.getUser(secondUserId).getFriends().remove(firstUserId);
    }

    @Override
    public Set<User> getFriendsUser(Long id) throws NotFoundException {
        log.info("{} - Обработка запроса на получение всех друзей пользователя по id {}", TAG, id);
        Set<Long> friendsIds = userStorage.getUser(id).getFriends();
        Set<User> friends = new HashSet<>();
        for (Long userId : friendsIds) {
            friends.add(getUser(userId));
        }
        return friends;
    }

    @Override
    public Set<User> getCommonFriends(Long firstUserId, Long secondUserId) throws NotFoundException {
        log.info("{} - Обработка запроса на получение общих друзей пользователей {} и {}", TAG, firstUserId, secondUserId);
        Set<User> firstUserFriends = getFriendsUser(firstUserId);
        Set<User> secondUserFriends = getFriendsUser(secondUserId);
        Set<User> temp = new HashSet<>(firstUserFriends);
        temp.retainAll(secondUserFriends);
        return temp;
    }
}
