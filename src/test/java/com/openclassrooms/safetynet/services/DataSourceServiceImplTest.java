package com.openclassrooms.safetynet.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.openclassrooms.safetynet.TestData;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DataSourceServiceImplTest {

    @Autowired
    DataSourceService dataSourceService;

    @Test
    public void getAllPersonsByFirestationTest() {
        assertEquals(TestData.coveredByFirestation_2, dataSourceService.getAllPeopleByFirestation(2).getPersons());
    }

    @Test
    public void getChildrenByAddressTest() {
        assertEquals(TestData.kids, dataSourceService.getChildrenByAddress("947 E. Rose Dr"));
    }

    @Test
    public void getAllByAddressInCaseOfFireTest() {
        assertEquals(TestData.fire_data, dataSourceService.getAllByAddressInCaseOfFire("112 Steppes Pl"));
    }

    @Test
    public void getPhonesByFirestationTest() {
        assertEquals(TestData.phonesCoverredByFirestation_2, dataSourceService.getPhonesByFirestation(2));
    }

    @Test
    public void getAllByAddressFireTest() {
        assertEquals(TestData.fire_data, dataSourceService.getAllByAddressInCaseOfFire("112 Steppes Pl"));
    }

}
