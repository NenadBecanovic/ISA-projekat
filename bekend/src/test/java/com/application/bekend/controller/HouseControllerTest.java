package com.application.bekend.controller;

import com.application.bekend.constants.HouseConstants;
import com.application.bekend.model.Address;
import com.application.bekend.model.House;
import com.application.bekend.util.TestUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashSet;

import static com.application.bekend.constants.HouseConstants.*;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
    public void testGetNumberOfHouses() throws Exception {
        mockMvc.perform(get(URL_PREFIX + "/findAll")).andExpect(status().isOk())
                .andExpect(content().contentType(contentType)).andExpect(jsonPath("$", hasSize(4)));
//        		.andExpect(jsonPath("$.[*].id").value(hasItem(4)));
    }

    @Test
    public void testGetHouseById() throws Exception {
        mockMvc.perform(get(URL_PREFIX + "/getHouseById/" + HouseConstants.integration_id)).andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.id").value(HouseConstants.integration_id.intValue()))
                .andExpect(jsonPath("$.name").value("Vikendica na Kopaoniku"));
//                .andExpect(jsonPath("$.grade").value(0))
//                .andExpect(jsonPath("$.promoDescription").value("Najbolja vikendica na Tari. Ocenjena sa 5 zvezdica od svakog klijenta koji je poseti!"))
//                .andExpect(jsonPath("$.behaviourRules").value("Zabranjeno pusenje."))
//                .andExpect(jsonPath("$.pricePerDay").value(2000));
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testSaveHouse() throws Exception {
        // na frontu smo kreirali novu vikendicu
        House house = new House();
        house.setName(new_name);
        house.setPromoDescription(new_promoDescription);
        house.setGrade(new_grade);
        house.setNumberOfReviews(new_numberOfReviews);
        house.setBehaviourRules(new_behaviourRules);
        house.setPricePerDay(new_pricePerDay);
        house.setCancalletionFee(new_cancalletionFee);
        house.setCancalletionFree(new_isCancalletionFree);
        house.setCourses(new HashSet<>());
        house.setServices(new HashSet<>());
        house.setRooms(new HashSet<>());
        house.setImages(new HashSet<>());
        house.setAddress(new Address(null, "Balzakova", "Novi Sad", "Srbija", 0f,0f,21000));
        house.setAppeals(new HashSet<>());
        house.setFeedbacks(new HashSet<>());

        // kreiranu vikendicu saljemo u kontroler
        String json = TestUtil.json(house);
        String newURL = URL_PREFIX + "/add";
        this.mockMvc.perform(post(newURL).contentType(contentType).content(json)).andExpect(status().isCreated());
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testUpdateHouse() throws Exception {
        // dobavljena vikendica
        House house = new House();
        house.setId(1L);        // id postojece kuce koja se moze izmeniti
        house.setName(new_name);
        house.setPromoDescription(new_promoDescription);
        house.setGrade(new_grade);
        house.setNumberOfReviews(new_numberOfReviews);
        house.setBehaviourRules(new_behaviourRules);
        house.setPricePerDay(new_pricePerDay);
        house.setCancalletionFee(new_cancalletionFee);
        house.setCancalletionFree(new_isCancalletionFree);
        house.setCourses(new HashSet<>());
        house.setServices(new HashSet<>());
        house.setRooms(new HashSet<>());
        house.setImages(new HashSet<>());
        house.setAddress(new Address(28L, "Balzakova", "Novi Sad", "Srbija", 0f,0f,21000));
        house.setAppeals(new HashSet<>());
        house.setFeedbacks(new HashSet<>());

        String json = TestUtil.json(house);
        String newURL = URL_PREFIX + "/edit/" + integration_id;
        this.mockMvc.perform(put(newURL).contentType(contentType).content(json)).andExpect(status().isOk());
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testDeleteHouse() throws Exception {
        this.mockMvc.perform(delete(URL_PREFIX + "/delete/" + 1L)).andExpect(status().isOk());
    }
}
