package com.openclassrooms.safetynet.services;

import java.util.List;

import com.openclassrooms.safetynet.models.DataSource;
import com.openclassrooms.safetynet.models.Medicalrecord;
import com.openclassrooms.safetynet.models.Person;
import com.openclassrooms.safetynet.models.DTO.CoveredByFirestationDTO;

public interface DataSourceService {
    DataSource getAllData();
    CoveredByFirestationDTO getAllPersonsByFirestation(int num);
    List<Person> getAllYoungerThan(int age, DataSource data);
    
}
