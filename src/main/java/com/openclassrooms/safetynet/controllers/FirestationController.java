package com.openclassrooms.safetynet.controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.openclassrooms.safetynet.models.Firestation;
import com.openclassrooms.safetynet.services.FirestationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@RestController
public class FirestationController {

    @Autowired
    private FirestationService firestationService;

    @PostMapping(value = "/firestation", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Firestation> save(@RequestBody Firestation firestation) {
        if (firestation.getAddress().equals("") || firestation.getStation() == 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(firestationService.save(firestation), HttpStatus.OK);
        }
    }

    @PutMapping(value = "/firestation", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Firestation> update(@RequestBody ObjectNode objectNode) {
        // Objectnode allows to accept anything in Json format.
        String address = objectNode.get("address").asText();
        int currentFstationNum = objectNode.get("station").asInt();
        int newFstationNum = objectNode.get("new_num").asInt();

        if (address.equals("") || currentFstationNum == 0 || newFstationNum == 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(firestationService.update(address, currentFstationNum, newFstationNum),
                    HttpStatus.OK);
        }
    }

    @DeleteMapping(value = "/firestation", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Firestation> delete(@RequestBody Firestation firestation) {
        if (firestation.getAddress().equals("") || firestation.getStation() == 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(firestationService.delete(firestation), HttpStatus.OK);
        }
    }
}
