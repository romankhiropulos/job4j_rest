package ru.job4j.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.job4j.auth.domain.Employee;
import ru.job4j.auth.domain.Person;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ru.job4j.auth.repository.PersonRepository;
import ru.job4j.auth.service.EmployeeService;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    private static final String PERSON_API = "http://localhost:8080/person/";

    private static final String PERSON_BY_ID_API = "http://localhost:8080/person/{id}";

    private static final String PERSON_BY_EMPLOYEE_ID_API = "http://localhost:8080/person/employee/{employeeid}";

    private final RestTemplate rest;

    private final PersonRepository personRepository;

    private final EmployeeService employeeService;

    public EmployeeController(RestTemplate rest, PersonRepository personRepository, EmployeeService employeeService) {
        this.rest = rest;
        this.personRepository = personRepository;
        this.employeeService = employeeService;
    }

    @GetMapping("/")
    public List<Employee> findAll() {
        return StreamSupport.stream(this.employeeService.getAll().spliterator(), false)
                .peek(employee -> Objects.requireNonNull(rest.exchange(
                        PERSON_BY_EMPLOYEE_ID_API + employee.getId(),
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<Person>>() {
                        }
                ).getBody()).forEach(employee::addPerson))
                .collect(Collectors.toList());
    }

    @PostMapping("/")
    public ResponseEntity<Person> create(@RequestBody Person person) {
        Person rsl = rest.postForObject(PERSON_API, person, Person.class);
        return new ResponseEntity<>(
                rsl,
                HttpStatus.CREATED
        );
    }

    @PutMapping("/")
    public ResponseEntity<Void> update(@RequestBody Person person) {
        rest.put(PERSON_API, person);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        personRepository.deleteById(id);
        rest.delete(PERSON_BY_ID_API, id);
        return ResponseEntity.ok().build();
    }
}