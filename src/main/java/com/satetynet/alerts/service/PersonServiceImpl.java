package com.satetynet.alerts.service;

import com.satetynet.alerts.exception.PersonNotFoundException;
import com.satetynet.alerts.model.Person;
import com.satetynet.alerts.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

    private static final Logger log = LoggerFactory.getLogger(PersonServiceImpl.class);

    private final PersonRepository personRepository;

    @Override
    public void add(Person person) {
        log.debug("Adding person: {} {}", person.getFirstName(), person.getLastName());
        personRepository.save(person);
    }

    @Override
    public void update(Person person) {
        log.debug("Updating person: {} {}", person.getFirstName(), person.getLastName());
        personRepository.findByKey(person.getFirstName(), person.getLastName())
                .orElseThrow(() -> new PersonNotFoundException(person.getFirstName(), person.getLastName()));
        personRepository.save(person);
    }

    @Override
    public void delete(String firstName, String lastName) {
        log.debug("Deleting person: {} {}", firstName, lastName);
        personRepository.findByKey(firstName, lastName)
                .orElseThrow(() -> new PersonNotFoundException(firstName, lastName));
        personRepository.deleteByKey(firstName, lastName);
    }
}
