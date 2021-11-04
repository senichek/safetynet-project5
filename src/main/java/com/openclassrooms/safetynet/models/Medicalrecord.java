package com.openclassrooms.safetynet.models;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Medicalrecord {
    private String firstName;
    private String lastName;
    private LocalDate birthdate;
    private String[] medications;
    private String[] allergies;
}
