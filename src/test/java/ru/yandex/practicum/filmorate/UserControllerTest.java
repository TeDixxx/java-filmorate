package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controllers.UserController;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

public class UserControllerTest {

    UserController userController;

    @BeforeEach
    void beforeEach() {
        userController = new UserController();
    }

    @Test
    void shouldAddUser() throws ValidationException {
        User testUser = new User("developer@yandex.ru", "junior_dev", "Dmitry",
                LocalDate.of(2000, 10, 14));
        userController.createUser(testUser);
        Assertions.assertEquals(1, userController.getAllUsers().size(), "Ошибка добавления пользователя");
    }

    @Test
    void shouldUpdateUser() throws ValidationException {
        User testUser = new User("developer@yandex.ru", "junior_dev", "Dmitry",
                LocalDate.of(2000, 10, 14));
        userController.createUser(testUser);
        User newUser = userController.updateUser(testUser);

        Assertions.assertEquals(testUser, newUser, "Ошибка обновления пользователя");
    }

    @Test
    void checkValidUserEmailEmpty() {
        User testUser = new User("", "junior_dev", "Dmitry",
                LocalDate.of(2000, 10, 14));
        Assertions.assertFalse(userController.checkValid(testUser));
    }

    @Test
    void checkValidUserEmailChar() {
        User testUser = new User("developeryandex.ru", "junior_dev", "Dmitry",
                LocalDate.of(2000, 10, 14));
        Assertions.assertFalse(userController.checkValid(testUser));
    }

    @Test
    void checkValidUserBirthDay() {
        User testUser = new User("developer@yandex.ru", "junior_dev", "Dmitry",
                LocalDate.of(2024, 10, 14));
        Assertions.assertFalse(userController.checkValid(testUser));
    }
}
