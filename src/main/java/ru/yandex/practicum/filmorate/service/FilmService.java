package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    public Film addFilm(Film film) {
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
        filmStorage.getFilm(filmID).addLikes(userID);
    }

    public void removeLike(Long filmID, Long userID) {
        filmStorage.getFilm(filmID).removeLike(userID);
    }

    public List<Film> getPopularFilms(long count) {
        return filmStorage.getAllFilms().stream().sorted((o1, o2) -> o2.getLikes().size() - o1.getLikes().size())
                .limit(count).collect(Collectors.toList());
    }


}
