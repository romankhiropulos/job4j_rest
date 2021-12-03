DROP TABLE IF EXISTS person;

CREATE TABLE person
(
    id       SERIAL PRIMARY KEY NOT NULL,
    login    VARCHAR(2000),
    password VARCHAR(2000)
);

INSERT INTO person (id, login, password)
VALUES (1, 'parsentev', '123'),
       (2, 'login', 'password'),
       (3, 'ivan', '123');