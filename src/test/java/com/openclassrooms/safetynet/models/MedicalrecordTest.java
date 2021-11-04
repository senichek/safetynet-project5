package com.openclassrooms.safetynet.models;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MedicalrecordTest {

    @Test
    public void hashEqualsTest() {

    Medicalrecord medRecA = new Medicalrecord("New", "New", LocalDate.of(2005, 8, 30),
    new String[] { "hydrapermazol:300mg", "dodoxadin:30mg" }, new String[] { "shellfish" });

    Medicalrecord medRecB = new Medicalrecord("New", "New", LocalDate.of(2005, 8, 30),
    new String[] { "hydrapermazol:300mg", "dodoxadin:30mg" }, new String[] { "shellfish" });

    assertTrue(medRecA.equals(medRecB) && medRecB.equals(medRecA));
    assertTrue(medRecA.hashCode() == medRecB.hashCode());
    }
}
