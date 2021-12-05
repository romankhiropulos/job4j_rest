package ru.job4j.auth.service;

import org.springframework.stereotype.Service;
import ru.job4j.auth.domain.Person;
import ru.job4j.auth.repository.PersonRepository;

import java.util.List;

@Service
public class PersonService {

    private final PersonRepository repository;

    public PersonService(PersonRepository repository) {
        this.repository = repository;
    }

    public List<Person> getAll() {
        return (List<Person>) repository.findAll();
    }

    public Person save(Person person) {
        return repository.save(person);
    }

    public void delete(int id) {
        Person person = new Person();
        person.setId(id);
        repository.delete(person);
    }

    public List<Person> getByEmployeeId(int id) {
        return repository.findByEmployeeid(id);
    }
}