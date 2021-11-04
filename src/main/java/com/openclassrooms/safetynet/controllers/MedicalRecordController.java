package com.openclassrooms.safetynet.controllers;

import com.openclassrooms.safetynet.models.Medicalrecord;
import com.openclassrooms.safetynet.services.MedicalRecordService;

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
public class MedicalRecordController {

    @Autowired
    private MedicalRecordService medicalRecordService;

    @PostMapping(value = "/medicalRecord", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Medicalrecord> save(@RequestBody Medicalrecord medicalrecord) {
        if (medicalrecord.getFirstName() == null || medicalrecord.getLastName() == null) {
            log.error("Failed to create/update {}", medicalrecord);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else if (medicalrecord.getFirstName().equals("") || medicalrecord.getLastName().equals("")) {
            log.error("Failed to create/update {}", medicalrecord);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            // Save if the Medicalrecord is new and update if the Medicalrecord exists.
            return new ResponseEntity<>(medicalRecordService.save(medicalrecord), HttpStatus.OK);
        }
    }

    @DeleteMapping(value = "/medicalRecord", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Medicalrecord> delete(@RequestBody Medicalrecord medicalrecord) {
        if (medicalrecord.getFirstName() == null || medicalrecord.getLastName() == null) {
            log.error("Failed to delete {}", medicalrecord);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else if (medicalrecord.getFirstName().equals("") || medicalrecord.getLastName().equals("")) {
            log.error("Failed to delete {}", medicalrecord);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(medicalRecordService.delete(medicalrecord), HttpStatus.OK);
        }
    }
    
}
