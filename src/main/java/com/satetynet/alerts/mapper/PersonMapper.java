package com.satetynet.alerts.mapper;

import com.satetynet.alerts.dto.PersonRequestDTO;
import com.satetynet.alerts.model.Person;
import org.springframework.stereotype.Component;

@Component
public class PersonMapper {
    public Person toEntity(PersonRequestDTO dto) {
        return Person.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .address(dto.getAddress())
                .city(dto.getCity())
                .zip(dto.getZip())
                .phone(dto.getPhone())
                .email(dto.getEmail())
                .build();
    }
}
