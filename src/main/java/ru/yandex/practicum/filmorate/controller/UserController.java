package ru.yandex.practicum.filmorate.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.user.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/users")
@Slf4j
@AllArgsConstructor
public class UserController {
    private static final String TAG = "USER CONTROLLER";
    private final UserService userService;

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

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        log.info("{} -  Пришел запрос на получение пользователя по id {}", TAG, id);
        userService.checkUserId(id);
        return new ResponseEntity<>(userService.getUser(id), HttpStatus.OK);
    }

    @GetMapping("/{id}/friends")
    public ResponseEntity<Set<User>> getFriendsUser(@PathVariable Long id) {
        log.info("{} - Пришел запрос на получение всех друзей пользователя по id {}", TAG, id);
        userService.checkUserId(id);
        userService.getUser(id);
        Set<User> users = userService.getFriendsUser(id);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public ResponseEntity<Set<User>> getCommonFriends(@PathVariable Long id,
                                                      @PathVariable Long otherId) {
        log.info("{} - Пришел запрос на получение общих друзей пользователей {} и {}", TAG, id, otherId);
        userService.checkUserId(id);
        userService.checkUserId(otherId);
        return new ResponseEntity<>(userService.getCommonFriends(id, otherId), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<User> updateUser(@RequestBody @Valid User user) {
        log.info("{} -  Пришел запрос на обновление пользователя {}", TAG, user);
        return new ResponseEntity<>(userService.updateUser(user), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/friends/{friendId}")
    public ResponseEntity<String> putFriendsUser(@PathVariable Long id,
                                                 @PathVariable Long friendId) {
        log.info("{} -  Пришел запрос на добавление пользователя {} в друзья к пользователю {}", TAG, id, friendId);
        userService.checkUserId(id);
        userService.checkUserId(friendId);
        userService.putFriendsUser(id, friendId);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteUser(Long id) {
        log.info("{} -  Пришел запрос на удаление пользователя по id {}", TAG, id);
        userService.checkUserId(id);
        userService.deleteUser(id);
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public ResponseEntity<String> deleteFriendsUser(@PathVariable Long id,
                                                    @PathVariable Long friendId) {
        log.info("{} -  Пришел запрос на удаление пользователя {} из друзей у пользователя {}", TAG, id, friendId);
        userService.checkUserId(id);
        userService.checkUserId(friendId);
        userService.deleteFriendsUser(id, friendId);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}