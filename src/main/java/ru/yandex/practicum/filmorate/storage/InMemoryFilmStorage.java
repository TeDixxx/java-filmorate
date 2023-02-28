package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;


@Component
@Slf4j
public class InMemoryFilmStorage implements FilmStorage {

    private final HashMap<Long, Film> films = new HashMap<>();
    private final LocalDate releaseDate = LocalDate.of(1895, 12, 28);
    private long currentID = 1;

    @Override
    public Film addFilm(Film film) throws ValidationException {
        if (checkValid(film)) {
            log.info("Добавление фильма");
            film.setId(currentID++);
            film.setLikes(new HashSet<>());
            films.put(film.getId(), film);

        } else {
            log.debug("Ошибка при добавлении фильма{}", film);
            throw new ValidationException("Ошибка при добавлении фильма");
        }
        return film;
    }

    @Override
    public Film updateFilm(Film film) {
        if (checkValid(film) && films.containsKey(film.getId())) {
            log.info("Обновление фильма");
            films.put(film.getId(), film);
        } else {
            log.debug("Ошибка обновления фильма");
        }
        return film;
    }

    @Override
    public List<Film> getAllFilms() {
        return new ArrayList<>(films.values());
    }

    @Override
    public Film getFilmByID(long id) {
        return films.get(id);
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