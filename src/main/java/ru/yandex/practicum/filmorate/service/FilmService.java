package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilmService {

    private final FilmStorage filmStorage;

    @Autowired
    public FilmService(FilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    public Film addFilm(Film film) throws ValidationException {
        return filmStorage.addFilm(film);
    }

    public Film updateFilm(Film film) {
        return filmStorage.updateFilm(film);
    }

    public List<Film> getAllFilms() {
        return filmStorage.getAllFilms();
    }

    public Film addLike(long filmId, long id) {
        Film value = filmStorage.getFilmByID(filmId);
        value.addLikes(id);
        return value;
    }

    public Film removeLike(long filmId, long id) {
        Film value = filmStorage.getFilmByID(filmId);
        if (value.getLikes().contains(id)) {
            value.removeLike(id);
        }
        return value;
    }

    public List<Film> getPopularFilms(int count) {
        return filmStorage.getAllFilms().stream().sorted((a, b) -> b.getLikes().size() - a.getLikes().size())
                .limit(count).collect(Collectors.toList());
    }
}
