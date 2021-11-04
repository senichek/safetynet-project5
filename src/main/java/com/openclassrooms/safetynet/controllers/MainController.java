package com.openclassrooms.safetynet.controllers;

import java.util.List;
import java.util.Set;

import com.openclassrooms.safetynet.models.DataSource;
import com.openclassrooms.safetynet.models.PersonFullDetails;
import com.openclassrooms.safetynet.models.DTO.ChildAlertDTO;
import com.openclassrooms.safetynet.models.DTO.CoveredByFirestationDTO;
import com.openclassrooms.safetynet.models.DTO.FireDTO;
import com.openclassrooms.safetynet.models.DTO.FloodDTO;
import com.openclassrooms.safetynet.services.URLsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class MainController {

    @Autowired
    private URLsService urlService;

    // TODO - delete later
    @GetMapping(value = "/data")
    public DataSource allData() {
        return urlService.getAllData();
    }

    // TODO - delete later
    @GetMapping(value = "/hello")
    public String hello() {
        return "hello";
    }

    @GetMapping(value = "/firestation")
    public ResponseEntity<CoveredByFirestationDTO> getAllCoveredByFirestation(
            @RequestParam(name = "stationNumber", required = false) Integer stationNum) {
        if (stationNum == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(urlService.getAllPeopleByFirestation(stationNum), HttpStatus.OK);
        }
    }

    @GetMapping(value = "/childAlert")
    public ResponseEntity<List<ChildAlertDTO>> getChildrenByAddress(
            @RequestParam(name = "address", required = false) String address) {
        if (address.equals("") || urlService.getChildrenByAddress(address) == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(urlService.getChildrenByAddress(address), HttpStatus.OK);
        }
    }

    @GetMapping(value = "/phoneAlert")
    public ResponseEntity<Set<String>> getPhonesByFirestation(
            @RequestParam(name = "firestation", required = false) Integer num) {
        if (num == null || urlService.getPhonesByFirestation(num) == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(urlService.getPhonesByFirestation(num), HttpStatus.OK);
        }
    }

    @GetMapping(value = "/fire")
    public ResponseEntity<FireDTO> getAllByAddressFire(
            @RequestParam(name = "address", required = false) String address) {
        if (address.equals("") || urlService.getAllByAddressInCaseOfFire(address) == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(urlService.getAllByAddressInCaseOfFire(address), HttpStatus.OK);
        }
    }

    @GetMapping(value = "/flood/stations")
    public ResponseEntity<List<FloodDTO>> getAllByFireStationNumberFlood(
            @RequestParam(name = "stations", required = false) Set<Integer> fStationNums) {
        if (fStationNums.size() == 0 || urlService.getAllByFireStationNumberFlood(fStationNums) == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(urlService.getAllByFireStationNumberFlood(fStationNums), HttpStatus.OK);
        }
    }

    @GetMapping(value = "/personInfo")
    public ResponseEntity<List<PersonFullDetails>> getAllByFirstNameAndLastName(
            @RequestParam(name = "firstName", required = false) String firstName,
            @RequestParam(name = "lastName", required = false) String lastName) {
        if (urlService.getAllByFirstNameAndLastName(firstName, lastName) == null
                || urlService.getAllByFirstNameAndLastName(firstName, lastName).size() == 0) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(urlService.getAllByFirstNameAndLastName(firstName, lastName),
                    HttpStatus.OK);
        }
    }

    @GetMapping(value = "/communityEmail")
    public ResponseEntity<Set<String>> getEmailsByCityName(@RequestParam(name = "city", required = false) String city) {
        if (urlService.getEmailsByCityName(city) == null
                || urlService.getEmailsByCityName(city).size() == 0) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(urlService.getEmailsByCityName(city), HttpStatus.OK);
        }
    }
}