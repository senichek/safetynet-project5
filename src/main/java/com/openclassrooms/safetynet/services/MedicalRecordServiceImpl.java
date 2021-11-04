package com.openclassrooms.safetynet.services;

import java.util.Arrays;

import com.openclassrooms.safetynet.models.Medicalrecord;
import com.openclassrooms.safetynet.repos.DataRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;


@Service
@Log4j2
public class MedicalRecordServiceImpl implements MedicalRecordService {

    @Autowired
    DataRepo dataRepo;

    @Override
    public Medicalrecord save(Medicalrecord medicalrecord) {
        Boolean matchFound = false;
        Boolean changeDetected = false;
        Medicalrecord updated = new Medicalrecord();
        for (int i = 0; i < dataRepo.getData().getMedicalrecords().size(); i++) {
            if (dataRepo.getData().getMedicalrecords().get(i).getFirstName().equals(medicalrecord.getFirstName()) && 
                dataRepo.getData().getMedicalrecords().get(i).getLastName().equals(medicalrecord.getLastName())) {
                // Match found, now update the values.
                matchFound = true;
                updated = dataRepo.getData().getMedicalrecords().get(i);
                if (!updated.getBirthdate().isEqual(medicalrecord.getBirthdate())) {
                    changeDetected = true;
                    updated.setBirthdate(medicalrecord.getBirthdate());
                }
                if (!Arrays.equals(updated.getMedications(), medicalrecord.getMedications())) {
                    changeDetected = true;
                    updated.setMedications(medicalrecord.getMedications());
                }
                if (!Arrays.equals(updated.getAllergies(), medicalrecord.getAllergies())) {
                    changeDetected = true;
                    updated.setAllergies(medicalrecord.getAllergies());
                }
                if (changeDetected) {
                    dataRepo.getData().getMedicalrecords().set(i, updated);
                    log.info("Updated {}", updated);
                } else {
                    log.info("The entry is not updated because no changes detected.");
                }
            }
        }

        if (matchFound) {
            return updated;
        } else {
            dataRepo.getData().getMedicalrecords().add(medicalrecord);
            log.info("Created {}", medicalrecord);
            return medicalrecord;
        }
    }

    @Override
    public Medicalrecord delete(Medicalrecord medicalrecord) {
        Boolean matchFound = false;
        Medicalrecord deleted = new Medicalrecord();
        for (int i = 0; i < dataRepo.getData().getMedicalrecords().size(); i++) {
            if (dataRepo.getData().getMedicalrecords().get(i).getFirstName().equals(medicalrecord.getFirstName()) && 
                dataRepo.getData().getMedicalrecords().get(i).getLastName().equals(medicalrecord.getLastName())) {
                // Match found, delete
                matchFound = true;
                deleted = dataRepo.getData().getMedicalrecords().get(i);
                dataRepo.getData().getMedicalrecords().remove(deleted);
                log.info("Deleted {}", medicalrecord);
            }
        }
        if (matchFound) {
            return deleted;
        } else {
            return null;
        }
    }
}
