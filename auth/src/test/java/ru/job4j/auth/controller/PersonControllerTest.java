package ru.job4j.auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.auth.AuthApplication;
import ru.job4j.auth.domain.Person;
import ru.job4j.auth.repository.PersonRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = AuthApplication.class)
@AutoConfigureMockMvc
@Sql({"/schema-test.sql"})
@Sql(
        scripts = "/schema-test-after.sql",
        executionPhase = AFTER_TEST_METHOD
)
public class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PersonRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Sql({"/schema-test-after.sql"})
    public void whenCreatedThenStatus201() throws Exception {
        Person person = Person.of(1, "login1", "password1");
        mockMvc.perform(
                        post("/person/")
                                .content(objectMapper.writeValueAsString(person))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.login").value("login1"))
                .andExpect(jsonPath("$.password").value("password1"));
    }

    @Test
    public void whenGetUserInfoByIdThenStatus200() throws Exception {
        Person personId1 = repository.findById(1).get();
        mockMvc.perform(
                        get("/person/{id}", personId1.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(personId1.getId()))
                .andExpect(jsonPath("$.login").value(personId1.getLogin()))
                .andExpect(jsonPath("$.password").value(personId1.getPassword())
                );
    }

    @Test
    public void whenGetUserInfoByIdThenStatus404() throws Exception {
        mockMvc.perform(
                        get("/persons/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void whenUpdateUserThenStatus200() throws Exception {
        Person personId1 = repository.findById(1).get();
        personId1.setLogin("newLogin");
        personId1.setPassword("newPassword");
        mockMvc.perform(
                        put("/person/")
                                .content(objectMapper.writeValueAsString(personId1))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        mockMvc.perform(
                        get("/person/{id}", personId1.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.login").value("newLogin"))
                .andExpect(jsonPath("$.password").value("newPassword"));
    }

    @Test
    public void whenDeleteUserThenStatus200() throws Exception {
        Person personId1 = repository.findById(1).get();
        mockMvc.perform(
                        delete("/person/{id}", personId1.getId()))
                .andExpect(status().isOk());
        mockMvc.perform(
                        get("/persons/" + personId1.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    public void whenGetAllPersonsThenStatus200() throws Exception {
        List<Person> persons = (List<Person>) repository.findAll();
        mockMvc.perform(get("/person/"))
                .andExpect(status().isOk())
                .andExpect(content().json(
                        objectMapper.writeValueAsString(persons)
                ));
    }
}