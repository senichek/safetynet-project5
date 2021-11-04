package com.openclassrooms.safetynet.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.openclassrooms.safetynet.models.Person;
import com.openclassrooms.safetynet.repos.DataRepo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PersonServiceImplTest {

    @Autowired
    PersonService personService;

    @Autowired
    DataRepo dataRepo;

    @Test
    public void saveTest() {
        // Save
        Person expectedSave = new Person("New", "New", "New_Address", "New_City", "95000", "111-222-333", "newP@gmail.com");
        Person fromServiceSaved = personService.save(new Person("New", "New", "New_Address", "New_City", "95000", "111-222-333", "newP@gmail.com"));
        assertEquals(expectedSave, fromServiceSaved);
        // Save method will increase the length of the persons' list from 23 to 24 elements
        assertEquals(24, dataRepo.getData().getPersons().size());
        // Update
        Person expectedUpdate = new Person("New", "New", "Updated", "Updated", "95_updated", "updated-222-333", "updated@gmail.com");
        Person fromServiceUpdated = personService.save(new Person("New", "New", "Updated", "Updated", "95_updated", "updated-222-333", "updated@gmail.com"));
        assertEquals(expectedUpdate, fromServiceUpdated);
    }

    @Test
    public void deleteTest() {
        // this person is in the list due to the SaveTest method.
        Person toDelete = new Person("New", "New", "Updated", "Updated", "95_updated", "updated-222-333", "updated@gmail.com");
        Person deleted = personService.delete(toDelete);
        assertEquals(toDelete, deleted);
        // After the deletion the list of persons will be (should be) back to 23 elements.
        assertEquals(23, dataRepo.getData().getPersons().size());
    }

    @Test
    public void deleteNonExistantTest() {
        Person nonExistant = new Person("Non", "Existant", "none", "", "none", "111-222-333", "nonExistant@gmail.com");
        assertEquals(null, personService.delete(nonExistant));
    }
}
