package ru.job4j.auth.domain;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.*;

@Entity
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String surname;

    private String inn;

    @Column(name = "date_hiring")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateHiring;

    @Transient
    private List<Person> accounts;

    public static Employee of(int id, String name, String surname, String inn) {
        Employee e = new Employee();
        e.id = id;
        e.name = name;
        e.surname = surname;
        e.inn = inn;
        e.dateHiring = new Timestamp(System.currentTimeMillis());
        return e;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurName() {
        return surname;
    }

    public void setSurName(String surname) {
        this.surname = surname;
    }

    public String getInn() {
        return inn;
    }

    public void setInn(String inn) {
        this.inn = inn;
    }

    public Date getDateHiring() {
        return dateHiring;
    }

    public List<Person> getAccounts() {
        return accounts;
    }

    public void addPerson(Person person) {
        this.accounts.add(person);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Employee.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("name='" + name + "'")
                .add("surname='" + surname + "'")
                .add("inn='" + inn + "'")
                .add("datehiring=" + dateHiring)
                .add("persons=" + accounts)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Employee employee = (Employee) o;
        return id == employee.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

