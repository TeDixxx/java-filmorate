package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.interfaces.MpaStorage;

import java.util.List;

@Service
public class MpaService {

    private final MpaStorage mpaStorage;

    public MpaService(MpaStorage mpaStorage) {
        this.mpaStorage = mpaStorage;
    }

    public Mpa getById(Long id) {
        if (mpaStorage.getById(id) == null) {
            throw new NotFoundException("Not found");
        }
        return mpaStorage.getById(id);
    }

    public List<Mpa> getAll() {
        return mpaStorage.getAll();
    }

    public Mpa getFilmMpa(Long filmId) {
        return mpaStorage.getFilmMpa(filmId);
    }

}
