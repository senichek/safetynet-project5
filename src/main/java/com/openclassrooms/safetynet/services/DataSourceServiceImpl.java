package com.openclassrooms.safetynet.services;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
        // List of people covered by the firestation num
        CoveredByFirestationDTO dto = new CoveredByFirestationDTO();
        List<Person> people = new ArrayList<>();
        Set<String> addressesCoveredbyFirestation = new HashSet<>();
        dataSource.getFirestations().forEach(fStation -> {
            if (fStation.getStation() == num) {
                addressesCoveredbyFirestation.add(fStation.getAddress());
            }
        });

        for (String address : addressesCoveredbyFirestation) {
            for (int i = 0; i < dataSource.getPersons().size(); i++) {
                if (address.equals(dataSource.getPersons().get(i).getAddress())) {
                    Person person = new Person();
                    person.setFirstName(dataSource.getPersons().get(i).getFirstName());
                    person.setLastName(dataSource.getPersons().get(i).getLastName());
                    person.setAddress(dataSource.getPersons().get(i).getAddress());
                    person.setCity(dataSource.getPersons().get(i).getCity());
                    person.setZip(dataSource.getPersons().get(i).getZip());
                    person.setPhone(dataSource.getPersons().get(i).getPhone());
                    people.add(person);
                }
            }
        }
            return dto;
    }

    // if (m.getBirthdate().isBefore(today.minusYears(age)))
    @Override
    public List<Person> getAllYoungerThan(int age, DataSource data) {
        List<Person> result = new ArrayList<>();
        LocalDate today = LocalDate.now();
        for (int i = 0; i < data.getMedicalrecords().size(); i++) {
            if (data.getMedicalrecords().get(i).getBirthdate().isBefore(today.minusYears(age))) {
                result.add(data.getPersons().get(i));
            }
        }
        return result;
    }
}
