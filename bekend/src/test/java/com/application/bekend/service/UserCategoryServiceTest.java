package com.application.bekend.service;

import static com.application.bekend.constants.UserCategoryConstants.new_category_points;
import static com.application.bekend.constants.UserCategoryConstants.new_category_discountPercentage;
import static com.application.bekend.constants.UserCategoryConstants.new_category_id;
import static com.application.bekend.constants.UserCategoryConstants.id;
import static com.application.bekend.constants.UserCategoryConstants.points;
import static com.application.bekend.constants.UserCategoryConstants.discountPercentage;
import static com.application.bekend.constants.UserCategoryConstants.name;
import static com.application.bekend.constants.UserCategoryConstants.new_name;
import static com.application.bekend.constants.UserCategoryConstants.new_points;
import static com.application.bekend.constants.UserCategoryConstants.new_category_name;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import com.application.bekend.model.UserCategory;
import com.application.bekend.repository.UserCategoryRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserCategoryServiceTest {


    @Mock
    private UserCategoryRepository userCategoryRepositoryMock;

    @Mock
    private UserCategory userCategoryMock;

    @InjectMocks
    private UserCategoryService userCategoryService;

    @Test
    public void testGetAllCategories() {
		/*
		Kako za testove koristimo mokovane repository objekte moramo da definišemo šta će se desiti kada se
		pozove određena metoda kombinacijom "when"-"then" Mockito metoda.
		 */

        // 1. Definisanje ponašanja mock objekata (ja mu kazem kada se pozove metoda mock-a, da mi vrati tu konkretnu kucu (definisem ponasanje metode))
        when(userCategoryRepositoryMock.findAll()).thenReturn(Arrays.asList(new UserCategory( id, points, name, discountPercentage)));

        // 2. Akcija
        List<UserCategory> categories = userCategoryService.findAll();

        // 3. Verifikacija: asertacije i/ili verifikacija interakcije sa mock objektima
        assertThat(categories).hasSize(1);

		/*
		Možemo verifikovati ponašanje mokovanih objekata pozivanjem verify* metoda.
		 */
   /*     verify(houseRepositoryMock, times(1)).findAll();
        verifyNoMoreInteractions(houseRepositoryMock);*/
    }

    @Test
    public void testGetCategoryById() {
        // 1. Definisanje ponašanja mock objekata
        when(userCategoryRepositoryMock.getUserCategoryById(id)).thenReturn(userCategoryMock);

        // 2. Akcija
        UserCategory category = userCategoryService.getCategoryById(id);

        // 3. Verifikacija: asertacije i/ili verifikacija interakcije sa mock objektima
        assertEquals(userCategoryMock, category);

        verify(userCategoryRepositoryMock, times(1)).getUserCategoryById(id);
        verifyNoMoreInteractions(userCategoryRepositoryMock);
    }

    @Test
    public void testGetUserCategoryByName() {
        // 1. Definisanje ponašanja mock objekata
        when(userCategoryRepositoryMock.getUserCategoryByName(name)).thenReturn(userCategoryMock);

        // 2. Akcija
        UserCategory userCategory = userCategoryService.findUserCategoryByName(name);

        // 3. Verifikacija: asertacije i/ili verifikacija interakcije sa mock objektima
        assertEquals(userCategoryMock, userCategory);

        verify(userCategoryRepositoryMock, times(1)).getUserCategoryByName(name);
        verifyNoMoreInteractions(userCategoryRepositoryMock);
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testUpdate() {
        // 1. Definisanje ponašanja mock objekata
        when(userCategoryRepositoryMock.getUserCategoryById(id)).thenReturn(new UserCategory( id, points, name, discountPercentage));

        // 2. Akcija
        UserCategory userCategoryUpdate = userCategoryService.getCategoryById(id);
        userCategoryUpdate.setName(new_name);
        userCategoryUpdate.setPoints(new_points);

        when(userCategoryRepositoryMock.save(userCategoryUpdate)).thenReturn(userCategoryUpdate);

        userCategoryUpdate = userCategoryService.save(userCategoryUpdate);

        // 3. Verifikacija: asertacije i/ili verifikacija interakcije sa mock objektima
        assertThat(userCategoryUpdate).isNotNull();

        userCategoryUpdate = userCategoryService.getCategoryById(id); // verifikacija da se u bazi nalaze izmenjeni podaci
        assertThat(userCategoryUpdate.getName()).isEqualTo(new_name);
        assertThat(userCategoryUpdate.getPoints()).isEqualTo(new_points);

    /*    verify(fishingAdventureRepositoryMock, times(2)).getFishingAdventureById(id);
        verify(fishingAdventureRepositoryMock, times(1)).save(fishingAdventureForUpdate);
        verifyNoMoreInteractions(fishingAdventureRepositoryMock);*/
    }

    @Test
    @Transactional
    @Rollback(true) // uključeno po default-u, ne mora se navesti
    public void testAdd() {
        UserCategory category = new UserCategory();
        category.setName(new_category_name);
        category.setPoints(new_category_points);
        category.setDiscountPercentage(new_category_discountPercentage);
        category.setId(new_category_id);
        // 1. Definisanje ponašanja mock objekata
        when(userCategoryRepositoryMock.findAll()).thenReturn(Arrays.asList(new UserCategory( id, points, name, discountPercentage)));
        when(userCategoryRepositoryMock.save(category)).thenReturn(category);

        // 2. Akcija
        int dbSizeBeforeAdd = userCategoryService.findAll().size();

        UserCategory newCategory = userCategoryService.save(category);

        when(userCategoryRepositoryMock.findAll()).thenReturn(Arrays.asList(new UserCategory( id, points, name, discountPercentage), category));

        // 3. Verifikacija: asertacije i/ili verifikacija interakcije sa mock objektima
        assertThat(newCategory).isNotNull();

        List<UserCategory> allCategories = userCategoryService.findAll();
        assertThat(allCategories).hasSize(dbSizeBeforeAdd + 1); //verifikacija da je nova vikendica upisan u bazu

        newCategory = allCategories.get(allCategories.size() - 1); // preuzimanje poslednje vikendice

        assertThat(newCategory.getName()).isEqualToIgnoringCase(new_category_name);
        assertThat(newCategory.getDiscountPercentage()).isEqualTo(new_category_discountPercentage);
        assertThat(newCategory.getPoints()).isEqualTo(new_category_points);
        assertThat(newCategory.getId()).isEqualTo(new_category_id);

    }
}
