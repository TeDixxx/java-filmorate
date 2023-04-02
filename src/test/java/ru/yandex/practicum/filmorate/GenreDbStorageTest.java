package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.GenreService;
import ru.yandex.practicum.filmorate.storage.dao.GenreDbStorage;

import java.util.List;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class GenreDbStorageTest {
    private final GenreDbStorage genreDbStorage;
    private final GenreService genreService;

    @Test
    void getById() {
        Genre filmGenre = genreService.getById(1L);
        Assertions.assertEquals("Комедия", filmGenre.getName());
    }

    @Test
    void getAll() {
        List<Genre> genreList = genreDbStorage.getAll();
        Assertions.assertEquals(6, genreList.size());
    }
}
