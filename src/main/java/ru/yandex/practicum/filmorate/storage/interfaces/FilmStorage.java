package ru.yandex.practicum.filmorate.storage.interfaces;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmStorage {

    Film addFilm(Film film);

    Film updateFilm(Film film);

    Film getFilm(Long filmId);

    List<Film> getAllFilms();

    List<Film> getPopular(int count);

    boolean isExists(Long id);

}
