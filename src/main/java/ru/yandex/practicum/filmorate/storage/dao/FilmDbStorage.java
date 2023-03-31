package ru.yandex.practicum.filmorate.storage.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.interfaces.FilmStorage;
import ru.yandex.practicum.filmorate.storage.interfaces.GenreStorage;
import ru.yandex.practicum.filmorate.storage.interfaces.MpaStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor

public class FilmDbStorage implements FilmStorage {
    private final JdbcTemplate jdbcTemplate;
    private final MpaStorage mpaStorage;
    private final GenreStorage genreStorage;


    @Override
    public void addFilm(Film film) {

        Map<String, Object> sqlValues = new HashMap<>();

        sqlValues.put("name", film.getName());
        sqlValues.put("description", film.getDescription());
        sqlValues.put("release_date", film.getReleaseDate());
        sqlValues.put("duration", film.getDuration());
        sqlValues.put("mpa_id", film.getMpaId().getId());

        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("films")
                .usingGeneratedKeyColumns("film_id");
        film.setId(simpleJdbcInsert.executeAndReturnKey(sqlValues).longValue());

        addGenre(film.getId(), film.getGenres());
    }

    @Override
    public void updateFilm(Film film) {
        String sqlQuery = "SELECT FROM films WHERE film_id = ?";

        jdbcTemplate.update(sqlQuery,
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getMpaId().getId(),
                film.getId());

        addGenre(film.getId(), film.getGenres());

    }

    @Override
    public Film getFilm(Long filmId) {
        String sqlQuery = "SELECT* FROM films WHERE film_id = ?";

        return jdbcTemplate.queryForObject(sqlQuery, this::mapRowToFilm, filmId);
    }

    @Override
    public List<Film> getAllFilms() {
        String sqlQuery = "SELECT* FROM films";

        return jdbcTemplate.query(sqlQuery, this::mapRowToFilm);
    }

    @Override
    public boolean isExists(Long id) {
        String sqlQuery = "SELECT film_id FROM films WHERE film_id = ?";
        return jdbcTemplate.queryForRowSet(sqlQuery, id).next();
    }


    private Film mapRowToFilm(ResultSet resultSet, int rowNumber) throws SQLException {
        Film film = new Film();
        film.setId(resultSet.getInt("film_id"));
        film.setName(resultSet.getString("name"));
        film.setDescription(resultSet.getString("description"));
        film.setReleaseDate(resultSet.getDate("release_date").toLocalDate());
        film.setDuration(resultSet.getLong("duration"));
        film.setMpaId(mpaStorage.getFilmMpa(resultSet.getLong("mpa_id")));
        film.setGenres(genreStorage.getFilmGenre(film.getId()));

        return film;
    }

    private void addGenre(Long filmId, List<Genre> genres) {
        deleteGenre(filmId);
        if (genres != null) {
            if (!genres.isEmpty()) {
                StringBuilder sqlQuery = new StringBuilder("INSERT INTO film_genres (film_id, genre_id) VALUES ");
                for (Genre filmGenre : new HashSet<>(genres)) {
                    sqlQuery.append("(").append(filmId).append(", ").append(filmGenre.getId()).append("), ");
                }
                sqlQuery.delete(sqlQuery.length() - 2, sqlQuery.length());
                jdbcTemplate.update(sqlQuery.toString());
            }
        }
    }

    private void deleteGenre(Long filmId) {
        String sqlQuery = "DELETE FROM film_genres WHERE fim_id = ?";
        jdbcTemplate.update(sqlQuery, filmId);
    }


}
