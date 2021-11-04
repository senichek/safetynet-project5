package com.openclassrooms.safetynet.services;

import com.openclassrooms.safetynet.models.Firestation;

public interface FirestationService {
    Firestation save(Firestation firestation);
    Firestation delete(Firestation firestation);
}
