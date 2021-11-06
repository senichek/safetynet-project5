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
        for (int i = 0; i < dataRepo.getData().getFirestations().size(); i++) {
            if (dataRepo.getData().getFirestations().get(i).getAddress().equals(firestation.getAddress()) && 
                dataRepo.getData().getFirestations().get(i).getStation() == firestation.getStation()) {
                    matchFound = true;
                    log.info("Entry exists {}", firestation);
            }
        }
        if (matchFound) {
            return null;
        } else {
            dataRepo.getData().getFirestations().add(firestation);
            log.info("Created {}", firestation);
            return firestation;
        }
    }

    @Override
    public Firestation update(String address, int currentNum, int newNum) {
        Boolean matchFound = false;
        Firestation updated = new Firestation();
        for (int i = 0; i < dataRepo.getData().getFirestations().size(); i++) {
            if (dataRepo.getData().getFirestations().get(i).getAddress().equals(address)
                    && dataRepo.getData().getFirestations().get(i).getStation() == currentNum) {
                dataRepo.getData().getFirestations().get(i).setStation(newNum);
                updated = dataRepo.getData().getFirestations().get(i);
                matchFound = true;
                log.info("Updated {}", updated);
            }
        }
        if (matchFound) {
            return updated;
        } else {
            log.info("Update was not done. Entry with address {} and stationNumber {} was not found", address, currentNum);
            return null;
        }
    }

    @Override
    public Firestation delete(Firestation firestation) {
        Boolean matchFound = false;
        Firestation deleted = new Firestation();
        for (int i = 0; i < dataRepo.getData().getFirestations().size(); i++) {
            if (dataRepo.getData().getFirestations().get(i).getAddress().equals(firestation.getAddress())
                    && dataRepo.getData().getFirestations().get(i).getStation() == firestation.getStation()) {
                deleted = dataRepo.getData().getFirestations().get(i);
                dataRepo.getData().getFirestations().remove(i);
                matchFound = true;
                log.info("Deleted {}", deleted);
            }
        }
        if (matchFound) {
            return deleted;
        } else {
            log.info("Not deleted. Entry {} was not found", firestation);
            return null;
        }
    }
}
