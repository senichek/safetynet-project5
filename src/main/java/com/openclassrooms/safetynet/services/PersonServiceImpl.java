package com.openclassrooms.safetynet.services;

import com.openclassrooms.safetynet.models.Person;
import com.openclassrooms.safetynet.repos.DataRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class PersonServiceImpl implements PersonService {

    @Autowired
    DataRepo dataRepo;

    @Override
    public Person save(Person person) {
        Boolean matchFound = false;
        Boolean changeDetected = false;
        Person updated = new Person();
        for (int i = 0; i < dataRepo.getData().getPersons().size(); i++) {
            if (dataRepo.getData().getPersons().get(i).getFirstName().equals(person.getFirstName()) && 
                dataRepo.getData().getPersons().get(i).getLastName().equals(person.getLastName())) {
                // Match found, now update the values.
                matchFound = true;
                updated = dataRepo.getData().getPersons().get(i);
                if (!updated.getAddress().equals(person.getAddress())) {
                    changeDetected = true;
                    updated.setAddress(person.getAddress());
                }
                if (!updated.getCity().equals(person.getCity())) {
                    changeDetected = true;
                    updated.setCity(person.getCity());
                }
                if (!updated.getZip().equals(person.getZip())) {
                    changeDetected = true;
                    updated.setZip(person.getZip());
                }
                if (!updated.getPhone().equals(person.getPhone())) {
                    changeDetected = true;
                    updated.setPhone(person.getPhone());
                }
                if (!updated.getEmail().equals(person.getEmail())) {
                    changeDetected = true;
                    updated.setEmail(person.getEmail());
                }
                if (changeDetected) {
                    dataRepo.getData().getPersons().set(i, updated);
                    log.info("Updated {}", updated);
                } else {
                    log.info("The entry is not updated because no changes detected.");
                }
            }
        }

        if (matchFound) {
            return updated;
        } else {
            dataRepo.getData().getPersons().add(person);
            log.info("Created {}", person);
            return person;
        }
    }

    @Override
    public Person delete(Person person) {
        Boolean matchFound = false;
        Person deleted = new Person();
        for (int i = 0; i < dataRepo.getData().getPersons().size(); i++) {
            if (dataRepo.getData().getPersons().get(i).getFirstName().equals(person.getFirstName())
                    && dataRepo.getData().getPersons().get(i).getLastName().equals(person.getLastName())) {
                // Match found, delete
                matchFound = true;
                deleted = dataRepo.getData().getPersons().get(i);
                dataRepo.getData().getPersons().remove(deleted);
                log.info("Deleted {}", person);
            }
        }
        if (matchFound) {
            return deleted;
        } else {
            return null;
        }
    }
}
