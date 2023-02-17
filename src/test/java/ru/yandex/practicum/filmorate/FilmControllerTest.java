package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controllers.FilmController;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

public class FilmControllerTest {

    FilmController filmController;

    @BeforeEach
    public void beforeEach() {
        filmController = new FilmController();
    }

    @Test
    void shouldAddFilm() throws ValidationException {
        Film testFilm = new Film("Великий Гэтсби", "Описание",
                LocalDate.of(2013, 5, 10), 120L);
        filmController.addFilm(testFilm);
        Assertions.assertEquals(1, filmController.getAllFilms().size(), "Ошибка при добавлении фильма");
    }

    @Test
    void shouldUpdateFilm() throws ValidationException {
        Film lastFilm = new Film("Зеленая миля", "Драма",
                LocalDate.of(1999, 12, 9), 200L);
        filmController.addFilm(lastFilm);

        Film newFilm = filmController.updateFilm(lastFilm);
        Assertions.assertEquals(lastFilm, newFilm, "Ошибка при обновлении");
    }

    @Test
    void checkValidFilmName() {
        Film testFilm = new Film("", "Описание",
                LocalDate.of(2013, 5, 10), 120L);
        Assertions.assertFalse(filmController.checkValid(testFilm));
    }

    @Test
    void checkValidFilmDescription200() {
        Film testFilm = new Film("Аватар", "Описание",
                LocalDate.of(2023, 12, 9), 200L);
        Assertions.assertTrue(filmController.checkValid(testFilm));
    }

    @Test
    void checkValidFilmDescription201() {
        Film testFilm = new Film("Аватар", "Счастлив писатель, который мимо характеров скучных, " +
                "против¬ных, поражающих печальною своею действительностью, " +
                "приближа¬ется к характерам, являющим высокое достоинство человека, " +
                " из великого омута ежедневно вращающихся образов избрал одни не¬многие " +
                "исключения, который не изменял ни разу возвышенного строя своей лиры, который не " +
                "ниспускался с вершины своей к бедным, ни¬чтожным своим собратьям и который, не касаясь земли," +
                " весь повер¬гался в свои далеко отодвинутые от нее и возвеличенные образы. Вдвойне завиден прекрасный" +
                " удел: он среди них, как в родной семье; а между тем далеко и громко разносится его слава; он чуд¬но" +
                " польстил людям, скрыв печальное в жизни, показав им пре¬красного человека. Великим" +
                " всемирным поэтом именуют его, кото¬рый парит высоко над всеми другими гениями, как парит" +
                " орел над другими высоко летающими. Нет равного ему в силе — он бог! Но не таков удел и другая" +
                " судьба писателя, который дерзнул вы¬звать наружу все, что ежеминутно пред очами и чего не зрят" +
                " равно¬душные очи, — всю страшную, потрясающую тину мелочей, которые опутали нашу жизнь," +
                " всю глубину холодных, повседневных характе¬ров, которыми кишит наша земная, подчас горькая и" +
                " скучная дорога, и который крепкою силою неумолимого резца дерзнул выставить их выпукло и ярко" +
                " на всенародные очи! Ему не собрать народных руко¬плесканий, ему не зреть признательных слез" +
                " и единодушного востор¬га взволнованных им душ.",
                LocalDate.of(2023, 12, 9), 200L);
        Assertions.assertFalse(filmController.checkValid(testFilm));
    }

    @Test
    void checkValidFilmReleaseDate() {
        Film testFilm = new Film("Кинг-Конг", "Описание",
                LocalDate.of(1700, 12, 9), 200L);
        Assertions.assertFalse(filmController.checkValid(testFilm));
    }

    @Test
    void checkValidFilmDuration() {
        Film testFilm = new Film("Форсаж", "Описание",
                LocalDate.of(1700, 12, 9), -1L);
        Assertions.assertFalse(filmController.checkValid(testFilm));
    }


}
