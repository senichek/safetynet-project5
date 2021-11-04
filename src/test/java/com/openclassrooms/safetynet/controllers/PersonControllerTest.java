package com.openclassrooms.safetynet.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.safetynet.models.Person;
import com.openclassrooms.safetynet.services.PersonService;

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

@WebMvcTest(controllers = PersonController.class)
public class PersonControllerTest {

    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    PersonService personService;

    @Test
    public void saveTest() throws Exception {
        Person prs = new Person("NewPrs", "NewPrs", null, null, null, null, null);
        mockMvc.perform(post("/person")
        .contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(prs)))
        .andExpect(status().isOk())
        .andDo(print());
    }

    @Test
    public void SaveBadDataTest() throws Exception {
        // Method cannot accept a Person without f/l name
        Person prsEmpty = new Person("", "", null, null, null, null, null);
        mockMvc.perform(post("/person")
        .contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(prsEmpty)))
        .andExpect(status().isBadRequest())
        .andDo(print());

        Person prsNulls = new Person(null, null, null, null, null, null, null);
        mockMvc.perform(post("/person")
        .contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(prsNulls)))
        .andExpect(status().isBadRequest())
        .andDo(print());
    }

    @Test
    public void deleteTest() throws Exception {
        Person prs = new Person("NewPrs", "NewPrs", null, null, null, null, null);
        mockMvc.perform(delete("/person")
        .contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(prs)))
        .andExpect(status().isOk())
        .andDo(print());
    }

    @Test
    public void deleteBadDataTest() throws Exception {
        // Method cannot accept a Person without f/l name
        Person prsEmpty = new Person("", "", null, null, null, null, null);
        mockMvc.perform(delete("/person")
        .contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(prsEmpty)))
        .andExpect(status().isBadRequest())
        .andDo(print());

        Person prsNulls = new Person(null, null, null, null, null, null, null);
        mockMvc.perform(delete("/person")
        .contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(prsNulls)))
        .andExpect(status().isBadRequest())
        .andDo(print());
    }
}
