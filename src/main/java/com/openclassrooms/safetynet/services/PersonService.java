package com.openclassrooms.safetynet.services;

import com.openclassrooms.safetynet.models.Person;

public interface PersonService {
    Person save(Person person);
    Person delete(Person person);
}
