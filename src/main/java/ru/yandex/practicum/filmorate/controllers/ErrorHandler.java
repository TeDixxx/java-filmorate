package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;

import java.util.Map;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {

//    @ExceptionHandler
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    public Map<String, String> handleNoSuchFilmException(final NotFoundException e) {
//        log.error(e.getMessage());
//        return Map.of(
//                "error", "Фильм не найден",
//                "errorMessage", e.getMessage()
//        );
//    }
//
//    @ExceptionHandler
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    public Map<String, String> handleNoSuchUserException(final NotFoundException e) {
//        log.error(e.getMessage());
//        return Map.of(
//                "error", "Пользователь не найден",
//                "errorMessage", e.getMessage()
//        );
//    }
//
//    @ExceptionHandler
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public Map<String, String> handleValidationException(final ValidationException e) {
//        log.error(e.getMessage());
//        return Map.of(
//                "error", "Ошибка",
//                "errorMessage", e.getMessage()
//        );
//    }
//
//    @ExceptionHandler
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    public Map<String, String> handleException(final Exception e) {
//        log.error(e.getMessage());
//        return Map.of(
//                "error", "Ошибка",
//                "errorMessage", e.getMessage()
//        );
//    }


}
