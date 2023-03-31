package ru.yandex.practicum.filmorate.storage.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
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
            throw new NotFoundException("Не найдено");
        }
        String sqlQuery = "SELECT* FROM genres WHERE genre_id = ?";

        return jdbcTemplate.queryForObject(sqlQuery, this::mapRowToGenre, id);
    }

    @Override
    public List<Genre> getAll() {
        String sqlQuery = "SELECT* FROM genres ORDER BY 1";

        return jdbcTemplate.query(sqlQuery, this::mapRowToGenre);
    }

    @Override
    public List<Genre> getFilmGenre(Long filmId) {
        String sqlQuery = "SELECT FROM genres AS g LEFT JOIN film_genres AS fg ON g.genre_id = fg.genre_id WHERE fg.film_id = ? ORDER BY g.genre_id";

        return jdbcTemplate.query(sqlQuery, this::mapRowToGenre);
    }


    private Genre mapRowToGenre(ResultSet resultSet, int rowNumber) throws SQLException {
        Genre genre = new Genre();
        genre.setId(resultSet.getLong("genre_id"));
        genre.setName(resultSet.getString("name"));

        return genre;
    }

    private boolean isExist(Long id) {
        String sqlQuery = "SELECT genre_id FROM genres WHERE genre_id = ?";
        return jdbcTemplate.queryForRowSet(sqlQuery, id).next();
    }
}
