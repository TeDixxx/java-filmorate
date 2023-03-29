package ru.yandex.practicum.filmorate.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import lombok.*;


@Data
public class User {
    private long id;
    private String email;
    private String login;
    private String name;
    private LocalDate birthday;

    public User() {}

    public User(long id, String email, String login, String name, LocalDate birthday) {
        this.id = id;
        this.email = email;
        this.login = login;
        this.name = name;
        this.birthday = birthday;
    }

    private Set<Long> friends = new HashSet<>();


    public void addFriends(long id) {
        friends.add(id);
    }

    public void removeFriend(long id) {
        friends.remove(id);
    }


}
