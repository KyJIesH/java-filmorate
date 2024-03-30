package ru.yandex.practicum.filmorate.service.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Set;

public interface UserService {
    User createUser(User user);

    List<User> getAllUsers();

    User getUser(Long id);

    User updateUser(User user);

    void deleteUser(Long id);

    void putFriendsUser(Long firstUserId, Long secondUserId);

    void deleteFriendsUser(Long firstUserId, Long secondUserId);

    Set<User> getFriendsUser(Long id);

    Set<User> getCommonFriends(Long firstUserId, Long secondUserId);

    void checkUserId(Long id);
}
