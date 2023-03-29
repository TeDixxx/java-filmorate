package ru.yandex.practicum.filmorate.storage.interfaces;

public interface LikeStorage {

    void addLike(Long userId, Long filmId);

    void deleteLike(Long userId, Long filmId);
}
