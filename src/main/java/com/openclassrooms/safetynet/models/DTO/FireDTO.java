package com.openclassrooms.safetynet.models.DTO;

import java.util.List;
import java.util.Set;

import com.openclassrooms.safetynet.models.PersonFullDetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FireDTO {
    private List<PersonFullDetails> persons;
    private Set<Integer> fireStationNumbers;
}
