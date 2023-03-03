package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;
import java.util.ArrayList;
import java.util.List;



@Service
public class UserService {
    UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }


    public User createUser(User user) {
        return userStorage.createUser(user);
    }

    public User updateUser(User user) {
        return userStorage.updateUser(user);
    }

    public User getUser(Long userID) {
        return userStorage.getUser(userID);
    }

    public List<User> getAllUsers() {
        return userStorage.getAllUsers();
    }

    public void addFriend(Long userID, Long friendID) {
        userStorage.getUser(userID).addFriends(friendID);
        userStorage.getUser(friendID).addFriends(userID);
    }

    public void deleteFriend(Long userID, Long friendID) {
        userStorage.getUser(userID).removeFriend(friendID);
        userStorage.getUser(friendID).removeFriend(userID);
    }

    public List<User> getUserFriends(Long userID) {
        List<User> userFriends = new ArrayList<>();
        for (Long id : getUser(userID).getFriends()) {
            userFriends.add(userStorage.getAllUsers().get(Math.toIntExact(id)));
        }
        return userFriends;
    }

    public List<User> getCommonFriends(Long userID, Long friendID) {
        List<User> commonFriends = new ArrayList<>();
        if (getUser(userID).getFriends().isEmpty()) {
            return commonFriends;
        }
        commonFriends.addAll(getUserFriends(userID));
        commonFriends.retainAll(getUserFriends(friendID));
        return commonFriends;
    }


}
