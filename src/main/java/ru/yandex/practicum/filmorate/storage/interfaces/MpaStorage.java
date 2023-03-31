package ru.yandex.practicum.filmorate.storage.interfaces;

import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.List;

public interface MpaStorage {

    Mpa getById(Long id) throws ValidationException;

    List<Mpa> getAll();

    Mpa getFilmMpa(Long filmId);

    boolean isExist(Long id);
}
