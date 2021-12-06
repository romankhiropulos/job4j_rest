DROP TABLE IF EXISTS employee;
DROP TABLE IF EXISTS person;

CREATE TABLE person
(
    id       SERIAL PRIMARY KEY NOT NULL,
    login    VARCHAR(2000),
    password VARCHAR(2000),
    employee_id INT
);

INSERT INTO person (id, login, password, employee_id)
VALUES (1, 'parsentev', '123', 1),
       (2, 'login', 'password', 2),
       (3, 'ivan', '123', 2);

CREATE TABLE employee
(
    id          SERIAL PRIMARY KEY NOT NULL,
    name        VARCHAR(2000)      NOT NULL,
    surname     VARCHAR(2000)      NOT NULL,
    inn         VARCHAR(15)        NOT NULL,
    date_hiring TIMESTAMP          NOT NULL
);

INSERT INTO employee (id, name, surname, inn, date_hiring)
VALUES (1, 'Ivan', 'Ivanov', '46574857457', NOW());
INSERT INTO employee (id, name, surname, inn, date_hiring)
VALUES (2, 'Sergey', 'Petrov', '24574425747', NOW());