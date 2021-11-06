package com.openclassrooms.safetynet.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.safetynet.models.Medicalrecord;
import com.openclassrooms.safetynet.services.MedicalRecordService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = MedicalRecordController.class)
public class MedicalrecordControllerTest {

    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MedicalRecordService medicalRecordService;

    @Test
    public void saveTest() throws Exception {
        Medicalrecord medRec = new Medicalrecord("New", "MedRec", null, null, null);
        mockMvc.perform(post("/medicalRecord")
        .contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(medRec)))
        .andExpect(status().isOk())
        .andDo(print());
    }

    @Test
    public void SaveBadDataTest() throws Exception {
        // Method cannot accept a MedicalRecord without f/l name
        Medicalrecord medRecEmpty = new Medicalrecord("", "", null, null, null);
        mockMvc.perform(post("/medicalRecord")
        .contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(medRecEmpty)))
        .andExpect(status().isBadRequest())
        .andDo(print());

        Medicalrecord medRecNulls = new Medicalrecord(null, null, null, null, null);
        mockMvc.perform(post("/medicalRecord")
        .contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(medRecNulls)))
        .andExpect(status().isBadRequest())
        .andDo(print());
    }

    @Test
    public void deleteTest() throws Exception {
        Medicalrecord medRec = new Medicalrecord("New", "MedRec", null, null, null);
        mockMvc.perform(delete("/medicalRecord")
        .contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(medRec)))
        .andExpect(status().isOk())
        .andDo(print());
    }

    @Test
    public void deleteBadDataTest() throws Exception {
        // Method cannot accept a MedicalRecord without f/l name
        Medicalrecord medRecEmpty = new Medicalrecord("", "", null, null, null);
        mockMvc.perform(delete("/medicalRecord")
        .contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(medRecEmpty)))
        .andExpect(status().isBadRequest())
        .andDo(print());

        Medicalrecord medRecNulls = new Medicalrecord(null, null, null, null, null);
        mockMvc.perform(delete("/medicalRecord")
        .contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(medRecNulls)))
        .andExpect(status().isBadRequest())
        .andDo(print());
    }
}
