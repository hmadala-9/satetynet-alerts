package com.satetynet.alerts.service;

import com.satetynet.alerts.dto.PersonRequestDTO;
import com.satetynet.alerts.exception.PersonNotFoundException;
import com.satetynet.alerts.mapper.PersonMapper;
import com.satetynet.alerts.model.Person;
import com.satetynet.alerts.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PersonServiceImplTest {

    private PersonRepository personRepository;
    private PersonMapper personMapper;
    private PersonServiceImpl personService;

    private static final PersonRequestDTO DTO = PersonRequestDTO.builder()
            .firstName("Lebron")
            .lastName("James")
            .address("324 Hello St")
            .city("Akron")
            .zip("75033")
            .phone("123-435-2345")
            .email("lebron.j@gmail.com")
            .build();

    private static final Person ENTITY = Person.builder()
            .firstName("Lebron")
            .lastName("James")
            .address("324 Hello St")
            .city("Akron")
            .zip("75033")
            .phone("123-435-2345")
            .email("lebron.j@gmail.com")
            .build();

    @BeforeEach
    void setUp() {
        personRepository = mock(PersonRepository.class);
        personMapper = mock(PersonMapper.class);
        personService = new PersonServiceImpl(personRepository, personMapper);
    }

    @Test
    void addPerson_shouldCallSave() {
        when(personMapper.toEntity(DTO)).thenReturn(ENTITY);

        personService.add(DTO);

        verify(personMapper).toEntity(DTO);
        verify(personRepository).save(ENTITY);
    }

    @Test
    void updatePerson_shouldCallSave_whenExists() {
        when(personRepository.findByKey("Lebron", "James")).thenReturn(Optional.of(ENTITY));
        when(personMapper.toEntity(DTO)).thenReturn(ENTITY);

        personService.update(DTO);

        verify(personRepository).findByKey("Lebron", "James");
        verify(personRepository).save(ENTITY);
    }

    @Test
    void updatePerson_shouldThrow_whenNotExists() {
        when(personRepository.findByKey("Lebron", "James")).thenReturn(Optional.empty());

        assertThrows(PersonNotFoundException.class, () -> personService.update(DTO));

        verify(personRepository).findByKey("Lebron", "James");
        verify(personRepository, never()).save(any());
    }

    @Test
    void deletePerson_shouldCallDelete_whenExists() {
        when(personRepository.findByKey("Lebron", "James")).thenReturn(Optional.of(ENTITY));

        personService.delete("Lebron", "James");

        verify(personRepository).findByKey("Lebron", "James");
        verify(personRepository).deleteByKey("Lebron", "James");
    }

    @Test
    void deletePerson_shouldThrow_whenNotExists() {
        when(personRepository.findByKey("Lebron", "James")).thenReturn(Optional.empty());

        assertThrows(PersonNotFoundException.class, () -> personService.delete("Lebron", "James"));

        verify(personRepository).findByKey("Lebron", "James");
        verify(personRepository, never()).deleteByKey(any(), any());
    }
}
