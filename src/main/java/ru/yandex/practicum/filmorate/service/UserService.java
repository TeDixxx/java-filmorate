package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ValidationException;
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

    public User createUser(User user) throws ValidationException {
        return userStorage.createUser(user);
    }

    public User updateUser(User user) throws ValidationException {
        return userStorage.updateUser(user);
    }

    public List<User> getAllUsers(){
        return userStorage.getAllUsers();
    }

    public User getUser(long id) throws ValidationException {
        return userStorage.getUser(id);
    }

    public void addFriend(long userId,long friendId) throws ValidationException {
        getUser(userId).addFriends(friendId);
        getUser(friendId).addFriends(userId);
    }


    public List<User> getCommonFriends(long firstUserId, long secondUserId) throws ValidationException {
        List<User> commonList = new ArrayList<>(getUserFriends(firstUserId));
        commonList.retainAll(getUserFriends(secondUserId));

        return commonList;
    }

    public List<User> getUserFriends(long id) throws ValidationException {
        List<User> friendList = new ArrayList<>();

        for (long i : getUser(id).getFriends()) {
            friendList.add(userStorage.getUser(i));
        }
        return friendList;
    }

    public void deleteFriend(long id, long friendId) throws ValidationException {
        getUser(id).removeFriend(friendId);
    }

}
