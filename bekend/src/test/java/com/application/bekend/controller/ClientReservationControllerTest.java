package com.application.bekend.controller;

import com.application.bekend.DTO.*;
import com.application.bekend.constants.FishingAdventureConstants;
import com.application.bekend.constants.ReservationContstants;
import com.application.bekend.model.HouseReservation;
import com.application.bekend.util.TestUtil;
import com.application.bekend.constants.ReservationContstants;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.context.WebApplicationContext;

import java.util.Date;
import java.util.HashSet;

import static com.application.bekend.constants.HouseConstants.*;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ClientReservationControllerTest {

    private static final String URL_PREFIX = "/api/user/reservation";
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
    public void addHouseReservationClient() throws Exception {
        HouseReservationDTO houseReservationDTO = new HouseReservationDTO();
        ReservationCheckDTO dto = new ReservationCheckDTO();
        houseReservationDTO.setMilisStartDate(1646313630000L);
        houseReservationDTO.setMilisEndDate(1647350430000L);
        houseReservationDTO.setMaxGuests(4);
        houseReservationDTO.setAvailable(false);
        houseReservationDTO.setCancelled(false);
        houseReservationDTO.setAction(false);
        houseReservationDTO.setHouseId(1L);
        houseReservationDTO.setGuestId(2L);
        houseReservationDTO.setAdditionalServices(new HashSet<>());
        String json = TestUtil.json(houseReservationDTO);
        String newURL = URL_PREFIX + "/house";
        this.mockMvc.perform(post(newURL).contentType(contentType).content(json)).andExpect(status().isOk())
                .andExpect(content().string("true"));
    }


    @Test
    @Transactional
    @Rollback(true)
    public void addBoatReservationClient() throws Exception {
        BoatReservationDTO boatReservationDTO = new BoatReservationDTO();
        boatReservationDTO.setMilisStartDate(1646313630000L);
        boatReservationDTO.setMilisEndDate(1647350430000L);
        boatReservationDTO.setMaxGuests(4);
        boatReservationDTO.setAvailable(false);
        boatReservationDTO.setCancelled(false);
        boatReservationDTO.setAction(false);
        boatReservationDTO.setBoatId(1L);
        boatReservationDTO.setGuestId(2L);
        boatReservationDTO.setAdditionalServices(new HashSet<>());
        String json = TestUtil.json(boatReservationDTO);
        String newURL = URL_PREFIX + "/boat";
        this.mockMvc.perform(post(newURL).contentType(contentType).content(json)).andExpect(status().isOk())
                .andExpect(content().string("true"));
    }


    @Test
    @Transactional
    @Rollback(true)
    public void addAdventureReservationClient() throws Exception {
        AdventureReservationDTO adventureReservationDTO = new AdventureReservationDTO();
        adventureReservationDTO.setMilisStartDate(1646313630000L);
        adventureReservationDTO.setMilisEndDate(1647350430000L);
        adventureReservationDTO.setMaxGuests(4);
        adventureReservationDTO.setAvailabilityPeriod(false);
        adventureReservationDTO.setCancelled(false);
        adventureReservationDTO.setIsAction(false);
        adventureReservationDTO.setAdventureId(1L);
        adventureReservationDTO.setGuestId(2L);
        adventureReservationDTO.setAdditionalServices(new HashSet<>());
        String json = TestUtil.json(adventureReservationDTO);
        String newURL = URL_PREFIX + "/adventure";
        this.mockMvc.perform(post(newURL).contentType(contentType).content(json)).andExpect(status().isOk())
                .andExpect(content().string("false"));
    }

    @Test
    @Transactional
    @Rollback(true)
    public void addActionHouseClient() throws Exception {
        ActionDTO actionDTO = new ActionDTO();
        actionDTO.setUserId(1L);
        actionDTO.setEntityId(17L);
        String json = TestUtil.json(actionDTO);
        String newURL = URL_PREFIX + "/action/house";
        this.mockMvc.perform(post(newURL).contentType(contentType).content(json)).andExpect(status().isOk())
                .andExpect(content().string("true"));
    }


    @Test
    @Transactional
    @Rollback(true)
    public void addActionBoatClient() throws Exception {
        ActionDTO actionDTO = new ActionDTO();
        actionDTO.setUserId(1L);
        actionDTO.setEntityId(17L);
        String json = TestUtil.json(actionDTO);
        String newURL = URL_PREFIX + "/action/boat";
        this.mockMvc.perform(post(newURL).contentType(contentType).content(json)).andExpect(status().isOk())
                .andExpect(content().string("false"));
    }


    @Test
    @Transactional
    @Rollback(true)
    public void addActionAdventureBoatClient() throws Exception {
        ActionDTO actionDTO = new ActionDTO();
        actionDTO.setUserId(1L);
        actionDTO.setEntityId(17L);
        String json = TestUtil.json(actionDTO);
        String newURL = URL_PREFIX + "/action/adventure";
        this.mockMvc.perform(post(newURL).contentType(contentType).content(json)).andExpect(status().isOk())
                .andExpect(content().string("false"));
    }

}

// =======
//     public void testGetHouseReservationByHouseId() throws Exception {
//         mockMvc.perform(get(URL_PREFIX + "/house/" + ReservationContstants.guestid.intValue())).andExpect(status().isOk())
//                 .andExpect(content().contentType(contentType))
//                 .andExpect(content().string("true"));

//     }
// }
// >>>>>>> develop
