package ru.yandex.practicum.filmorate.service;


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

    public Genre getById(Long id) {
        if (genreStorage.getById(id) == null) {
            throw new NotFoundException("Not found");
        }
        return genreStorage.getById(id);
    }

    public List<Genre> getAll() {
        return genreStorage.getAll();
    }

}
