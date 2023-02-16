package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/users")
public class UserController {

    private final HashMap<Integer, User> usersMap = new HashMap<>();
    private int userId = 0;

    @PostMapping
    public User createUser(@RequestBody User user) throws ValidationException {
        if (checkValid(user)) {
            log.info("Получен запрос на добавление пользователя");
            user.setId(userId++);
            usersMap.put(user.getId(), user);
        } else {
            log.debug("Ошибка при добавлении пользователя{}", user);
            throw new ValidationException("Ошибка при добавлении пользователя");
        }
        return user;
    }

    @PutMapping
    public User updateUser(@RequestBody User user) throws ValidationException {
        if (checkValid(user) && usersMap.containsKey(user.getId())) {
            log.info("Получен запрос на обновление пользователя");
            usersMap.put(user.getId(), user);
        } else {
            log.debug("Ошибка обновления пользователя {}", user);
            throw new ValidationException("Ошибка при обновлении пользователя");
        }
        return user;
    }

    @GetMapping
    public List<User> getAllUsers() {
        log.info("Получен запрос на просмотр списка всех пользователей");
        return new ArrayList<>(usersMap.values());
    }

    public boolean checkValid(User user) {
        if (user.getEmail().isEmpty()
                || !user.getEmail().contains("@")
                || user.getBirthday().isAfter(LocalDate.now())) {
            return false;
        }
        if (user.getName().isEmpty()) {
            user.setName(user.getName());
        }
        return true;
    }
}
