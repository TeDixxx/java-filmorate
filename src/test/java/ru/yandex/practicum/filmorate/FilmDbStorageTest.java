package ru.yandex.practicum.filmorate;


import lombok.RequiredArgsConstructor;

import static org.assertj.core.api.Assertions.assertThat;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.dao.FilmDbStorage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class FilmDbStorageTest {
    private final FilmDbStorage filmDbStorage;
    private final FilmService filmService;

    @Test
    void addFilm() {
        Film film = new Film();
        film.setName("testFilm");
        film.setDescription("Film for tests");
        film.setReleaseDate(LocalDate.of(2000, 10, 14));
        film.setDuration(150);

        Mpa filmMpa = new Mpa();
        filmMpa.setId(1L);
        film.setMpa(filmMpa);

        filmDbStorage.addFilm(film);

    }

    @Test
    void getFilm() {
        Film film = new Film();
        Mpa filmMpa = new Mpa();
        film.setName("xxx");
        filmMpa.setId(1L);
        film.setMpa(filmMpa);

        film.setReleaseDate(LocalDate.of(2000, 12, 21));

        filmDbStorage.addFilm(film);

        Optional<Film> filmOptional = Optional.ofNullable(filmService.getFilm(1L));

        assertThat(filmOptional)
                .isPresent()
                .hasValueSatisfying(film1 -> assertThat(film).hasFieldOrPropertyWithValue("id", film.getId()));
    }

    @Test
    void update() {
        Film film = new Film();
        Mpa mpa = new Mpa();
        film.setName("filmName");
        mpa.setId(3L);
        film.setMpa(mpa);
        film.setReleaseDate(LocalDate.of(2012, 12, 12));

        filmDbStorage.addFilm(film);

        Film filmForUp = new Film();
        filmForUp.setId(film.getId());
        Mpa mpa2 = new Mpa();
        filmForUp.setName("forUp");
        mpa2.setId(3L);
        filmForUp.setMpa(mpa2);
        filmForUp.setReleaseDate(LocalDate.of(2012, 12, 12));

        filmDbStorage.updateFilm(filmForUp);

        assertThat(filmForUp).hasFieldOrPropertyWithValue("name", "forUp");
    }

    @Test
    void getAllFilms() {
        Film film1 = new Film();
        Mpa mpa = new Mpa();
        film1.setName("test");
        mpa.setId(5L);
        film1.setMpa(mpa);
        film1.setReleaseDate(LocalDate.of(2001, 6, 17));

        filmDbStorage.addFilm(film1);

        List<Film> testList = new ArrayList<>(filmDbStorage.getAllFilms());

        Assertions.assertEquals(filmDbStorage.getAllFilms(), testList);
    }
}
