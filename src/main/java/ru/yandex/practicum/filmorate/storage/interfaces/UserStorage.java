package ru.yandex.practicum.filmorate.storage.interfaces;

import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserStorage {
    User createUser(User user) throws ValidationException;

    User updateUser(User user) throws NotFoundException;

    User getUser(Long id) throws NotFoundException;

    void removeUser(User user);

    List<User> getAllUsers();

    boolean isExists(Long id);


}
