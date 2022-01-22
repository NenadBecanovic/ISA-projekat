package com.application.bekend.controller;

import static com.application.bekend.constants.HouseConstants.new_behaviourRules;
import static com.application.bekend.constants.HouseConstants.new_cancalletionFee;
import static com.application.bekend.constants.HouseConstants.new_grade;
import static com.application.bekend.constants.HouseConstants.new_isCancalletionFree;
import static com.application.bekend.constants.HouseConstants.new_name;
import static com.application.bekend.constants.HouseConstants.new_numberOfReviews;
import static com.application.bekend.constants.HouseConstants.new_promoDescription;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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

import com.application.bekend.DTO.AdditionalServicesDTO;
import com.application.bekend.DTO.AddressDTO;
import com.application.bekend.DTO.FishingAdventureDTO;
import com.application.bekend.constants.FishingAdventureConstants;
import com.application.bekend.util.TestUtil;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FishingAdventureControllerTest {

    private static final String URL_PREFIX = "/api/fishingAdventure";

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype());

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

        String json = TestUtil.json(adventure);
        String newURL = URL_PREFIX + "/add";
        this.mockMvc.perform(post(newURL).contentType(contentType).content(json)).andExpect(status().isCreated());
    }

    @Test
    public void testGetFishingAdventure() throws Exception {
        mockMvc.perform(get(URL_PREFIX + "/getFishingAdventureById/" + 1L)).andExpect(status().isOk());
    }
    
    @Test
    @Transactional
    @Rollback(true)
    public void testAddAdditionalService() throws Exception {
        AdditionalServicesDTO additionalService = new AdditionalServicesDTO();
        additionalService.setId(1L);
        additionalService.setAdventureId(1L);
        additionalService.setName("Stapovi");
        additionalService.setPrice(3000);
        String json = TestUtil.json(additionalService);
        String newURL = URL_PREFIX + "/addAdditionalService";
        this.mockMvc.perform(post(newURL).contentType(contentType).content(json)).andExpect(status().isCreated());
    }
    
    @Test
    public void testGetFishingAdventureInstructor() throws Exception {
        mockMvc.perform(get(URL_PREFIX + "/getFishingAdventureInstructor/" + 1L)).andExpect(status().isOk());
    }
}
