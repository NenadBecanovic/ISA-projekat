package com.application.bekend.controller;

import com.application.bekend.DTO.ActionDTO;
import com.application.bekend.DTO.ReservationCancelDTO;
import com.application.bekend.constants.MyUserConstants;
import com.application.bekend.constants.ReservationContstants;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.context.WebApplicationContext;

import javax.transaction.Transactional;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class MyUserControllerTest {

    private static final String URL_PREFIX = "/api/user";

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
    public void findAllSubscriptionsByUserId() throws Exception {
        mockMvc.perform(get(URL_PREFIX + "/findAllSubscriptionsByUserId/" + MyUserConstants.myUserId.intValue())).andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(content().contentType(contentType)).andExpect(jsonPath("$", hasSize(1)));

    }

}
