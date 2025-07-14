package com.satetynet.alerts.exception;

public class PersonNotFoundException extends RuntimeException{
    public PersonNotFoundException(String firstName, String lastName) {
        super("Person Not Found: " + firstName + " " + lastName);
    }
}
