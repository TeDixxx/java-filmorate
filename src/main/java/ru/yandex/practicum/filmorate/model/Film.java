package ru.yandex.practicum.filmorate.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;;


@Data
@AllArgsConstructor
public class Film {
    private long id;
    private String name;
    private String description;
    private LocalDate releaseDate;
    private long duration;

    @Builder.Default
    private Set<Long> likes = new HashSet<>();

    public void addLikes(long id) {
        likes.add(id);
    }

    public void removeLike(long id) {
        likes.remove(id);
    }
}
