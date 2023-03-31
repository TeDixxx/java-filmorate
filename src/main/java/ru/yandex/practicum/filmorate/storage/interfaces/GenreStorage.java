package ru.yandex.practicum.filmorate.storage.interfaces;

import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Genre;
import java.util.List;

public interface GenreStorage {

    Genre getById(Long id) throws ValidationException;

    List<Genre> getAll();

    List<Genre> getFilmGenre(Long filmId);

    boolean isExist(Long id);
}
