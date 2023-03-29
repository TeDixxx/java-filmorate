package ru.yandex.practicum.filmorate.storage.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.interfaces.UserStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserDbStorage implements UserStorage {

    private final JdbcTemplate jdbcTemplate;

    public UserDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User createUser(User user) {
        Map<String, Object> sqlValues = new HashMap<>();
        sqlValues.put("login", user.getLogin());
        sqlValues.put("name", user.getName());
        sqlValues.put("email", user.getEmail());
        sqlValues.put("birthday", user.getBirthday());
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("user_id");
        user.setId(simpleJdbcInsert.executeAndReturnKey(sqlValues).longValue());

        return user;
    }

    @Override
    public User updateUser(User user) throws NotFoundException {
        String sqlQuery = "UPDATE users" + "SET name = ?, email = ?,birthday = ?" + "WHERE user_id = ?";

        jdbcTemplate.update(sqlQuery, user.getName(), user.getEmail(), user.getBirthday());
        return user;
    }

    @Override
    public User getUser(Long id) throws NotFoundException {
        String sqlQuery = "SELECT user_id,login,email,name,birthday" + "FROM users" + "WHERE user_id = ?";

        return jdbcTemplate.queryForObject(sqlQuery, this::mapRowToUser, id);
    }

    @Override
    public void removeUser(User user) {
        String sqlQuery = "DELETE FROM users" + "WHERE user_id = ?";
        jdbcTemplate.update(sqlQuery, user.getId());

    }

    @Override
    public List<User> getAllUsers() {
        String sqlQuery = "SELECT*" + "FROM users";

        return jdbcTemplate.query(sqlQuery,this::mapRowToUser) ;
    }

    private User mapRowToUser(ResultSet resultSet, int rowNumber) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt("user_id"));
        user.setEmail(resultSet.getString("email"));
        user.setLogin(resultSet.getString("login"));
        user.setName(resultSet.getString("name"));
        user.setBirthday(resultSet.getDate("birthday").toLocalDate());

        return user;
    }
}
