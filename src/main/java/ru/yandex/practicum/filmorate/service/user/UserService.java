package ru.yandex.practicum.filmorate.service.user;

import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Set;

public interface UserService {
    User createUser(User user);

    List<User> getAllUsers();

    User getUser(Long id) throws NotFoundException;

    User updateUser(User user) throws NotFoundException;

    void deleteUser(Long id) throws NotFoundException;

    void putFriendsUser(Long firstUserId, Long secondUserId) throws NotFoundException;

    void deleteFriendsUser(Long firstUserId, Long secondUserId) throws NotFoundException;

    Set<User> getFriendsUser(Long id) throws NotFoundException;

    Set<User> getCommonFriends(Long firstUserId, Long secondUserId) throws NotFoundException;
}
