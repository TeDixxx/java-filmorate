CREATE TABLE IF NOT EXISTS users
(
    user_id  int PRIMARY KEY AUTO_INCREMENT,
    login    varchar,
    email    varchar,
    name     varchar,
    birthday date
);

CREATE TABLE IF NOT EXISTS mpa
(
    mpa_id int PRIMARY KEY AUTO_INCREMENT,
    name varchar UNIQUE
);

CREATE TABLE IF NOT EXISTS films
(
    film_id      int PRIMARY KEY AUTO_INCREMENT,
    name         varchar,
    description varchar,
    release_date date,
    duration     int,
    mpa_id         int,
    FOREIGN KEY (mpa_id) REFERENCES mpa (mpa_id)
    );

CREATE TABLE IF NOT EXISTS genres
(
    genre_id int PRIMARY KEY AUTO_INCREMENT,
    name     varchar UNIQUE
);

CREATE TABLE IF NOT EXISTS film_genres
(
    film_id  int,
    genre_id int,
    FOREIGN KEY (film_id) REFERENCES films (film_id),
    FOREIGN KEY (genre_id) REFERENCES genres (genre_id),
    UNIQUE (film_id, genre_id)
    );

CREATE TABLE IF NOT EXISTS user_friends
(
    user_id      int,
    friend_id    int,
    FOREIGN KEY (user_id) REFERENCES users (user_id),
    FOREIGN KEY (friend_id) REFERENCES users (user_id),
    UNIQUE (user_id, friend_id)
    );

CREATE TABLE IF NOT EXISTS film_likes
(
    film_id int,
    user_id int,
    FOREIGN KEY (film_id) REFERENCES films (film_id),
    FOREIGN KEY (user_id) REFERENCES users (user_id),
    UNIQUE (film_id, user_id)
    );