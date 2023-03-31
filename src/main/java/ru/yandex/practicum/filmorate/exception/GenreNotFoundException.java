package ru.yandex.practicum.filmorate.exception;

public class GenreNotFoundException extends IllegalArgumentException {
    public GenreNotFoundException(String message) {
        super(message);
    }
}
