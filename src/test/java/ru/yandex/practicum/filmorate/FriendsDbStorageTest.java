package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.dao.FriendsDbStorage;
import ru.yandex.practicum.filmorate.storage.dao.UserDbStorage;

import java.time.LocalDate;


import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class FriendsDbStorageTest {
    private final FriendsDbStorage friendsDbStorage;
    private final UserDbStorage userDbStorage;


    @Test
    void addFriend() {
        User firstU = new User();
        firstU.setEmail("devede@email.ru");
        firstU.setLogin("ooo");
        firstU.setName("Jon");
        firstU.setBirthday(LocalDate.of(2000, 10, 14));

        userDbStorage.createUser(firstU);

        User secondU = new User();
        secondU.setEmail("opachki@meail.ru");
        secondU.setLogin("aaa");
        secondU.setName("Vlad");
        secondU.setBirthday(LocalDate.of(2003, 11, 21));


        userDbStorage.createUser(secondU);

        assertTrue(friendsDbStorage.getAllUserFriends(firstU.getId()).isEmpty());

        friendsDbStorage.addFriend(firstU.getId(), secondU.getId());

        assertFalse(friendsDbStorage.getAllUserFriends(firstU.getId()).isEmpty());
    }

    @Test
    void deleteFriend() {
        User user1 = new User();
        user1.setName("Artem");
        user1.setLogin("login_1");
        user1.setEmail("test1@email.ru");
        user1.setBirthday(LocalDate.of(1999, 7, 15));
        userDbStorage.createUser(user1);

        User user2 = new User();
        user2.setName("Light");
        user2.setLogin("login_2");
        user2.setEmail("test2@email.ru");
        user2.setBirthday(LocalDate.of(200, 8, 16));
        userDbStorage.createUser(user2);

        assertTrue(friendsDbStorage.getAllUserFriends(user1.getId()).isEmpty());

        friendsDbStorage.addFriend(user1.getId(), user2.getId());

        assertFalse(friendsDbStorage.getAllUserFriends(user1.getId()).isEmpty());

        friendsDbStorage.deleteFriend(user1.getId(), user2.getId());

        assertTrue(friendsDbStorage.getAllUserFriends(user1.getId()).isEmpty());
    }

    @Test
    void getAllUserFriends() {
        User firstU = new User();
        firstU.setEmail("rrr@email.ru");
        firstU.setLogin("mert");
        firstU.setName("Stan");
        firstU.setBirthday(LocalDate.of(2002, 10, 14));

        userDbStorage.createUser(firstU);

        User secondU = new User();
        secondU.setEmail("popo@meail.ru");
        secondU.setLogin("ret");
        secondU.setName("Fedor");
        secondU.setBirthday(LocalDate.of(2005, 10, 21));

        userDbStorage.createUser(secondU);

        assertTrue(friendsDbStorage.getAllUserFriends(firstU.getId()).isEmpty());

        friendsDbStorage.addFriend(firstU.getId(), secondU.getId());

        Assertions.assertEquals(1, friendsDbStorage.getAllUserFriends(firstU.getId()).size());
    }

    @Test
    void getCommonFriends() {
        User user1 = new User();
        user1.setName("User_for1");
        user1.setLogin("lll");
        user1.setEmail("test_34@email.ru");
        user1.setBirthday(LocalDate.of(2001, 7, 15));
        userDbStorage.createUser(user1);

        User user2 = new User();
        user2.setName("user_for2");
        user2.setLogin("yyy");
        user2.setEmail("test25@email.ru");
        user2.setBirthday(LocalDate.of(2002, 4, 15));
        userDbStorage.createUser(user2);

        User user3 = new User();
        user3.setName("User3");
        user3.setLogin("testLogin232");
        user3.setEmail("test235@email.ru");
        user3.setBirthday(LocalDate.of(2003, 8, 16));
        userDbStorage.createUser(user3);

        assertTrue(friendsDbStorage.getCommonFriends(user1.getId(), user2.getId()).isEmpty());

        friendsDbStorage.addFriend(user1.getId(), user3.getId());
        friendsDbStorage.addFriend(user2.getId(), user3.getId());

        Assertions.assertEquals(1, friendsDbStorage.getCommonFriends(user1.getId(), user2.getId()).size());

    }
}
