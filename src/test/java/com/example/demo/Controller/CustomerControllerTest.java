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
public class CustomerControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void whenCustomerGetHimselfById() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/customers/1").accept(MediaType.APPLICATION_JSON)
                .header(AUTHORIZATION, "Bearer1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void whenCustomerGetHimselfByMe() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/customers/@me").accept(MediaType.APPLICATION_JSON)
                .header(AUTHORIZATION, "Bearer1"))
                .andExpect(status().isNotFound());
    }

}
