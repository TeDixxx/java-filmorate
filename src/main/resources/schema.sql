SET MODE MySQL;

CREATE TABLE IF NOT EXISTS genres
(
    genre_id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS rating
(
    rating_id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS films
(
    film_id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    release_date DATE,
    duration INTEGER,
    rating_id INTEGER
);

CREATE TABLE IF NOT EXISTS users
(
    user_id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    login VARCHAR(255) NOT NULL UNIQUE,
    name VARCHAR(255),
    birthday DATE NOT NULL
);

CREATE TABLE IF NOT EXISTS film_likes
(
    film_id INTEGER NOT NULL REFERENCES films (film_id),
    user_id INTEGER NOT NULL REFERENCES users (user_id),
    PRIMARY KEY (film_id, user_id)
);

CREATE TABLE IF NOT EXISTS user_friends
(
    user_id INTEGER NOT NULL REFERENCES users (user_id),
    friend_id INTEGER NOT NULL REFERENCES users (user_id),
    PRIMARY KEY (user_id, friend_id)
);

CREATE TABLE IF NOT EXISTS film_genres
(
    film_id INTEGER NOT NULL REFERENCES films (film_id),
    genre_id INTEGER NOT NULL REFERENCES genres (genre_id),
    PRIMARY KEY (film_id, genre_id)
);