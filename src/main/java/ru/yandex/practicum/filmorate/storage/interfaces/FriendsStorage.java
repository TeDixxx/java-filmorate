package ru.yandex.practicum.filmorate.storage.interfaces;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface FriendsStorage {

    void addFriend(Long userId,Long friendId);

    void deleteFriend(Long userId,Long friendId);

    List<User> getAllUserFriends(Long userId);

    List<User> getCommonFriends(Long userId, Long secondId);
}
