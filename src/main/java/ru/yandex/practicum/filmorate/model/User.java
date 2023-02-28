package ru.yandex.practicum.filmorate.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;


@Data
@RequiredArgsConstructor
public class User {
    @NonNull
    private long id;
    @NonNull
    private String email;
    @NonNull
    private String login;
    @NonNull
    private String name;
    @NonNull
    private LocalDate birthday;

    private Set<Long> friends = new HashSet<>();

    public void addFriends(long id) {
        friends.add(id);
    }

    public void removeFriend(long id) {
        friends.remove(id);
    }


}
