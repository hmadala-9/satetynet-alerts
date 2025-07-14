package com.satetynet.alerts.service;

import com.satetynet.alerts.model.Person;

public interface PersonService {
    void add(Person person);
    void update(Person person);
    void delete(String firstName, String lastName);
}
