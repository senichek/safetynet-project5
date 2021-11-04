package com.openclassrooms.safetynet.controllers;

import com.openclassrooms.safetynet.models.Medicalrecord;
import com.openclassrooms.safetynet.services.MedicalRecordService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;

@RestController
public class MedicalRecordController {

    @Autowired
    MedicalRecordService medicalRecordService;

    @PostMapping(value = "/medicalRecord", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Medicalrecord save(@RequestBody Medicalrecord medicalrecord) {
        // Save if the Medicalrecord is new and update if the Medicalrecord exists.
        return medicalRecordService.save(medicalrecord);
    }

    @DeleteMapping(value = "/medicalRecord", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Medicalrecord delete(@RequestBody Medicalrecord medicalrecord) {
        return medicalRecordService.delete(medicalrecord);
    }
}
