package com.openclassrooms.safetynet.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import com.openclassrooms.safetynet.TestData;
import com.openclassrooms.safetynet.models.PersonFullDetails;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class URLsServiceImplTest {

    @Autowired
    URLsService urlService;

    @Test
    public void getAllPersonsByFirestationTest() {
        assertEquals(TestData.coveredByFirestation_2, urlService.getAllPeopleByFirestation(2).getPersons());
    }

    @Test
    public void getChildrenByAddressTest() {
        assertEquals(TestData.kids, urlService.getChildrenByAddress("947 E. Rose Dr"));
    }

    @Test
    public void getAllByAddressInCaseOfFireTest() {
        assertEquals(TestData.fire_data, urlService.getAllByAddressInCaseOfFire("112 Steppes Pl"));
    }

    @Test
    public void getPhonesByFirestationTest() {
        assertEquals(TestData.phonesCoverredByFirestation_2, urlService.getPhonesByFirestation(2));
    }

    @Test
    public void getAllByAddressFireTest() {
        assertEquals(TestData.fire_data, urlService.getAllByAddressInCaseOfFire("112 Steppes Pl"));
    }

    @Test
    public void getAllByFireStationNumberFloodTest() {
        assertEquals(TestData.floodDTOs, urlService.getAllByFireStationNumberFlood(Set.of(4)));
    }

    @Test
    public void getAllByFirstNameAndLastNameTest() {
        List<PersonFullDetails> Tony = List.of(TestData.Tony_Cooper);
        List<PersonFullDetails> Lily = List.of(TestData.Lily_Cooper);
        assertEquals(Tony, urlService.getAllByFirstNameAndLastName("Tony", "Cooper"));
        assertEquals(Lily, urlService.getAllByFirstNameAndLastName("Lily", "Cooper"));
    }

    @Test
    public void getAllByFirstNameAndLastNameOneParamTest() {
        List<PersonFullDetails> Tony = List.of(TestData.Tony_Cooper);
        assertEquals(Tony, urlService.getAllByFirstNameAndLastName("Tony", ""));
        assertEquals(TestData.coopers, urlService.getAllByFirstNameAndLastName("", "Cooper"));
    }

    @Test
    public void getAllByFirstNameAndLastNameNoParamsTest() {
        //If there are no parameters the full list of 23 elements is returned.
        assertEquals(23, urlService.getAllByFirstNameAndLastName("", "").size());
        // And here we check just a couple of entries to make sure the data inside of the list is correct.
        // Tony is located at Index 9 and Lily is located at Index 10.
        assertEquals(TestData.Tony_Cooper, urlService.getAllByFirstNameAndLastName("", "").get(9));
        assertEquals(TestData.Lily_Cooper, urlService.getAllByFirstNameAndLastName("", "").get(10));
    }

    @Test
    public void getEmailsByCityNameTest() {
        assertEquals(TestData.emails, urlService.getEmailsByCityName("Culver"));
        assertEquals(null, urlService.getEmailsByCityName(""));
        assertEquals(0, urlService.getEmailsByCityName("NonExistant").size());
    }

    @Test
    public void getFirestationsByAddressTest() {
        assertEquals(TestData.firestations, urlService.getFirestationsByAddress("112 Steppes Pl"));
    }

    @Test
    public void getBirthdateByNameTest() {
        //LocalDate expected = LocalDate.of(1975, 12, 6);
        assertEquals(LocalDate.of(1975, 12, 6), urlService.getBirthdateByName("Brian", "Stelzer"));
    }
}
