package ru.yandex.practicum.filmorate.storage.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.interfaces.FriendsStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class FriendsDbStorage implements FriendsStorage {
    private final JdbcTemplate jdbcTemplate;

    public FriendsDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addFriend(Long userId, Long friendId) {
        String sqlQuery = "INSERT INTO user_friends (user_id,friend_id)" + "VALUES (?,?)";

        jdbcTemplate.update(sqlQuery, userId, friendId);
    }

    @Override
    public void deleteFriend(Long userId, Long friendId) {
        String sqlQuery = "DELETE FROM user_friends" + "WHERE user_id = ? AND friend_id = ?";
        jdbcTemplate.update(sqlQuery, userId, friendId);
    }

    @Override
    public List<User> getAllUserFriends(Long userId) {
        String sqlQuery = "SELECT*" + "FROM users AS u" + "LEFT OUTER JOIN user_friends AS uf ON u.user_id" +
                "WHERE uf.user_id = ?";
        return jdbcTemplate.query(sqlQuery,this::mapRowToUser,userId);
    }

    @Override
    public List<User> getCommonFriends(Long userId, Long secondId) {
        String sqlQuery = "SELECT*" + "FROM users" + "WHERE user_id IN" + "(SELECT friend_id FROM user_friends " +
                "WHERE user_id IN(?,?) GROUP BY friend_id HAVING COUNT(friend_id >1))";
        return jdbcTemplate.query(sqlQuery,this::mapRowToUser,userId,secondId);
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

