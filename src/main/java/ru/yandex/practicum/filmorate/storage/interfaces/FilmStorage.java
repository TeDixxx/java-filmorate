package ru.yandex.practicum.filmorate.storage.interfaces;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmStorage {

   void addFilm(Film film);

    void updateFilm(Film film);

    Film getFilm(Long filmId);

    List<Film> getAllFilms();

    boolean isExists(Long id);



}
