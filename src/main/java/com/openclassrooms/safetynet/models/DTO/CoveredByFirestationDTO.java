package com.openclassrooms.safetynet.models.DTO;

import java.util.List;

import com.openclassrooms.safetynet.models.PersonFullDetails;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CoveredByFirestationDTO {
    private List<PersonFullDetails> persons;
    private int numberOfKids;
    private int numberOfAdults;
}
