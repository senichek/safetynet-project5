package com.openclassrooms.safetynet.controllers;

import com.openclassrooms.safetynet.models.Firestation;
import com.openclassrooms.safetynet.services.FirestationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;

@RestController
public class FirestationController {

    @Autowired
    private FirestationService firestationService;

    @PostMapping(value = "/firestation", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Firestation save(@RequestBody Firestation firestation) {
        // Save if the Firestation is new and update if the Firestation exists.
        return firestationService.save(firestation);
    }

    @DeleteMapping(value = "/firestation", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Firestation delete(@RequestBody Firestation firestation) {
        return firestationService.delete(firestation);
    }
}
