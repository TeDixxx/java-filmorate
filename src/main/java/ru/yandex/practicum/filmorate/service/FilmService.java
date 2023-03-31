package ru.yandex.practicum.filmorate.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.interfaces.FilmStorage;
import ru.yandex.practicum.filmorate.storage.interfaces.LikeStorage;


import java.time.LocalDate;
import java.util.List;



@Service
public class FilmService {

    private final FilmStorage filmStorage;
    private final LikeStorage likeStorage;
    private final UserService userService;

    @Autowired
    public FilmService(
            @Qualifier("filmDbStorage") FilmStorage filmStorage,
            LikeStorage likeStorage, UserService userService) {
        this.filmStorage = filmStorage;
        this.likeStorage = likeStorage;
        this.userService = userService;

    }

    public Film addFilm(Film film) throws ValidationException {
        if (!checkValid(film)) {
            throw new ValidationException("Ошибка добавления фильма");
        }
        filmStorage.addFilm(film);
        return getFilm(film.getId());
    }

    public Film updateFilm(Film film) throws ValidationException {
        if (!checkValid(film)) {
            throw new ValidationException("Ошибка обновления");
        }
        isExists(film.getId());
        filmStorage.updateFilm(film);
        return filmStorage.getFilm(film.getId());
    }

    public Film getFilm(Long filmID) {
        isExists(filmID);
        return filmStorage.getFilm(filmID);
    }

    public List<Film> getAllFilms() {
        return filmStorage.getAllFilms();
    }

    public void addLike(Long filmID, Long userID) {
        isExists(filmID);
        userService.isExists(userID);
        likeStorage.deleteLike(userID, filmID);
    }

    public void removeLike(Long filmID, Long userID) {
        isExists(filmID);
        userService.isExists(userID);
        likeStorage.deleteLike(userID, filmID);
    }

    public List<Film> getPopularFilms(int count) {
        if (filmStorage.getPopular(count).isEmpty()) {
            return filmStorage.getAllFilms();
        }
        return filmStorage.getPopular(count);
    }

    private void isExists(Long id) {
        if (!filmStorage.isExists(id)) {
            throw new NotFoundException("Не найдено!");
        }
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


