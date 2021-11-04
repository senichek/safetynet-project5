package com.openclassrooms.safetynet.services;

import com.openclassrooms.safetynet.models.Firestation;
import com.openclassrooms.safetynet.repos.DataRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;


@Service
@Log4j2
public class FirestationServiceImpl implements FirestationService {

    @Autowired
    DataRepo dataRepo;

    @Override
    public Firestation save(Firestation firestation) {
        Boolean matchFound = false;
        Firestation updated = new Firestation();
        for (int i = 0; i < dataRepo.getData().getFirestations().size(); i++) {
            if (dataRepo.getData().getFirestations().get(i).getAddress().equals(firestation.getAddress())) {
                
        }
    }

        if (matchFound) {
            return updated;
        } else {
            dataRepo.getData().getFirestations().add(firestation);
            log.info("Created {}", firestation);
            return firestation;
        }
    }

    @Override
    public Firestation delete(Firestation firestation) {
        // TODO Auto-generated method stub
        return null;
    }

}
