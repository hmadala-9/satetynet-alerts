package com.satetynet.alerts.controllers;

import com.satetynet.alerts.dto.PersonRequestDTO;
import com.satetynet.alerts.mapper.PersonMapper;
import com.satetynet.alerts.service.PersonService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequiredArgsConstructor
public class PersonController {

    private static final Logger log = LoggerFactory.getLogger(PersonController.class);
    private final PersonService personService;

    @PostMapping(path = "/person")
    public ResponseEntity<String> addPerson(@Valid @RequestBody PersonRequestDTO requestDTO) {
        log.info("Add person: {} {}", requestDTO.getFirstName(), requestDTO.getLastName());
        personService.add(PersonMapper.toEntity(requestDTO));
        return ResponseEntity.ok("Person added successfully");
    }

    @PutMapping(path = "/person")
    public ResponseEntity<String> updatePerson(@Valid @RequestBody PersonRequestDTO requestDTO) {
        log.info("Update person: {} {}", requestDTO.getFirstName(), requestDTO.getLastName());
        personService.update(PersonMapper.toEntity(requestDTO));
        return ResponseEntity.ok("Person updated successfully");
    }

    @DeleteMapping(path = "/person")
    public ResponseEntity<String> deletePerson(@RequestParam String firstName,
                                               @RequestParam String lastName) {
        log.info("Delete person: {} {}", firstName, lastName);
        personService.delete(firstName, lastName);
        return ResponseEntity.ok("Person deleted successfully");
    }
}