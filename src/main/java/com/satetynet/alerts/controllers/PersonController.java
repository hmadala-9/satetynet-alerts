package com.satetynet.alerts.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.satetynet.alerts.model.Person;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

@RestController
public class PersonController {

    @Value("${data.json.file.path}")
    private String jsonFilePath;

    private List<Person> persons;

    @GetMapping("/personDetails")
    public List<Person> getPersonDetails() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(Paths.get(jsonFilePath).toFile());
        return objectMapper.convertValue(rootNode.get("persons"), objectMapper.getTypeFactory().constructCollectionType(List.class, Person.class));
    }
}
