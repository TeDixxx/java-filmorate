package ru.yandex.practicum.filmorate.storage.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.interfaces.FilmStorage;
import ru.yandex.practicum.filmorate.storage.interfaces.GenreStorage;
import ru.yandex.practicum.filmorate.storage.interfaces.MpaStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor

public class FilmDbStorage implements FilmStorage {
    private final JdbcTemplate jdbcTemplate;
    private final MpaStorage mpaStorage;
    private final GenreStorage genreStorage;

    @Override
    public Film addFilm(Film film) {
        Map<String,Object> sqlValues = new HashMap<>();

        sqlValues.put("name",film.getName());
        sqlValues.put("description",film.getDescription());
        sqlValues.put("release_date",film.getReleaseDate());
        sqlValues.put("duration",film.getDuration());
        sqlValues.put("mpa_id",film.getMpaId());

        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("films")
                .usingGeneratedKeyColumns("film_id");
        film.setId(simpleJdbcInsert.executeAndReturnKey(sqlValues).longValue());

        return film;
    }

    @Override
    public Film updateFilm(Film film) {
        String sqlQuery = "UPDATE films SET name = ?, description = ?,release_date = ?, duration = ?, mpa_id = ? WHERE film_id = ?";

        jdbcTemplate.update(sqlQuery,
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getMpaId().getId(),
                film.getId());

        return film;
    }

    @Override
    public Film getFilm(Long filmId) {
        String sqlQuery = "SELECT* FROM films WHERE film_id = ?";

        return jdbcTemplate.queryForObject(sqlQuery,this::mapRowToFilm,filmId);
    }

    @Override
    public List<Film> getAllFilms() {
        String sqlQuery = "SELECT* FROM films";

        return jdbcTemplate.query(sqlQuery,this::mapRowToFilm);
    }

    private Film mapRowToFilm(ResultSet resultSet,int rowNumber) throws SQLException {
        Film film = new Film();
        film.setId(resultSet.getInt("film_id"));
        film.setName(resultSet.getString("name"));
        film.setDescription(resultSet.getString("description"));
        film.setReleaseDate(resultSet.getDate("release_date").toLocalDate());
        film.setMpaId(mpaStorage.getFilmMpa(resultSet.getLong("mpa_id")));
        film.setGenres(genreStorage.getFilmGenre(film.getId()));

        return film;
    }
}
