package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class UserService {
    UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }


    public User createUser(User user) throws ValidationException {
        return userStorage.createUser(user);
    }

    public User updateUser(User user) throws ValidationException {
        return userStorage.updateUser(user);
    }

    public User getUser(long userID) throws ValidationException {
        return userStorage.getUser(userID);
    }

    public List<User> getAllUsers() {
        return userStorage.getAllUsers();
    }

    public void addFriend(long userID, long friendID) throws ValidationException {
        userStorage.getUser(userID).addFriends(friendID);
        userStorage.getUser(friendID).addFriends(userID);
    }

    public void deleteFriend(long userID, long friendID) throws ValidationException {
        userStorage.getUser(userID).removeFriend(friendID);
        userStorage.getUser(friendID).removeFriend(userID);
    }

    public List<User> getUserFriends(long userID) {

        return userStorage.getAllUsers().stream().filter(user -> {
            try {
                return userStorage.getUser(userID).getFriends()
                        .contains(user.getId());
            } catch (ValidationException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());
    }

    public List<User> getCommonFriends(Long userID, Long friendID) throws ValidationException {
        List<Long> userFriends = new ArrayList<>(userStorage.getUser(userID).getFriends());
        userFriends.retainAll(userStorage.getUser(friendID).getFriends());
        return userStorage.getAllUsers().stream().filter(user -> userFriends.contains(user.getId()))
                .collect(Collectors.toList());
    }

}
