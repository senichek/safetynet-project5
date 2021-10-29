package com.openclassrooms.safetynet.services;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.openclassrooms.safetynet.models.DataSource;
import com.openclassrooms.safetynet.models.Medicalrecord;
import com.openclassrooms.safetynet.models.Person;
import com.openclassrooms.safetynet.models.DTO.CoveredByFirestationDTO;
import com.openclassrooms.safetynet.utils.JsonReader;

import org.springframework.stereotype.Service;

@Service
public class DataSourceServiceImpl implements DataSourceService {

    private DataSource dataSource;

    public DataSourceServiceImpl() throws IOException {
        this.dataSource = JsonReader.getData("https://s3-eu-west-1.amazonaws.com/course.oc-static.com/projects/DA+Java+EN/P5+/data.json");
    }

    @Override
    public DataSource getAllData() {
        return this.dataSource;
    }

    @Override
    public CoveredByFirestationDTO getAllPersonsByFirestation(int num) {
        Set<String> streetsCoveredbyFirestation = new HashSet<>();
        List<Person> peopleCoveredByFirestation = new ArrayList<>();
        CoveredByFirestationDTO dto = new CoveredByFirestationDTO();

        dataSource.getFirestations().forEach(fStation -> {
            if (fStation.getStation() == num) {
                streetsCoveredbyFirestation.add(fStation.getAddress());
            }
        });

        for (String street: streetsCoveredbyFirestation) {
            for (int i = 0; i < dataSource.getPersons().size(); i++) {
                if (street.equals(dataSource.getPersons().get(i).getAddress())) {
                    peopleCoveredByFirestation.add(dataSource.getPersons().get(i));
                }
            }
        }

        int numOfKids = getAllYoungerThan(19, peopleCoveredByFirestation).size();
        int numOfAdults = peopleCoveredByFirestation.size() - numOfKids;

        dto.setPersons(peopleCoveredByFirestation);
        dto.setNumberOfKids(numOfKids);
        dto.setNumberOfAdults(numOfAdults);
        // Setting Nulls to the values we do not want to show in Json response.
        dto.getPersons().forEach(person -> {
            person.setEmail(null);
        });
        return dto;
    }

    @Override
    public List<Person> getAllYoungerThan(int age, List<Person> data) {
        List<Person> result = new ArrayList<>();
        LocalDate today = LocalDate.now();
        for (Person person: data) {
            for (Medicalrecord medRec: dataSource.getMedicalrecords()) {
                if (person.getFirstName().equals(medRec.getFirstName()) &&
                    person.getLastName().equals(medRec.getLastName()) &&
                    medRec.getBirthdate().isAfter(today.minusYears(age))
                ) {
                    result.add(person);
                }
            }

        }
        return result;
    }
}
