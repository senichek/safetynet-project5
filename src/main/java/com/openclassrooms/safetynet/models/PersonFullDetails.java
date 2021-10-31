package com.openclassrooms.safetynet.models;

import java.time.LocalDate;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonFullDetails {
    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String zip;
    private String phone;
    private String email;
    private LocalDate birthdate;
    private Integer age;
    private String[] medications;
    private String[] allergies;
    private Set<Firestation> firestations;
}
