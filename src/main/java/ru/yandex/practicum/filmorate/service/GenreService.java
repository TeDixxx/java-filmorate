package ru.yandex.practicum.filmorate.service;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.interfaces.GenreStorage;

import java.util.List;

@Service
public class GenreService {
    private final GenreStorage genreStorage;

    public GenreService(GenreStorage genreStorage) {
        this.genreStorage = genreStorage;
    }

    public Genre getById(Long id) throws EmptyResultDataAccessException {
        try {
            return genreStorage.getById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException("Не найдено");
        }
    }

    public List<Genre> getAll() {
        return genreStorage.getAll();
    }

    public void isExist(Long id) {
        if (!genreStorage.isExist(id)) {
            throw new NotFoundException("Не найдено");
        }

    }
}
