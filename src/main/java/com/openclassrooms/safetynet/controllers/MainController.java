package com.openclassrooms.safetynet.controllers;

import java.io.IOException;

import com.openclassrooms.safetynet.models.DataSource;
import com.openclassrooms.safetynet.models.DTO.CoveredByFirestationDTO;
import com.openclassrooms.safetynet.services.DataSourceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.log4j.Log4j2;


@RestController
@Log4j2
public class MainController {

    @Autowired
    DataSourceService dataSourceService;

    // TODO - delete later
    @GetMapping(value = "/")
    public String greetings() throws IOException {    
        return "Just checking if it's running. Looks OK!";
    }

    // TODO - delete later
    @GetMapping(value="/data")
    public DataSource getAllData() throws IOException {
    
        return dataSourceService.getAllData();
    }

    // TODO - delete later
    @GetMapping(value="/med")
    public String[] getMed() {
        return new String[]{"a", "b", "c"};
    }

    @GetMapping(value = "/firestation")
    public ResponseEntity<CoveredByFirestationDTO> getAllCoveredByFirestation(@RequestParam (name = "stationNumber", required = false) Integer stationNum) {
        log.info("MainController.getAllCoveredByFirestation(stationNum) was called.");
        if (stationNum == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(dataSourceService.getAllPersonsByFirestation(stationNum), HttpStatus.OK);
        }
    }
}