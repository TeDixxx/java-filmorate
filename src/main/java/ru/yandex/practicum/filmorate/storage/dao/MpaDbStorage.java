package ru.yandex.practicum.filmorate.storage.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.interfaces.MpaStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class MpaDbStorage implements MpaStorage {

    private final JdbcTemplate jdbcTemplate;

    public MpaDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Mpa getById(Long id) {
        String sqlQuery = "SELECT* FROM mpa WHERE mpa_id = ?";
        return jdbcTemplate.queryForObject(sqlQuery, this::mapRowToRating, id);
    }

    @Override
    public List<Mpa> getAll() {
            String sqlQuery = "SELECT* FROM mpa ORDER BY mpa_id";
            return jdbcTemplate.query(sqlQuery, this::mapRowToRating);
    }

    @Override
    public Mpa getFilmMpa(Long mpaId) {
        isExist(mpaId);
        String sqlQuery = "SELECT* FROM mpa WHERE mpa_id = ?";

        return jdbcTemplate.queryForObject(sqlQuery, this::mapRowToRating, mpaId);
    }

    private Mpa mapRowToRating(ResultSet resultSet, int rowNumber) throws SQLException {
        Mpa mpa = new Mpa();
        mpa.setId(resultSet.getLong("mpa_id"));
        mpa.setName(resultSet.getString("name"));

        return mpa;
    }

    @Override
    public boolean isExist(Long id) {
        String sqlQuery = "SELECT mpa_id FROM mpa WHERE mpa_id = ?";

        return jdbcTemplate.queryForRowSet(sqlQuery, id).next();
    }
}
