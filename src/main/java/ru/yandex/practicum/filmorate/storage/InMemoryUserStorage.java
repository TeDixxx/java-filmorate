package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.*;

@Slf4j
@Component
public class InMemoryUserStorage implements UserStorage {

    private final Map<Long, User> users = new HashMap<>();
    private long currentID = 1;

    @Override
    public User createUser(User user) {
        if (checkValid(user)) {
            user.setId(currentID++);
            users.put(user.getId(), user);
            log.debug("Был добавлен пользователь{}", user);
        }
        return user;
    }

    @Override
    public User updateUser(User user) {
        if (users.containsKey(user.getId())) {
            users.put(user.getId(), user);
            log.debug("Полтзователь был обновлен{}", user);
        } else throw new NotFoundException("Пользователь не найден");
        return user;
    }

    @Override
    public User getUser(Long userID) throws NotFoundException {
        User user;
        if (users.containsKey(userID)) {
            user = users.get(userID);
        } else {
            throw new NotFoundException("Пользователь с таким ID не существует");
        }
        return user;
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
