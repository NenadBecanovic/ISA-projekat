package com.application.bekend.controller;

import static com.application.bekend.constants.HouseConstants.integration_id;
import static com.application.bekend.constants.HouseConstants.new_behaviourRules;
import static com.application.bekend.constants.HouseConstants.new_cancalletionFee;
import static com.application.bekend.constants.HouseConstants.new_grade;
import static com.application.bekend.constants.HouseConstants.new_isCancalletionFree;
import static com.application.bekend.constants.HouseConstants.new_name;
import static com.application.bekend.constants.HouseConstants.new_numberOfReviews;
import static com.application.bekend.constants.HouseConstants.new_pricePerDay;
import static com.application.bekend.constants.HouseConstants.new_promoDescription;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashSet;

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

import com.application.bekend.DTO.AddressDTO;
import com.application.bekend.DTO.FishingAdventureDTO;
import com.application.bekend.constants.FishingAdventureConstants;
import com.application.bekend.constants.HouseConstants;
import com.application.bekend.model.Address;
import com.application.bekend.model.FishingAdventure;
import com.application.bekend.model.House;
import com.application.bekend.model.MyUser;
import com.application.bekend.util.TestUtil;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FishingAdventureControllerTest {

    private static final String URL_PREFIX = "/api/fishingAdventure";

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
    @Transactional
    @Rollback(true)
    public void testSaveAdventure() throws Exception {
        // na frontu smo kreirali novu vikendicu
        FishingAdventureDTO adventure = new FishingAdventureDTO();
        adventure.setId(1L);
        adventure.setName(new_name);
        adventure.setPromoDescription(new_promoDescription);
        adventure.setGrade(new_grade);
        adventure.setNumberOfReviews(new_numberOfReviews);
        adventure.setBehaviourRules(new_behaviourRules);
        adventure.setPricePerHour(FishingAdventureConstants.new_pricePerHour);
        adventure.setCancellationFee(new_cancalletionFee);
        adventure.setCancellationFree(new_isCancalletionFree);
        adventure.setAdditionalServices(new HashSet<>());
        adventure.setAddress(new AddressDTO(null, "Balzakova", "Novi Sad", "Srbija", 0f,0f,21000));
        adventure.setInstructorId(1L);

        // kreiranu vikendicu saljemo u kontroler
        String json = TestUtil.json(adventure);
        String newURL = URL_PREFIX + "/add";
        this.mockMvc.perform(post(newURL).contentType(contentType).content(json)).andExpect(status().isCreated());
    }

    @Test
    public void testGetFishingAdventure() throws Exception {
        mockMvc.perform(get(URL_PREFIX + "/getFishingAdventureById/" + 1L)).andExpect(status().isOk());
    }
    /*
    @Test
    @Transactional
    @Rollback(true)
    public void testDeleteFishingAdventure() throws Exception {
        this.mockMvc.perform(delete(URL_PREFIX + "/delete/" + 1L)).andExpect(status().isOk());
    }*/
}
