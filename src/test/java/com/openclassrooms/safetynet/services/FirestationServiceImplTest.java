package com.openclassrooms.safetynet.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.openclassrooms.safetynet.models.Firestation;
import com.openclassrooms.safetynet.repos.DataRepo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

@SpringBootTest
@DirtiesContext(classMode = ClassMode.AFTER_CLASS) // Tests change the collection, this is why we have to reset the collection
public class FirestationServiceImplTest {

    @Autowired
    private FirestationService firestationService;

    @Autowired
    private DataRepo dataRepo;

    @Test
    public void saveUpdateTest() {
        // Save
        Firestation expectedSave = new Firestation("New address", 5);
        Firestation fromServiceSaved = firestationService.save(new Firestation("New address", 5));
        assertEquals(expectedSave, fromServiceSaved);
        // Save method will increase the length of the firestatuons' list from 13 to 14 elements
        assertEquals(14, dataRepo.getData().getFirestations().size());
        // Update
        Firestation expectedUpdate = new Firestation("New address", 6);
        Firestation fromServiceUpdated = firestationService.update("New address", 5, 6);
        assertEquals(expectedUpdate, fromServiceUpdated);

        // Trying to save the Firestation that exists already, it should return null.
        assertNull(firestationService.save(new Firestation("New address", 6)));
    }

    @Test
    public void deleteTest() {
        // this person is in the list due to the SaveTest method.
        Firestation toDelete = new Firestation("112 Steppes Pl", 3);
        Firestation deleted = firestationService.delete(toDelete);
        assertEquals(toDelete, deleted);
        // After the deletion the list of firestations will be (should be) back to 23 elements.
        assertEquals(13, dataRepo.getData().getFirestations().size());
    }

    @Test
    public void deleteNonExistantTest() {
        Firestation nonExistant = new Firestation("Non Existant Street", 25);
        assertEquals(null, firestationService.delete(nonExistant));
    }

    @Test
    public void updateNonExistantTest() {
        Firestation nonExistant = new Firestation("Non Existant Street", 25);
        assertEquals(null, firestationService.update(nonExistant.getAddress(), nonExistant.getStation(), 11));
    }
}
