package ru.yandex.practicum.filmorate.storage.interfaces;


import ru.yandex.practicum.filmorate.model.Genre;
import java.util.List;
import java.util.Optional;

public interface GenreStorage {

    Optional<Genre> getById(Long id);

    List<Genre> getAll();

    List<Genre> getFilmGenre(Long filmId);

    boolean isExist(Long id);
}
