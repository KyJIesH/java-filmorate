package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserStorage {

    User createUser(User user);

    List<User> getAllUsers();

    User getUser(Long id);

    User updateUser(User user);

    void deleteUser(Long id);
}
