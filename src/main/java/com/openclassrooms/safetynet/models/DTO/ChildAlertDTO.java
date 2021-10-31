package com.openclassrooms.safetynet.models.DTO;
import java.util.List;

import com.openclassrooms.safetynet.models.Person;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChildAlertDTO {
    private String firstName;
    private String lastName;
    private int age;
    private List<Person> membersOfHoushold;
}
