package com.openclassrooms.safetynet.services;

import com.openclassrooms.safetynet.models.Firestation;

public interface FirestationService {
    Firestation save(Firestation firestation);
    Firestation update(String address, int currentNum, int newNum);
    Firestation delete(Firestation firestation);
}
