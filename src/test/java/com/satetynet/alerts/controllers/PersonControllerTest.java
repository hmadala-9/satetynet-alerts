package com.satetynet.alerts.controllers;

import com.satetynet.alerts.service.PersonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PersonController.class)
public class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PersonService personService;

    @Test
    void testAddPerson() throws Exception {
        String requestJson = """
        {
            "firstName": "Lebron",
            "lastName": "James",
            "address": "123 Main",
            "city": "Akron",
            "zip": "75203",
            "phone": "123-234-2345",
            "email": "lebron.james@gmail.com"
        }
        """;

        mockMvc.perform(post("/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().string("Person added successfully"));
    }

    @Test
    void testUpdatePerson() throws Exception {
        String updatedJson = """
        {
            "firstName": "Lebron",
            "lastName": "James",
            "address": "234 Main",
            "city": "Cleveland",
            "zip": "75203",
            "phone": "123-234-2345",
            "email": "lebron.james@gmail.com"
        }
        """;

        mockMvc.perform(put("/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedJson))
                .andExpect(status().isOk())
                .andExpect(content().string("Person updated successfully"));
    }

    @Test
    void testDeletePerson() throws Exception {
        mockMvc.perform(delete("/person")
                        .param("firstName", "Lebron")
                        .param("lastName", "James"))
                .andExpect(status().isOk())
                .andExpect(content().string("Person deleted successfully"));
    }
}
