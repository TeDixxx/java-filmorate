package ru.yandex.practicum.filmorate.storage.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.storage.interfaces.LikeStorage;

@Repository
public class LikeDbStorage implements LikeStorage {

    JdbcTemplate jdbcTemplate;

    public LikeDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addLike(Long userId, Long filmId) {
        String sqlQuery = "INSERT INTO film_likes (film_id, user_id)  VALUES(?,?)";
        jdbcTemplate.update(sqlQuery, userId, filmId);
    }

    @Override
    public void deleteLike(Long userId, Long filmId) {
        String sqlQuery = "DELETE FROM film_likes WHERE film_id=? AND user_id = ?";
        jdbcTemplate.update(sqlQuery, userId, filmId);
    }
}
