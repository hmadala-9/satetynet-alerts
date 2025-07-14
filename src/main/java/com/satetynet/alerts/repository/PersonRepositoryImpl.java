package com.satetynet.alerts.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.satetynet.alerts.model.Person;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class PersonRepositoryImpl implements PersonRepository {

    private static final Logger log = LoggerFactory.getLogger(PersonRepositoryImpl.class);
    private final ObjectMapper mapper;
    private final String dataFilePath;
    private final Map<String, Person> store = new ConcurrentHashMap<>();

    public PersonRepositoryImpl(ObjectMapper mapper, @Value("${data.json.file.path}") String dataFilePath) {
        this.mapper = mapper;
        this.dataFilePath = dataFilePath;
    }

    @PostConstruct
    public void loadData() throws Exception {
        log.info("Loading persons from {}", dataFilePath);
        Map<String, Object> root = mapper.readValue(new File(dataFilePath), new TypeReference<Map<String, Object>>() {});
        List<Person> persons = mapper.convertValue(root.get("persons"), new TypeReference<List<Person>>() {});

        persons.forEach(p -> store.put(buildKey(p.getFirstName(), p.getLastName()), p));
        log.info("Loaded {} persons", store.size());
    }

    private String buildKey(String... keys) {
        return String.join(":", Arrays.stream(keys)
                .map(String::toLowerCase)
                .toArray(String[]::new));
    }

    @Override
    public Optional<Person> findByKey(String... keys) {
        return Optional.ofNullable(store.get(buildKey(keys)));
    }

    @Override
    public Person save(Person person) {
        store.put(buildKey(person.getFirstName(), person.getLastName()), person);
        return person;
    }

    @Override
    public void deleteByKey(String... keys) {
        store.remove(buildKey(keys));
    }
}
