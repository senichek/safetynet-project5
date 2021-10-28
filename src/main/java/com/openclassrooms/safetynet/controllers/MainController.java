package com.openclassrooms.safetynet.controllers;

import java.io.IOException;
import java.util.List;

import com.openclassrooms.safetynet.models.DataSource;
import com.openclassrooms.safetynet.models.Person;
import com.openclassrooms.safetynet.models.DTO.CoveredByFirestationDTO;
import com.openclassrooms.safetynet.services.DataSourceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;


@RestController
@Slf4j
public class MainController {

    @Autowired
    DataSourceService dataSourceService;

    // TODO - delete later
    @GetMapping(value = "/")
    public String greetings() throws IOException {    
        log.info("MainController.greetings() was called."); 
        return "Just checking if it's running. Looks OK!";
    }

    // TODO - delete later
    @GetMapping(value="/data")
    public DataSource getAllData() throws IOException {
        log.info("MainController.getAllData() was called."); 
        return dataSourceService.getAllData();
    }

    // TODO - delete later
    @GetMapping(value="/med")
    public String[] getMed() {
        return new String[]{"a", "b", "c"};
    }

    @GetMapping(value = "/firestation")
    public CoveredByFirestationDTO getAllCoveredByFirestation(@RequestParam (name = "stationNumber", required = false) Integer stationNum) {
        return dataSourceService.getAllPersonsByFirestation(stationNum);
    }
}
