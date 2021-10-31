package com.openclassrooms.safetynet.models;

import java.time.LocalDate;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Medicalrecord {
    private String firstName;
    private String lastName;
    private LocalDate birthdate;
    private String[] medications;
    private String[] allergies;
}
