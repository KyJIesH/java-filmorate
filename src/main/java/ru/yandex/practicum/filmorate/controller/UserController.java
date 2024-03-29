package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.user.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    private static final String TAG = "USER CONTROLLER";
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody @Valid User user) {
        log.info("{} -  Пришел запрос на создание пользователя {}", TAG, user);
        return new ResponseEntity<>(userService.createUser(user), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        log.info("{} - Пришел запрос на получение списка всех пользователей", TAG);
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/id")
    public ResponseEntity<User> getUser(@RequestParam Long id) throws ValidationException, NotFoundException {
        log.info("{} -  Пришел запрос на получение пользователя по id {}", TAG, id);
        if (id == null || id <= 0) {
            throw new ValidationException("Некорректный формат id");
        }
        return new ResponseEntity<>(userService.getUser(id), HttpStatus.OK);
    }

    @GetMapping("/{id}/friends")
    public ResponseEntity<Set<User>> getFriendsUser(@PathVariable Long id) throws ValidationException, NotFoundException {
        log.info("{} - Пришел запрос на получение всех друзей пользователя по id {}", TAG, id);
        if (id == null || id <= 0) {
            throw new ValidationException("Некорректный формат id");
        }
        return new ResponseEntity<>(userService.getFriendsUser(id), HttpStatus.OK);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public ResponseEntity<Set<User>> getCommonFriends(@PathVariable Long id,
                                                      @PathVariable Long otherId) throws ValidationException, NotFoundException {
        log.info("{} - Пришел запрос на получение общих друзей пользователей {} и {}", TAG, id, otherId);
        if (id == null || id <= 0) {
            throw new ValidationException("Некорректный формат id первого пользователя");
        }
        if (otherId == null || otherId <= 0) {
            throw new ValidationException("Некорректный формат id второго пользователя");
        }
        return new ResponseEntity<>(userService.getCommonFriends(id, otherId), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<User> updateUser(@RequestBody @Valid User user) throws NotFoundException {
        log.info("{} -  Пришел запрос на обновление пользователя {}", TAG, user);
        return new ResponseEntity<>(userService.updateUser(user), HttpStatus.OK);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public ResponseEntity<String> putFriendsUser(@PathVariable Long id,
                                                 @PathVariable Long friendId) throws ValidationException, NotFoundException {
        log.info("{} -  Пришел запрос на добавление пользователя {} в друзья к пользователю {}", TAG, id, friendId);
        if (id == null || id <= 0) {
            throw new ValidationException("Некорректный формат id первого пользователя");
        }
        if (friendId == null || friendId <= 0) {
            throw new ValidationException("Некорректный формат id второго пользователя");
        }
        userService.putFriendsUser(id, friendId);
        return new ResponseEntity<>("Пользователь с id: " + id + " - добавлен в друзья к пользователю" +
                " с id: " + friendId, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteUser(Long id) throws ValidationException, NotFoundException {
        log.info("{} -  Пришел запрос на удаление пользователя по id {}", TAG, id);
        if (id == null || id <= 0) {
            throw new ValidationException("Некорректный формат id");
        }
        userService.deleteUser(id);
        return new ResponseEntity<>("Пользователь с id: " + id + " - удален", HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public ResponseEntity<String> deleteFriendsUser(@PathVariable Long id,
                                                    @PathVariable Long friendId) throws ValidationException, NotFoundException {
        log.info("{} -  Пришел запрос на удаление пользователя {} из друзей у пользователя {}", TAG, id, friendId);
        if (id == null || id <= 0) {
            throw new ValidationException("Некорректный формат id первого пользователя");
        }
        if (friendId == null || friendId <= 0) {
            throw new ValidationException("Некорректный формат id второго пользователя");
        }
        userService.deleteFriendsUser(id, friendId);
        return new ResponseEntity<>("Пользователь с id: " + id + " - удален из друзей у пользователя" +
                " с id: " + friendId, HttpStatus.OK);
    }
}