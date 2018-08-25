package com.example.demo.Controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PartnerMappingControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void getPartnersByCustomerId() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/customers/1/partners").accept(MediaType.APPLICATION_JSON)
                .header(AUTHORIZATION, "Bearer1"))
                .andExpect(status().isOk());
    }

    @Test
    public void getPartnersOfOtherCustomer() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/customers/2/partners").accept(MediaType.APPLICATION_JSON)
                .header(AUTHORIZATION, "Bearer1"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void deletePartner() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/customers/1/partners/1").accept(MediaType.APPLICATION_JSON)
                .header(AUTHORIZATION, "Bearer1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deletePartnerOfOtherCustomer() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/customers/2/partners/1").accept(MediaType.APPLICATION_JSON)
                .header(AUTHORIZATION, "Bearer1"))
                .andExpect(status().isUnauthorized());
    }
}
