package ru.yandex.practicum.filmorate;


import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.dao.FilmDbStorage;
import ru.yandex.practicum.filmorate.storage.dao.LikeDbStorage;
import ru.yandex.practicum.filmorate.storage.dao.UserDbStorage;

import java.time.LocalDate;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class LikeDbStorageTest {
    private final FilmDbStorage filmDbStorage;
    private final UserDbStorage userDbStorage;
    private final LikeDbStorage likeDbStorage;

    @Test
    void addLike() {
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

        User firstU = new User();
        firstU.setEmail("prakticum@email.ru");
        firstU.setLogin("staff");
        firstU.setName("Oleg");
        firstU.setBirthday(LocalDate.of(1996, 10, 14));
        firstU.setId(2);
        userDbStorage.createUser(firstU);

        likeDbStorage.addLike(firstU.getId(), filmForUp.getId());

        Assertions.assertEquals(filmForUp.getId(), filmDbStorage.getPopular(5).get(0).getId());
    }

    @Test
    void delete() {
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

        User firstU = new User();
        firstU.setEmail("javaDEV@email.ru");
        firstU.setLogin("mtm");
        firstU.setName("Slayer");
        firstU.setBirthday(LocalDate.of(1999, 10, 14));
        firstU.setId(2);
        userDbStorage.createUser(firstU);

        Long id = filmDbStorage.getPopular(5).get(0).getId();

        likeDbStorage.addLike(firstU.getId(), filmForUp.getId());

        Assertions.assertEquals(filmForUp.getId(), filmDbStorage.getPopular(5).get(0).getId());

        likeDbStorage.deleteLike(firstU.getId(), filmForUp.getId());

        Assertions.assertEquals(id, filmDbStorage.getPopular(5).get(0).getId());

    }
}
