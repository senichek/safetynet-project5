package com.openclassrooms.safetynet.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import com.openclassrooms.safetynet.models.DataSource;
import com.openclassrooms.safetynet.models.Firestation;
import com.openclassrooms.safetynet.models.Person;
import com.openclassrooms.safetynet.models.PersonFullDetails;
import com.openclassrooms.safetynet.models.DTO.ChildAlertDTO;
import com.openclassrooms.safetynet.models.DTO.CoveredByFirestationDTO;
import com.openclassrooms.safetynet.models.DTO.FireDTO;

public interface DataSourceService {
    DataSource getAllData();
    CoveredByFirestationDTO getAllPeopleByFirestation(int num);
    List<ChildAlertDTO> getChildrenByAddress(String address);
    List<Person> getHousholdMembers(String firstName, String lastName, String address);
    Set<String> getPhonesByFirestation(Integer fStationNumber);
    FireDTO getAllByAddressInCaseOfFire(String address);
    Set<Firestation> getFirestationsByAddress(String address);
    List<PersonFullDetails> getAllFullDetails();
    LocalDate getBirthdateByName(String fName, String lName);
    String[] getMedicationsByPersonName(String fName, String lName);
    String[] getAllergiesByPersonName(String fName, String lName);
    int getAgeByPersonName(String fName, String lName);
}
