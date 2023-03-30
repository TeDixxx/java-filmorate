package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.interfaces.FriendsStorage;
import ru.yandex.practicum.filmorate.storage.interfaces.UserStorage;

import java.time.LocalDate;
import java.util.List;

@Service
public class UserService {
    UserStorage userStorage;
    FriendsStorage friendsStorage;

    @Autowired
    public UserService(@Qualifier("userDbStorage") UserStorage userStorage, FriendsStorage friendsStorage) {
        this.userStorage = userStorage;
        this.friendsStorage = friendsStorage;
    }

    public User createUser(User user) throws ValidationException {
        if (!checkValid(user)) {
            throw new ValidationException("Ошибка создания пользователя");
        }
        return userStorage.createUser(user);
    }

    public User updateUser(User user) throws NotFoundException {
        if (!checkValid(user)) {
            throw new NotFoundException("Ошибка обновления пользователя, user not found");
        }
        isExists(user.getId());
        return userStorage.updateUser(user);
    }

    public User getUser(Long userID) throws NotFoundException {
        isExists(userID);
        return userStorage.getUser(userID);
    }

    public List<User> getAllUsers() {
        return userStorage.getAllUsers();
    }

    public void addFriend(Long userID, Long friendID) {
            isExists(userID);
            isExists(friendID);
            friendsStorage.addFriend(userID, friendID);
    }

    public void deleteFriend(Long userID, Long friendID) {
        isExists(userID);
        isExists(friendID);
        friendsStorage.deleteFriend(userID, friendID);
    }

    public List<User> getUserFriends(Long userID) throws NotFoundException {
       isExists(userID);
        return friendsStorage.getAllUserFriends(userID);
    }

    public List<User> getCommonFriends(Long userID, Long friendID) {
       isExists(userID);
       isExists(friendID);
        return friendsStorage.getCommonFriends(userID, friendID);
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

    public void isExists(Long id) {
        if (!userStorage.isExists(id)) {
            throw new NotFoundException("Не найдено!");
        }
    }
}
