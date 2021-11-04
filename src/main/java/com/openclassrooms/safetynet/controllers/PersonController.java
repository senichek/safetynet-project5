package com.openclassrooms.safetynet.controllers;

import com.openclassrooms.safetynet.models.Person;
import com.openclassrooms.safetynet.services.PersonService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.log4j.Log4j2;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@RestController
@Log4j2
public class PersonController {

    @Autowired
    private PersonService personService;

    @PostMapping(value = "/person", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Person> save(@RequestBody Person person) {
        if (person.getFirstName() == null || person.getLastName() == null) {
            log.error("Failed to create/update {}", person);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else if (person.getFirstName().equals("") || person.getLastName().equals("")) {
            log.error("Failed to create/update {}", person);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            // Save if the Person is new and update if the Person exists.
            return new ResponseEntity<>(personService.save(person), HttpStatus.OK);
        }
    }

    @DeleteMapping(value = "/person", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Person> delete(@RequestBody Person person) {
        if (person.getFirstName() == null || person.getLastName() == null) {
            log.error("Failed to delete {}", person);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else if (person.getFirstName().equals("") || person.getLastName().equals("")) {
            log.error("Failed to delete {}", person);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(personService.delete(person), HttpStatus.OK);
        }
    }
}
