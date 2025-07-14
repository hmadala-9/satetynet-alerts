package com.satetynet.alerts.mapper;

import com.satetynet.alerts.dto.PersonRequestDTO;
import com.satetynet.alerts.model.Person;

public class PersonMapper {
    public static Person toEntity(PersonRequestDTO dto) {
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
