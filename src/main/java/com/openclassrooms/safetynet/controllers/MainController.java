package com.openclassrooms.safetynet.controllers;

import java.util.List;
import java.util.Set;

import com.openclassrooms.safetynet.models.PersonFullDetails;
import com.openclassrooms.safetynet.models.DTO.ChildAlertDTO;
import com.openclassrooms.safetynet.models.DTO.CoveredByFirestationDTO;
import com.openclassrooms.safetynet.models.DTO.FireDTO;
import com.openclassrooms.safetynet.models.DTO.FloodDTO;
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

    //TODO - delete later
    @GetMapping(value = "/data")
    public List<PersonFullDetails> allData() {
        return dataSourceService.getAllFullDetails();
    }

    @GetMapping(value = "/firestation")
    public ResponseEntity<CoveredByFirestationDTO> getAllCoveredByFirestation(
            @RequestParam(name = "stationNumber", required = false) Integer stationNum) {
        log.info("MainController.getAllCoveredByFirestation(stationNum) was called.");
        if (stationNum == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(dataSourceService.getAllPeopleByFirestation(stationNum), HttpStatus.OK);
        }
    }

    @GetMapping(value = "/childAlert")
    public ResponseEntity<List<ChildAlertDTO>> getChildrenByAddress(
            @RequestParam(name = "address", required = false) String address) {
        log.info("MainController.getChildrenByAddress(address) was called.");
        if (address == null || dataSourceService.getChildrenByAddress(address) == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(dataSourceService.getChildrenByAddress(address), HttpStatus.OK);
        }
    }

    @GetMapping(value = "/phoneAlert")
    public ResponseEntity<Set<String>> getPhonesByFirestation(@RequestParam(name = "firestation", required = false) Integer num) {
        log.info("MainController.getPhonesByFirestation(num) was called.");
        if (num == null || dataSourceService.getPhonesByFirestation(num) == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(dataSourceService.getPhonesByFirestation(num), HttpStatus.OK);
        }
    }

    @GetMapping(value = "/fire")
    public ResponseEntity<FireDTO> getAllByAddressFire(@RequestParam(name = "address", required = false) String address) {
        log.info("MainController.getAllByAddressInCaseOfFire(address) was called.");
        if (address == null || dataSourceService.getAllByAddressInCaseOfFire(address) == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(dataSourceService.getAllByAddressInCaseOfFire(address), HttpStatus.OK);
        }
    }

    @GetMapping(value = "/flood/stations")
    public ResponseEntity<List<FloodDTO>> getAllByFireStationNumberFlood(@RequestParam(name = "stations", required = false) Set<Integer> fStationNums) {
        log.info("MainController.getAllByFireStationNumberFlood(fStationNums) was called.");
        if (fStationNums == null || dataSourceService.getAllByFireStationNumberFlood(fStationNums) == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(dataSourceService.getAllByFireStationNumberFlood(fStationNums), HttpStatus.OK);
        }
    }
}