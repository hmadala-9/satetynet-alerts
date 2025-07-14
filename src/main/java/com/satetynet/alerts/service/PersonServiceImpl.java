package com.satetynet.alerts.service;

import com.satetynet.alerts.dto.PersonRequestDTO;
import com.satetynet.alerts.exception.PersonNotFoundException;
import com.satetynet.alerts.mapper.PersonMapper;
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
    private final PersonMapper personMapper;

    @Override
    public void add(PersonRequestDTO dto) {
        log.debug("Adding person: {} {}", dto.getFirstName(), dto.getLastName());
        Person person = personMapper.toEntity(dto);
        personRepository.save(person);
    }

    @Override
    public void update(PersonRequestDTO dto) {
        log.debug("Updating person: {} {}", dto.getFirstName(), dto.getLastName());
        personRepository.findByKey(dto.getFirstName(), dto.getLastName())
                .orElseThrow(() -> new PersonNotFoundException(dto.getFirstName(), dto.getLastName()));
        Person person = personMapper.toEntity(dto);
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
