package com.application.bekend.controller;

import com.application.bekend.DTO.AppealDTO;
import com.application.bekend.constants.ReservationContstants;
import com.application.bekend.model.Address;
import com.application.bekend.model.House;
import com.application.bekend.util.TestUtil;
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

import java.util.HashSet;

import static com.application.bekend.constants.HouseConstants.*;
import static com.application.bekend.constants.HouseConstants.new_isCancalletionFree;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AppealControllerTest {

    private static final String URL_PREFIX = "/api/appeal";

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
    public void testSaveAppeal() throws Exception {
        AppealDTO appealDTO = new AppealDTO();
        appealDTO.setReview("Bilo je mnogo lose");
        appealDTO.setReservationId(1L);
        appealDTO.setOwnerId(1L);
        appealDTO.setSenderId(2L);
        appealDTO.setHasHouse(false);
        appealDTO.setHasBoat(false);
        appealDTO.setHasBoatOwner(false);
        appealDTO.setHasHouseOwner(false);
        appealDTO.setIsAnswered(false);
        // kreiranu vikendicu saljemo u kontroler
        String json = TestUtil.json(appealDTO);
        String newURL = URL_PREFIX + "/saveAppeal";
        this.mockMvc.perform(post(newURL).contentType(contentType).content(json)).andExpect(status().isBadRequest());
    }
}
