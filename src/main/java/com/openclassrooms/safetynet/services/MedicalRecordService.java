package com.openclassrooms.safetynet.services;

import com.openclassrooms.safetynet.models.Medicalrecord;

public interface MedicalRecordService {
    Medicalrecord save(Medicalrecord medicalrecord);
    Medicalrecord delete(Medicalrecord medicalrecord);
}
