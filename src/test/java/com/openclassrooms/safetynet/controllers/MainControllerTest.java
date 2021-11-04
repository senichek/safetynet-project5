package com.openclassrooms.safetynet.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Set;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.when;

import com.openclassrooms.safetynet.TestData;
import com.openclassrooms.safetynet.models.DTO.FireDTO;
import com.openclassrooms.safetynet.services.URLsService;


@WebMvcTest(controllers = MainController.class)
public class MainControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private URLsService urlService;

    @Autowired
    MainController mainController;

    @Test
    public void getAllCoveredByFirestationTest() throws Exception {
        mockMvc.perform(get("/firestation?stationNumber=4"))
        .andExpect(status().isOk())
        .andDo(print());

        mockMvc.perform(get("/firestation?stationNumber="))
        .andExpect(status().isNoContent());
    }

    @Test
    public void getChildrenByAddressTest() throws Exception {
        mockMvc.perform(get("/childAlert?address=1509 Culver St"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andDo(print());

        mockMvc.perform(get("/childAlert?address="))
        .andExpect(status().isNoContent())
        .andDo(print());
    }

    @Test
    public void getPhonesByFirestationTest() throws Exception {
        mockMvc.perform(get("/phoneAlert?firestation=3"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andDo(print());

        mockMvc.perform(get("/phoneAlert?firestation="))
        .andExpect(status().isNoContent())
        .andDo(print());
    }

    @Test
    public void getAllByAddressFireTest() throws Exception {
        when(urlService.getAllByAddressInCaseOfFire("112 Steppes Pl")).thenReturn(new FireDTO());
        
        mockMvc.perform(get("/fire?address=112 Steppes Pl"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andDo(print());

        mockMvc.perform(get("/fire?address="))
        .andExpect(status().isNoContent())
        .andDo(print());
    }

    @Test
    public void getAllByFireStationNumberFloodTest() throws Exception {
        mockMvc.perform(get("/flood/stations?stations=4"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andDo(print());

        mockMvc.perform(get("/flood/stations?stations="))
        .andExpect(status().isNoContent())
        .andDo(print());
    }

    @Test
    public void getAllByFirstNameAndLastNameTest() throws Exception {
        when(urlService.getAllByFirstNameAndLastName("", "Cooper")).thenReturn(List.of(TestData.Tony_Cooper, TestData.Lily_Cooper));

        mockMvc.perform(get("/personInfo?firstName=&lastName=Cooper"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andDo(print());

        mockMvc.perform(get("/personInfo?firstName=&lastName="))
        .andExpect(status().isNoContent())
        .andDo(print());
    }

    @Test
    public void getEmailsByCityNameTest() throws Exception {
        when(urlService.getEmailsByCityName("Culver")).thenReturn(Set.of("test@email.com"));
        
        mockMvc.perform(get("/communityEmail?city=Culver"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andDo(print());

        mockMvc.perform(get("/communityEmail?city="))
        .andExpect(status().isNoContent())
        .andDo(print());
    }
}
