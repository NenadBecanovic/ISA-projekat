package com.application.bekend.controller;

import com.application.bekend.constants.HouseConstants;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.Charset;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HouseControllerTest {

    private static final String URL_PREFIX = "/api/house";

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype());

    /*
    Mocking mehanizam omogućuje simulaciju ponašanja objekata koje testirani objekat koristi
    Da bi se testirani objekat testirao u izolaciji važno je da referencirani objekti ne unose
    grešku. Potrebno je simulirati da referencirani objekti uvek rade ispravno.

    Programski poziv REST servisa se vrši putem Spring MockMvc klase.

    MockMvc simulira kompletnu Spring veb MVC arhitekturu.
    Nije mock objekat u ranije korišćenom značenju!
    Omogućuje stvarno, a ne lažno predefinisano ponašanje kao kod mock objekata koje koristi
    Mockito (najpopularniji java radni okvir za implementaciju mocking mehanizma).

     */
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testGetAllHouses() throws Exception {
        mockMvc.perform(get(URL_PREFIX + "/findAll")).andExpect(status().isOk())
                .andExpect(content().contentType(contentType)).andExpect(jsonPath("$", hasSize(3)));
//                .andExpect(jsonPath("$.[*].id").value(hasItem(4)))
//                .andExpect(jsonPath("$.[*].firstName").value(hasItem(DB_FIRST_NAME.toUpperCase())))
//                .andExpect(jsonPath("$.[*].lastName").value(hasItem(DB_LAST_NAME)))
//                .andExpect(jsonPath("$.[*].index").value(hasItem(DB_INDEX)));
    }

}
