package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;
import java.time.LocalDate;
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
       if(!checkValid(user))  {
           throw new ValidationException("Ошибка создания пользователя");
       }
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

    public void addFriend(Long userID, Long friendID) throws ValidationException {
        if(checkValid(userStorage.getUser(userID)) && checkValid(userStorage.getUser(friendID)) && friendID > 0) {
            userStorage.getUser(userID).addFriends(friendID);
            userStorage.getUser(friendID).addFriends(userID);
        } else {
            throw new ValidationException("Ошибка добавления в друзья");
        }
    }

    public void deleteFriend(Long userID, Long friendID) {
        userStorage.getUser(userID).removeFriend(friendID);
        userStorage.getUser(friendID).removeFriend(userID);
    }

    public List<User> getUserFriends(Long userID) throws NotFoundException {
        List<User> userFriends = new ArrayList<>();
            for (Long id : getUser(userID).getFriends()) {
                userFriends.add(userStorage.getAllUsers().get(Math.toIntExact(id)));
            }
        return userFriends;
    }

    public List<User> getCommonFriends(Long userID, Long friendID)  {
        List<User> commonFriends = new ArrayList<>(getUserFriends(userID));
        commonFriends.retainAll(getUserFriends(friendID));
        return commonFriends;
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
