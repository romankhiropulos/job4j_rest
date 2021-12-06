DROP TABLE IF EXISTS person;
DROP TABLE IF EXISTS employee;

CREATE TABLE person
(
    id       SERIAL PRIMARY KEY NOT NULL,
    login    VARCHAR(2000),
    password VARCHAR(2000),
    employee_id INT
);

CREATE TABLE employee
(
    id          SERIAL PRIMARY KEY NOT NULL,
    name        VARCHAR(2000)      NOT NULL,
    surname     VARCHAR(2000)      NOT NULL,
    inn         VARCHAR(15)        NOT NULL,
    date_hiring TIMESTAMP          NOT NULL
);