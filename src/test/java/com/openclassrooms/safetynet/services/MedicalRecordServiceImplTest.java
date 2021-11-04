package com.openclassrooms.safetynet.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import com.openclassrooms.safetynet.models.Medicalrecord;
import com.openclassrooms.safetynet.repos.DataRepo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MedicalRecordServiceImplTest {

    @Autowired
    MedicalRecordService medicalRecordService;

    @Autowired
    DataRepo dataRepo;

    @Test
    public void SaveTest() {
        // Save
        Medicalrecord expectedSave = new Medicalrecord("New", "New", LocalDate.of(2005, 8, 30),
                new String[] { "hydrapermazol:300mg", "dodoxadin:30mg" }, new String[] { "shellfish" });
        Medicalrecord fromServiceSaved = medicalRecordService
                .save(new Medicalrecord("New", "New", LocalDate.of(2005, 8, 30),
                        new String[] { "hydrapermazol:300mg", "dodoxadin:30mg" }, new String[] { "shellfish" }));
        assertEquals(expectedSave, fromServiceSaved);
        // Save method will increase the length of the MedicalRecords' list from 23 to
        // 24 elements
        assertEquals(24, dataRepo.getData().getMedicalrecords().size());
        // Update
        Medicalrecord expectedUpdate = new Medicalrecord("New", "New", LocalDate.of(2005, 8, 30),
                new String[] { "hydrapermazolUPDATE:300mg", "dodoxadin:30mg" }, new String[] { "shellfish" });
        Medicalrecord fromServiceUpdated = medicalRecordService
                .save(new Medicalrecord("New", "New", LocalDate.of(2005, 8, 30),
                        new String[] { "hydrapermazolUPDATE:300mg", "dodoxadin:30mg" }, new String[] { "shellfish" }));
        assertEquals(expectedUpdate, fromServiceUpdated);
    }

    @Test
    public void deleteTest() {
        // this person is in the list due to the SaveTest method.
        Medicalrecord toDelete = new Medicalrecord("New", "New", LocalDate.of(2005, 8, 30),
        new String[] { "hydrapermazolUPDATE:300mg", "dodoxadin:30mg" }, new String[] { "shellfish" });
        Medicalrecord deleted = medicalRecordService.delete(toDelete);
        assertEquals(toDelete, deleted);
        // After the deletion the list of persons will be (should be) back to 23
        // elements.
        assertEquals(23, dataRepo.getData().getPersons().size());
    }

    @Test
    public void deleteNonExistantTest() {
        Medicalrecord nonExistant = new Medicalrecord("Non", "Existant", LocalDate.of(2005, 8, 30),
        new String[] { "hydrapermazol:300mg", "dodoxadin:30mg" }, new String[] { "shellfish" });
        assertEquals(null, medicalRecordService.delete(nonExistant));
    }
}
