package com.application.bekend.controller;

import com.application.bekend.DTO.AddressDTO;
import com.application.bekend.DTO.BoatDTO;
import com.application.bekend.DTO.NavigationEquipmentDTO;
import com.application.bekend.constants.BoatConstants;
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

import static com.application.bekend.constants.BoatConstants.*;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BoatControllerTest {

    private static final String URL_PREFIX = "/api/boat";

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
    public void testGetNumberOfBoats() throws Exception {
        mockMvc.perform(get(URL_PREFIX + "/findAll")).andExpect(status().isOk())
                .andExpect(content().contentType(contentType)).andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    public void testGetBoatById() throws Exception {
        mockMvc.perform(get(URL_PREFIX + "/getBoatById/" + BoatConstants.integration_id)).andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.id").value(BoatConstants.integration_id.intValue()))
                .andExpect(jsonPath("$.name").value("Kruzer na Dunavu"));
//                .andExpect(jsonPath("$.grade").value(0))
//                .andExpect(jsonPath("$.pricePerDay").value(4000));
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testSaveBoat() throws Exception {
        // na frontu smo kreirali novi brod
        BoatDTO boat = new BoatDTO();
        boat.setName(new_name_boat);
        boat.setPromoDescription(new_promoDescription);
        boat.setGrade(new_grade);
        boat.setBehaviourRules(new_behaviourRules);
        boat.setPricePerDay(new_pricePerDay);
        boat.setCancalletionFee(new_cancalletionFee);
        boat.setCancalletionFree(new_isCancalletionFree);
        boat.setFishingEquipment(new_fishingEquipment);
        boat.setType(new_type);
        boat.setLength(new_length);
        boat.setEngineNumber(new_engineNumber);
        boat.setEnginePower(new_enginePower);
        boat.setCapacity(new_capacity);
        boat.setMaxSpeed(new_maxSpeed);
        boat.setServices(new HashSet<>());
        boat.setImages(new HashSet<>());
        boat.setAddress(new AddressDTO(null, "Balzakova", "Novi Sad", "Srbija", 0f,0f,21000));
        boat.setNavigationEquipmentDTO(new NavigationEquipmentDTO(null, true, true, true, true));

        // kreiran brod saljemo u kontroler
        String json = TestUtil.json(boat);
        String newURL = URL_PREFIX + "/add";
        this.mockMvc.perform(post(newURL).contentType(contentType).content(json)).andExpect(status().isCreated());
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testUpdateBoat() throws Exception {
        // dobavljen brod
        BoatDTO boat = new BoatDTO();
        boat.setId(1L);
        boat.setName("Kruzer na Dunavu");
        boat.setPromoDescription("Odlican brod");
        boat.setGrade(0);
        boat.setBehaviourRules("Nema alkohla na brodu");
        boat.setPricePerDay(400);
        boat.setCancalletionFee(2);
        boat.setCancalletionFree(false);
        boat.setFishingEquipment(new_fishingEquipment);
        boat.setType("Kruzer");
        boat.setLength(100);
        boat.setEngineNumber(45645);
        boat.setEnginePower(100);
        boat.setCapacity(10);
        boat.setMaxSpeed(20);
        boat.setServices(new HashSet<>());
        boat.setImages(new HashSet<>());
        boat.setNavigationEquipmentDTO(new NavigationEquipmentDTO(1L, true, true, false, false));
        boat.setAddress(new AddressDTO(7L, "Dok 16", "Beograd", "Srbija", 0f,0f,76300));

        String json = TestUtil.json(boat);
        String newURL = URL_PREFIX + "/edit/" + integration_id;
        this.mockMvc.perform(put(newURL).contentType(contentType).content(json)).andExpect(status().isOk());
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testDeleteBoat() throws Exception {
        this.mockMvc.perform(delete(URL_PREFIX + "/delete/" + 1L)).andExpect(status().isOk());
    }
}
