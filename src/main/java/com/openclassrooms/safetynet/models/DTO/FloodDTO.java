package com.openclassrooms.safetynet.models.DTO;

import java.util.List;

import com.openclassrooms.safetynet.models.PersonFullDetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FloodDTO {
    private String address;
    private List<PersonFullDetails> people;
}
