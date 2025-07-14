package com.satetynet.alerts.service;

import com.satetynet.alerts.dto.PersonRequestDTO;

public interface PersonService {
    void add(PersonRequestDTO dto);
    void update(PersonRequestDTO dto);
    void delete(String firstName, String lastName);
}
