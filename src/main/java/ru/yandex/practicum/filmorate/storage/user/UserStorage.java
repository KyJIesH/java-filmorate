package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserStorage {

    User createUser(User user);

    List<User> getAllUsers();

    User getUser(Long id) throws NotFoundException;

    User updateUser(User user) throws NotFoundException;

    void deleteUser(Long id) throws NotFoundException;
}
