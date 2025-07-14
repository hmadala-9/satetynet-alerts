package com.satetynet.alerts.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.satetynet.alerts.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

public class PersonRepositoryImplTest {

    private PersonRepositoryImpl personRepository;

    private ObjectMapper objectMapper;



    private static final String JSON_TEMPLATE = """
        {
            "persons": [
                {
                    "firstName": "Lebron",
                    "lastName": "James",
                    "address": "234 Main",
                    "city": "Cleveland",
                    "zip": "75203",
                    "phone": "123-234-2345",
                    "email": "lebron.james@gmail.com"
                }
            ]
        }
        """;

    @BeforeEach
    void setUp() throws Exception {
        Path tempJson = Files.createTempFile("persons-test", ".json");
        Files.writeString(tempJson, JSON_TEMPLATE);

        objectMapper = new ObjectMapper();
        personRepository = new PersonRepositoryImpl(objectMapper, tempJson.toAbsolutePath().toString());

        personRepository.loadData();
    }

    @Test
    void loadData_shouldPopulatePerson() {
        Optional<Person> result = personRepository.findByKey("Lebron", "James");
        assertTrue(result.isPresent());
        assertEquals("Cleveland", result.get().getCity());
    }

    @Test
    void saveAndFindByKey_shouldWork() {
        Person person = Person.builder()
                .firstName("Lebron")
                .lastName("James")
                .address("324 Hello St")
                .city("Akron")
                .zip("75033")
                .phone("123-435-2345")
                .email("lebron.j@gmail.com")
                .build();

        personRepository.save(person);

        Optional<Person> result = personRepository.findByKey("Lebron", "James");
        assertTrue(result.isPresent());
        assertEquals("324 Hello St", result.get().getAddress());
    }

    @Test
    void deleteByKey_shouldRemovePerson() {
        personRepository.deleteByKey("Lebron", "James");

        Optional<Person> result = personRepository.findByKey("Lebron", "James");
        assertFalse(result.isPresent());
    }

}
