package com.openclassrooms.safetynet.models;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PersonFullDetailsTest {

    @Test
    public void hashEqualsTest() {

    PersonFullDetails prsA = new PersonFullDetails("Lily", "Cooper", "489 Manchester St", "Culver", "97451", 
    null, "lily@email.com", null, 27, new String[] {}, new String[] {}, null);
    
    PersonFullDetails prsB = new PersonFullDetails("Lily", "Cooper", "489 Manchester St", "Culver", "97451", 
    null, "lily@email.com", null, 27, new String[] {}, new String[] {}, null);
    
    assertTrue(prsA.equals(prsB) && prsB.equals(prsA));
    assertTrue(prsA.hashCode() == prsB.hashCode());
    }
}
