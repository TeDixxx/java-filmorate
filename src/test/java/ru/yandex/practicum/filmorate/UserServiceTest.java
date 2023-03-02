package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controllers.UserController;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import java.time.LocalDate;

public class UserServiceTest {

    User user = new User( 1,"xxx.@mail.ru","xxx","dmitry",
            LocalDate.of(2000,10,14));

    InMemoryUserStorage inMemoryUserStorage = new InMemoryUserStorage();
    UserService userService = new UserService(inMemoryUserStorage);

    UserController userController = new UserController(userService);



    @Test
    public void shouldGetUser() throws ValidationException {
        userController.createUser(user);
        userController.getUser(user.getId());

        Assertions.assertEquals(userController.getAllUsers().get(0),user);
    }
}
