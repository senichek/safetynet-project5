package com.openclassrooms.safetynet.services;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.openclassrooms.safetynet.models.DataSource;
import com.openclassrooms.safetynet.models.Firestation;
import com.openclassrooms.safetynet.models.Medicalrecord;
import com.openclassrooms.safetynet.models.Person;
import com.openclassrooms.safetynet.models.PersonFullDetails;
import com.openclassrooms.safetynet.models.DTO.ChildAlertDTO;
import com.openclassrooms.safetynet.models.DTO.CoveredByFirestationDTO;
import com.openclassrooms.safetynet.models.DTO.FireDTO;
import com.openclassrooms.safetynet.utils.JsonReader;

import org.springframework.stereotype.Service;

@Service
public class DataSourceServiceImpl implements DataSourceService {

    private DataSource dataSource;

    private List<PersonFullDetails> peopleFullDetails;

    public DataSourceServiceImpl() throws IOException {
        this.dataSource = JsonReader
                .getData("https://s3-eu-west-1.amazonaws.com/course.oc-static.com/projects/DA+Java+EN/P5+/data.json");

        // Copying data from 3 collections to a single list so that
        // we have all the data we might need in one place.
        peopleFullDetails = new ArrayList<>();
        dataSource.getPersons().forEach(person -> {
            PersonFullDetails personFullDetails = new PersonFullDetails();
            personFullDetails.setFirstName(person.getFirstName());
            personFullDetails.setLastName(person.getLastName());
            personFullDetails.setAddress(person.getAddress());
            personFullDetails.setCity(person.getCity());
            personFullDetails.setZip(person.getZip());
            personFullDetails.setPhone(person.getPhone());
            personFullDetails.setEmail(person.getEmail());
            personFullDetails.setBirthdate(getBirthdateByName(person.getFirstName(), person.getLastName()));
            personFullDetails.setAge(getAgeByPersonName(person.getFirstName(), person.getLastName()));
            personFullDetails.setMedications(getMedicationsByPersonName(person.getFirstName(), person.getLastName()));
            personFullDetails.setAllergies(getAllergiesByPersonName(person.getFirstName(), person.getLastName()));
            personFullDetails.setFirestations(getFirestationsByAddress(person.getAddress()));
            peopleFullDetails.add(personFullDetails);
        });
    }

    @Override
    public DataSource getAllData() {
        return this.dataSource;
    }

    @Override
    public List<PersonFullDetails> getAllFullDetails() {
        return peopleFullDetails;
    }

    @Override
    public CoveredByFirestationDTO getAllPeopleByFirestation(int num) {
        CoveredByFirestationDTO dto = new CoveredByFirestationDTO();
        List<PersonFullDetails> peopleCoveredByFirestation = new ArrayList<>();
        peopleFullDetails.forEach(p -> {
            p.getFirestations().forEach(fStation -> {
                if (fStation.getStation() == num) {
                    PersonFullDetails person = new PersonFullDetails();
                    person.setFirstName(p.getFirstName());
                    person.setLastName(p.getLastName());
                    person.setAddress(p.getAddress());
                    person.setCity(p.getCity());
                    person.setZip(p.getZip());
                    person.setPhone(p.getPhone());
                    person.setAge(p.getAge());
                    peopleCoveredByFirestation.add(person);
                }
            });
        });
        int numOfKids = peopleCoveredByFirestation.stream().filter(person -> person.getAge() < 19).collect(Collectors.toList()).size();
        int numOfAdults = peopleCoveredByFirestation.size() - numOfKids;

        dto.setPersons(peopleCoveredByFirestation);
        dto.setNumberOfKids(numOfKids);
        dto.setNumberOfAdults(numOfAdults);
        return dto;
    }

