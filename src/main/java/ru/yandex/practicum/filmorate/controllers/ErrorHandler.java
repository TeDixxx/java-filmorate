package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.filmorate.exception.*;
import ru.yandex.practicum.filmorate.model.ErrorResponse;

import java.util.Map;

@Slf4j
@RestControllerAdvice(assignableTypes = {UserController.class, FilmController.class})
public class ErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleNotFoundException(final NotFoundException e) {
        log.warn("Ошибка! NotFoundException {}", e.getMessage());
        return Map.of("NotFound", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleIncorrectParameterException(final IncorrectParameterException e) {
        log.warn("Ошибка! IncorrectParameterException {}", e.getMessage());
        return Map.of("IncorrectParameterException", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, String> handleNullPointerException(final NullPointerException e) {
        log.warn("Ошибка! NullPointerException {}", e.getMessage());
        return Map.of("NullPointerException", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidationException(final ValidationException e) {
        log.warn("Ошибка! NullPointerException {}", e.getMessage());
        return Map.of("NullPointerException", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleMpaNotFoundException(final MpaNotFoundException e) {
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleGenreNotFoundException(final GenreNotFoundException e) {
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, String> handleThrowable(final Throwable e) {
        log.warn("Ошибка!{}", e.getMessage());
        return Map.of("Error", e.getMessage());
    }

}
