package ru.yandex.practicum.filmorate.exception;

public class MpaNotFoundException extends IllegalArgumentException{
    public MpaNotFoundException(String message) {
        super(message);
    }
}