    @Override
    public List<ChildAlertDTO> getChildrenByAddress(String address) {
        List<PersonFullDetails> filteredData = new ArrayList<>();
        // Filtering full collection by address
        filteredData = peopleFullDetails.stream().filter(person -> person.getAddress().equals(address)).collect(Collectors.toList());
        // Looking for the kids in the filtered results.
        filteredData = filteredData.stream().filter(person -> person.getAge() < 19).collect(Collectors.toList());
        List<ChildAlertDTO> result = new ArrayList<>();
        filteredData.forEach(p -> {
            ChildAlertDTO childAlertDTO = new ChildAlertDTO();
            childAlertDTO.setFirstName(p.getFirstName());
            childAlertDTO.setLastName(p.getLastName());
            childAlertDTO.setAge(p.getAge());
            childAlertDTO.setMembersOfHoushold(getHousholdMembers(p.getFirstName(), p.getLastName(), p.getAddress()));
            result.add(childAlertDTO);
        });
        return result;
    }

    @Override
    public List<Person> getHousholdMembers(String firstName, String lastName, String address) {
        List<Person> result = new ArrayList<>();
        dataSource.getPersons().forEach(person -> {
            if (person.getLastName().equals(lastName) && person.getAddress().equals(address)
                    && !person.getFirstName().equals(firstName)) {
                result.add(person);
            }
        });
        return result;
    }

    @Override
    public Set<String> getPhonesByFirestation(Integer fStationNumber) {
        Set<String> result = new HashSet<>();
       peopleFullDetails.forEach(p -> {
           p.getFirestations().forEach(fStation -> {
               if (fStation.getStation() == fStationNumber) {
                    result.add(p.getPhone());
               }
           });
       });
        return result;
    }

    @Override
    public FireDTO getAllByAddressInCaseOfFire(String address) {
        List<PersonFullDetails> peopleByAddress = new ArrayList<>();
        Set<Integer> fireStationsNumbers = new HashSet<>();
        FireDTO result = new FireDTO();
        peopleFullDetails.forEach(person -> {
            if (person.getAddress().equals(address)) {
                PersonFullDetails personFullDetails = new PersonFullDetails();
                personFullDetails.setLastName(person.getLastName());
                personFullDetails.setPhone(person.getPhone());
                personFullDetails.setAge(person.getAge());
                personFullDetails.setMedications(person.getMedications());
                personFullDetails.setAllergies(person.getAllergies());
                peopleByAddress.add(personFullDetails);

                person.getFirestations().forEach(fStation -> {
                    fireStationsNumbers.add(fStation.getStation());
                });
            }
        });
        result.setPersons(peopleByAddress);
        result.setFireStationNumbers(fireStationsNumbers);
        return result;
    }

    @Override
    public Set<Firestation> getFirestationsByAddress(String address) {
        Set<Firestation> result = new HashSet<>();
        dataSource.getFirestations().forEach(fStation -> {
            if (fStation.getAddress().equals(address)) {
                result.add(fStation);
            }
        });
        return result;
    }

    @Override
    public LocalDate getBirthdateByName(String fName, String lName) {
        LocalDate birthdate = null;
        for (Medicalrecord medRec : dataSource.getMedicalrecords()) {
            if (fName.equals(medRec.getFirstName()) && lName.equals(medRec.getLastName())) {
                birthdate = medRec.getBirthdate();
            }
        }
        return birthdate;
    }

    @Override
    public String[] getMedicationsByPersonName(String fName, String lName) {
        String[] medications = null;
        for (Medicalrecord medRec : dataSource.getMedicalrecords()) {
            if (fName.equals(medRec.getFirstName()) && lName.equals(medRec.getLastName())) {
                medications = medRec.getMedications();
            }
        }
        return medications;
    }

    @Override
    public String[] getAllergiesByPersonName(String fName, String lName) {
        String[] allegries = null;
        for (Medicalrecord medRec : dataSource.getMedicalrecords()) {
            if (fName.equals(medRec.getFirstName()) && lName.equals(medRec.getLastName())) {
                allegries = medRec.getAllergies();
            }
        }
        return allegries;
    }

    @Override
    public int getAgeByPersonName(String fName, String lName) {
        LocalDate today = LocalDate.now();
        Integer age = null;
        for (Medicalrecord medRec : dataSource.getMedicalrecords()) {
            if (fName.equals(medRec.getFirstName()) && lName.equals(medRec.getLastName())) {
                age = Period.between(medRec.getBirthdate(), today).getYears();
            }
        }
        return age;
    }
}
