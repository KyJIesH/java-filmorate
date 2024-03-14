package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    private Map<Integer, User> users = new HashMap<>();
    private static final String TAG = "USER CONTROLLER";
    private int idGenerator = 1;

    @PostMapping
    public User createUser(@RequestBody @Valid User user) { //Создание пользователя
        log.info("{} - Создание пользователя " + user, TAG);
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        // без этого не проходят 5 тестов , а с этим 10
        user.setId(idGenerator++);
        users.put(user.getId(), user);
        return user;
    }

    @PutMapping
    public User updateUser(@RequestBody @Valid User user) { //Обновление пользователя
        log.info("{} - Обновление пользователя " + user, TAG);
        if (!users.containsKey(user.getId())) {
            throw new ValidationException("id не найден");
        }
        users.put(user.getId(), user);
        return user;
    }

    @GetMapping
    public List<User> getAllUsers() { //Получение списка всех пользователей
        log.info("{} - Получение списка всех пользователей " + users.values(), TAG);
        return new ArrayList<>(users.values());
    }
}
