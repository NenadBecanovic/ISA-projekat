package com.application.bekend.controller;

import static com.application.bekend.constants.HouseConstants.new_behaviourRules;
import static com.application.bekend.constants.HouseConstants.new_cancalletionFee;
import static com.application.bekend.constants.HouseConstants.new_grade;
import static com.application.bekend.constants.HouseConstants.new_isCancalletionFree;
import static com.application.bekend.constants.HouseConstants.new_name;
import static com.application.bekend.constants.HouseConstants.new_numberOfReviews;
import static com.application.bekend.constants.HouseConstants.new_promoDescription;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
import com.application.bekend.DTO.CompanyDTO;
import com.application.bekend.DTO.FishingAdventureDTO;
import com.application.bekend.DTO.UserCategoryDTO;
import com.application.bekend.constants.FishingAdventureConstants;
import com.application.bekend.util.TestUtil;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CompanyControllerTest {
	
    private static final String URL_PREFIX = "/api/company";

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
    public void testGetCompanyInfo() throws Exception {
    	mockMvc.perform(get(URL_PREFIX + "/getCompanyInfo/" + 1L)).andExpect(status().isOk())
        .andExpect(content().contentType(contentType))
        .andExpect(jsonPath("$.percentagePerReservation").value(5))
        .andExpect(jsonPath("$.pointsPerReservationClient").value(5))
        .andExpect(jsonPath("$.pointsPerReservationOwner").value(3));
    }

    @Test
    public void testGetAllCategoriesNumber() throws Exception {
        mockMvc.perform(get(URL_PREFIX + "/getAllUserCategories")).andExpect(status().isOk())
                .andExpect(content().contentType(contentType)).andExpect(jsonPath("$", hasSize(1)));
    }
    
    @Test
    @Transactional
    @Rollback(true)
    public void testSaveCategory() throws Exception {
        // na frontu smo kreirali novu vikendicu
        UserCategoryDTO category = new UserCategoryDTO();
        category.setDiscountPercentage(5);
        category.setName("Bronze");
        category.setPoints(25);

        // kreiranu vikendicu saljemo u kontroler
        String json = TestUtil.json(category);
        String newURL = URL_PREFIX + "/addCategory";
        this.mockMvc.perform(post(newURL).contentType(contentType).content(json)).andExpect(status().isOk());
    }
    
    @Test
    @Transactional
    @Rollback(true)
    public void testEditCategory() throws Exception {
        // na frontu smo kreirali novu vikendicu
        UserCategoryDTO category = new UserCategoryDTO();
        category.setDiscountPercentage(5);
        category.setName("Bronze");
        category.setPoints(30);

        // kreiranu vikendicu saljemo u kontroler
        String json = TestUtil.json(category);
        String newURL = URL_PREFIX + "/edit/" + 1L;
        this.mockMvc.perform(put(newURL).contentType(contentType).content(json)).andExpect(status().isOk());
    }
    
    @Test
    @Transactional
    @Rollback(true)
    public void testDeleteCategory() throws Exception {
        this.mockMvc.perform(delete(URL_PREFIX + "/delete/" + 1L)).andExpect(status().isOk());
    }
    
    @Test
    @Transactional
    @Rollback(true)
    public void testEditCompany() throws Exception {
        // na frontu smo kreirali novu vikendicu
        CompanyDTO company = new CompanyDTO();
        company.setPercentagePerReservation(7);
        company.setPointsPerReservationOwner(6);

        // kreiranu vikendicu saljemo u kontroler
        String json = TestUtil.json(company);
        String newURL = URL_PREFIX + "/saveChanges/" + 1L;
        this.mockMvc.perform(put(newURL).contentType(contentType).content(json)).andExpect(status().isOk());
    }
}
