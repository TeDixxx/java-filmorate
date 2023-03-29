package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.interfaces.FilmStorage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;


@Component
@Slf4j
public class InMemoryFilmStorage implements FilmStorage {

    private final HashMap<Long, Film> films = new HashMap<>();
    private final LocalDate releaseDate = LocalDate.of(1895, 12, 28);
    private long currentID = 1;


    @Override
    public Film addFilm(Film film) {
        if (checkValid(film)) {
            film.setId(currentID++);
            films.put(film.getId(), film);
            log.debug("Был добавлен фильм{}", film);
        }
        return film;
    }

    @Override
    public Film updateFilm(Film film) {
        if (films.containsKey(film.getId()) && checkValid(film)) {
            films.put(film.getId(), film);
            log.debug("Фильм был обновлен{}", film);
        } else {
            throw new NotFoundException("Ошибка обновления фильма");
        }
        return film;
    }

    @Override
    public Film getFilm(Long filmID) throws NotFoundException {
        Film film;
        if (films.containsKey(filmID)) {
            film = films.get(filmID);
        } else {
            throw new NotFoundException("Фильм с данным айди не найден");
        }
        return film;
    }

    @Override
    public List<Film> getAllFilms() {
        return new ArrayList<>(films.values());
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