package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/films")
public class FilmController {
    private HashMap<Integer, Film> filmMap = new HashMap<>();
    private int filmId = 1;
    private final LocalDate releaseDate = LocalDate.of(1895, 12, 28);

    @PostMapping
    public Film addFilm(@RequestBody Film film) throws ValidationException {
        if (checkValid(film)) {
            log.info("Получен запрос на добавление фильма");
            film.setId(filmId++);
            filmMap.put(film.getId(), film);
       } else {
            log.debug("Ошибка при добавлении фильма{}", film);
           throw new ValidationException("Ошибка при добавлении фильма");
        }

        return film;
    }

    @PutMapping
    public Film updateFilm(@RequestBody Film film) throws ValidationException {
        if (checkValid(film) && filmMap.containsKey(film.getId())) {
            log.info("Получен запрос на обновление фильма");
            filmMap.put(film.getId(), film);
        } else {
            log.debug("Ошибка при обновлении фильма{}", film);
           throw new ValidationException("Ошибка при обновлении фильма");
        }
        return film;
    }

    @GetMapping
    public List<Film> getAllFilms() {
        log.info("Получен запрос на просмотр списка всех фильмов");
        return new ArrayList<>(filmMap.values());
    }

    public boolean checkValid(Film film) {
        if (film.getName().isEmpty()
                || film.getDescription().length() > 200
                || film.getReleaseDate().isBefore(releaseDate)
                || film.getDuration() < 0) {
            return false;
        }
        return true;
    }

}
