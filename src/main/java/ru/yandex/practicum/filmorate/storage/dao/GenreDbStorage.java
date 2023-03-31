package ru.yandex.practicum.filmorate.storage.dao;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.interfaces.GenreStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class GenreDbStorage implements GenreStorage {
    private final JdbcTemplate jdbcTemplate;

    public GenreDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Genre getById(Long id) {
        if (!isExist(id)) {
            return null;
        }
        String sqlQuery = "SELECT* FROM genres WHERE genre_id = ?";
        return jdbcTemplate.queryForObject(sqlQuery, this::mapRowToGenre, id);
    }

    @Override
    public List<Genre> getAll() {
        String sqlQuery = "SELECT* FROM genres ORDER BY genre_id";

        return jdbcTemplate.query(sqlQuery, this::mapRowToGenre);
    }

    @Override
    public List<Genre> getFilmGenre(Long filmId) {
        isExist(filmId);
        String sqlQuery = "SELECT * FROM film_genres JOIN genres ON genres.genre_id = film_genres.genre_id WHERE film_id = ?";

        return jdbcTemplate.query(sqlQuery, this::mapRowToGenre, filmId);
    }


    private Genre mapRowToGenre(ResultSet resultSet, int rowNumber) throws SQLException {
        Genre genre = new Genre();
        genre.setId(resultSet.getLong("genre_id"));
        genre.setName(resultSet.getString("name"));

        return genre;
    }

    @Override
    public boolean isExist(Long id) {
        String sqlQuery = "SELECT genre_id FROM genres WHERE genre_id = ?";
        return jdbcTemplate.queryForRowSet(sqlQuery, id).next();
    }
}
