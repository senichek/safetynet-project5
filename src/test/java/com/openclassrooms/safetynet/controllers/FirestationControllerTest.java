package com.openclassrooms.safetynet.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.openclassrooms.safetynet.models.Firestation;
import com.openclassrooms.safetynet.services.FirestationService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = FirestationController.class)
public class FirestationControllerTest {

    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FirestationService firestationService;

    @Test
    public void saveTest() throws Exception {
        Firestation fStation = new Firestation("Street_name", 9);
        mockMvc.perform(post("/firestation")
        .contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(fStation)))
        .andExpect(status().isOk())
        .andDo(print());
    }

    @Test
    public void SaveBadDataTest() throws Exception {
        // Method cannot accept a Person without f/l name
        Firestation fStationEmpty = new Firestation("", 0);
        mockMvc.perform(post("/firestation")
        .contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(fStationEmpty)))
        .andExpect(status().isBadRequest())
        .andDo(print());
    }

    @Test
    public void deleteTest() throws Exception {
        Firestation fStation = new Firestation("Street", 5);
        mockMvc.perform(delete("/firestation")
        .contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(fStation)))
        .andExpect(status().isOk())
        .andDo(print());
    }

    @Test
    public void deleteBadDataTest() throws Exception {
        // Method cannot accept a Person without f/l name
        Firestation fStationEmpty = new Firestation("", 5);
        mockMvc.perform(delete("/firestation")
        .contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(fStationEmpty)))
        .andExpect(status().isBadRequest())
        .andDo(print());
    }

    @Test
    public void updateTest() throws Exception {
        ObjectNode objectNode = mapper.createObjectNode();
        objectNode.set("address", mapper.convertValue("Av. de Paris", JsonNode.class));
        objectNode.set("station", mapper.convertValue("5", JsonNode.class));
        objectNode.set("new_num", mapper.convertValue("6", JsonNode.class));

        mockMvc.perform(put("/firestation")
        .contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(objectNode)))
        .andExpect(status().isOk())
        .andDo(print());        
    }
}
