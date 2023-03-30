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
        String sqlQuery = "SELECT* FROM genre WHERE genre_id = ?";

        return jdbcTemplate.queryForObject(sqlQuery, this::mapRowToGenre, id);
    }

    @Override
    public List<Genre> getAll() {
        String sqlQuery = "SELECT* FROM genre";

        return jdbcTemplate.query(sqlQuery, this::mapRowToGenre);
    }

    @Override
    public List<Genre> getFilmGenre(Long filmId) {
        String sqlQuery = "SELECT*FROM genre AS gen LEFT OUTER JOIN film_genre AS fg ON gen.genre_id = fg.genre_id WHERE fg.film_id = ? ORDER BY gen.genre_id";

        return jdbcTemplate.query(sqlQuery, this::mapRowToGenre);
    }


    private Genre mapRowToGenre(ResultSet resultSet, int rowNumber) throws SQLException {
        Genre genre = new Genre();
        genre.setId(resultSet.getLong("genre_id"));
        genre.setName(resultSet.getString("name"));

        return genre;
    }
}
