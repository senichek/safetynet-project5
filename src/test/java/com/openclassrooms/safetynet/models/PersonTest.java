package com.openclassrooms.safetynet.models;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PersonTest {

    @Test
    public void hashEqualsTest() {
    
    Person prsA = new Person("John", "Smith", "Street", "City", "95000", "555-777-888", "john@mail.com");
    
    Person prsB = new Person("John", "Smith", "Street", "City", "95000", "555-777-888", "john@mail.com");
    
    assertTrue(prsA.equals(prsB) && prsB.equals(prsA));
    assertTrue(prsA.hashCode() == prsB.hashCode());
    }
}
