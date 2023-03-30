package ru.yandex.practicum.filmorate.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.interfaces.FilmStorage;
import ru.yandex.practicum.filmorate.storage.interfaces.LikeStorage;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilmService {

    private final FilmStorage filmStorage;
    private final LikeStorage likeStorage;

    @Autowired
    public FilmService(
            @Qualifier("filmDbStorage") FilmStorage filmStorage,
                       LikeStorage likeStorage) {
        this.filmStorage = filmStorage;
        this.likeStorage = likeStorage;
    }

    public Film addFilm(Film film) throws ValidationException {
        if (!checkValid(film)) {
            throw new ValidationException("Ошибка добавления фильма");
        }
        return filmStorage.addFilm(film);
    }

    public Film updateFilm(Film film) {
        return filmStorage.updateFilm(film);
    }

    public Film getFilm(Long filmID) {
        return filmStorage.getFilm(filmID);
    }

    public List<Film> getAllFilms() {
        return filmStorage.getAllFilms();
    }

    public void addLike(Long filmID, Long userID) {
        likeStorage.deleteLike(userID,filmID);
    }

    public void removeLike(Long filmID, Long userID) {
        likeStorage.deleteLike(userID,filmID);
    }

    public List<Film> getPopularFilms(long count) {
        return filmStorage.getAllFilms().stream().sorted((o1, o2) -> o2.getLikes().size() - o1.getLikes().size())
                .limit(count).collect(Collectors.toList());
    }


    public boolean checkValid(Film film) {
         final LocalDate releaseDate = LocalDate.of(1895, 12, 28);
        if (film.getName().isEmpty()
                || film.getDescription().length() > 200
                || film.getReleaseDate().isBefore(releaseDate)
                || film.getDuration() < 0) {
            return false;
        }
        return true;
    }


}