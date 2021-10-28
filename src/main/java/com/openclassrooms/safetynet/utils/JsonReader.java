package com.openclassrooms.safetynet.utils;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.openclassrooms.safetynet.models.DataSource;
import com.openclassrooms.safetynet.models.PersonFullDetails;

import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

public class JsonReader {
    public static DataSource getData(String pathToJson) throws IOException {
		JavaTimeModule module = new JavaTimeModule();

        LocalDateDeserializer localDateDeserializer = new LocalDateDeserializer(DateTimeFormatter.ofPattern("MM/dd/yyyy"));

        module.addDeserializer(LocalDate.class, localDateDeserializer);
    
        ObjectMapper objectMapperObj = Jackson2ObjectMapperBuilder.json().modules(module)
            .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS).build();

        URL url = new URL(pathToJson);
        DataSource dataSource = objectMapperObj.readValue(url, DataSource.class);

        // Copying all the details from Person and Medicalrecord to the FullDetailsPerson
        // so that all the data is in the same place.
        List<PersonFullDetails> personsFullDetails = new ArrayList<>();
        for (int i = 0; i < dataSource.getPersons().size(); i++) {
            PersonFullDetails pFullDetails = new PersonFullDetails();
            pFullDetails.setFirstName(dataSource.getPersons().get(i).getFirstName());
            pFullDetails.setLastName(dataSource.getPersons().get(i).getLastName());
            pFullDetails.setAddress(dataSource.getPersons().get(i).getAddress());
            pFullDetails.setCity(dataSource.getPersons().get(i).getCity());
            pFullDetails.setZip(dataSource.getPersons().get(i).getZip());
            pFullDetails.setPhone(dataSource.getPersons().get(i).getPhone());
            pFullDetails.setEmail(dataSource.getPersons().get(i).getEmail());
            
            // Copying birthdate
            pFullDetails.setBirthdate(dataSource.getMedicalrecords().get(i).getBirthdate());
            personsFullDetails.add(pFullDetails);
            
        }
        dataSource.setPersonFullDetails(personsFullDetails);
        
        // Setting up Firestations
        for (int i = 0; i < dataSource.getFirestations().size(); i++) {
            for (int j = 0; j < dataSource.getPersonFullDetails().size(); j++) {
                if (dataSource.getFirestations().get(i).getAddress().equals(dataSource.getPersonFullDetails().get(j).getAddress())) {
                    dataSource.getPersonFullDetails().get(j).setFirestationNumber(dataSource.getFirestations().get(i).getStation());
                }
            }
        }
        return dataSource;
    }
}
