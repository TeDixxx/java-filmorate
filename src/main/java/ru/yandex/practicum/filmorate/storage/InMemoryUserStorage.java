package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.*;

@Slf4j
@Component
public class InMemoryUserStorage implements UserStorage {

    private final Map<Long, User> users = new HashMap<>();
    private long currentID = 1;

    @Override
    public User createUser(User user) throws ValidationException {
        if (checkValid(user)) {
            log.info("Добавление пользователя");
            user.setId(currentID++);
            user.setFriends(new HashSet<>());
            users.put(user.getId(), user);
        } else {
            log.debug("Ошибка при создании пользователя{}", user);
            throw new ValidationException("Ошибка создания пользователя");
        }

        return user;
    }

    @Override
    public User updateUser(User user) throws ValidationException {
        if (checkValid(user)) {
            log.info("Обновление пользователя");
            user.setFriends(new HashSet<>());
            users.put(user.getId(), user);
        } else {
            log.debug("Ошибка при обновлении пользователя{}", user);
            throw new ValidationException("Ошибка обновления пользователя");
        }
        return user;
    }

    @Override
    public User getUser(long id) throws NotFoundException {
        User user = users.get(id);
        if (!users.containsKey(user.getId()) || user.getId() < 0) {
            throw new NotFoundException("Пользователь не найден!");
        }
        return users.get(id);
    }


    @Override
    public void removeUser(User user) {
        users.remove(user.getId());
    }

    @Override
    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }


    public boolean checkValid(User user) {
        if (user.getEmail().isEmpty()
                || !user.getEmail().contains("@")
                || user.getBirthday().isAfter(LocalDate.now())
                || user.getLogin().contains(" ")
                || user.getLogin().isEmpty()
                || user.getEmail() == null) {

            return false;
        }
        if (user.getName() == null || user.getName().isEmpty() || user.getName().equals(" ")) {
            user.setName(user.getLogin());
        }
        return true;
    }

}
