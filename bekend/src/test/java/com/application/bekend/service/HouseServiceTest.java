package com.application.bekend.service;

import com.application.bekend.model.Boat;
import com.application.bekend.model.House;
import com.application.bekend.repository.HouseRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

import static com.application.bekend.constants.HouseConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HouseServiceTest {

    @Mock
    private HouseRepository houseRepositoryMock;

    @Mock
    private House houseMock;

    @InjectMocks
    private HouseService houseService;

    @Test
    public void testGetAll() {
		/*
		Kako za testove koristimo mokovane repository objekte moramo da definišemo šta će se desiti kada se
		pozove određena metoda kombinacijom "when"-"then" Mockito metoda.
		 */

        // 1. Definisanje ponašanja mock objekata (ja mu kazem kada se pozove metoda mock-a, da mi vrati tu konkretnu kucu (definisem ponasanje metode))
        when(houseRepositoryMock.findAll()).thenReturn(Arrays.asList(new House( id, name, grade, numberOfReviews, promoDescription, behaviourRules, pricePerDay, isCancalletionFree, cancalletionFee)));

        // 2. Akcija
        List<House> houses = houseService.findAll();

        // 3. Verifikacija: asertacije i/ili verifikacija interakcije sa mock objektima
        assertThat(houses).hasSize(1);

		/*
		Možemo verifikovati ponašanje mokovanih objekata pozivanjem verify* metoda.
		 */
        verify(houseRepositoryMock, times(1)).findAll();
        verifyNoMoreInteractions(houseRepositoryMock);
    }

    @Test
    public void testGetHouseById() {
        // 1. Definisanje ponašanja mock objekata
        when(houseRepositoryMock.getHouseById(id)).thenReturn(houseMock);

        // 2. Akcija
        House dbHouse = houseService.getHouseById(id);

        // 3. Verifikacija: asertacije i/ili verifikacija interakcije sa mock objektima
        assertEquals(houseMock, dbHouse);

        verify(houseRepositoryMock, times(1)).getHouseById(id);
        verifyNoMoreInteractions(houseRepositoryMock);
    }

    @Test
    public void testGetHouseByName() {
        // 1. Definisanje ponašanja mock objekata
        when(houseRepositoryMock.getHouseByName(name)).thenReturn(houseMock);

        // 2. Akcija
        House dbHouse = houseService.getHouseByName(name);

        // 3. Verifikacija: asertacije i/ili verifikacija interakcije sa mock objektima
        assertEquals(houseMock, dbHouse);

        verify(houseRepositoryMock, times(1)).getHouseByName(name);
        verifyNoMoreInteractions(houseRepositoryMock);
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testUpdate() {
        // 1. Definisanje ponašanja mock objekata
        when(houseRepositoryMock.getHouseById(id)).thenReturn(new House( id, name, grade, numberOfReviews, promoDescription, behaviourRules, pricePerDay, isCancalletionFree, cancalletionFee));

        // 2. Akcija
        House houseForUpdate = houseService.getHouseById(id);
        houseForUpdate.setName(new_name);
        houseForUpdate.setPromoDescription(new_promoDescription);

        when(houseRepositoryMock.save(houseForUpdate)).thenReturn(houseForUpdate);

        houseForUpdate = houseService.save(houseForUpdate);

        // 3. Verifikacija: asertacije i/ili verifikacija interakcije sa mock objektima
        assertThat(houseForUpdate).isNotNull();

        houseForUpdate = houseService.getHouseById(id); // verifikacija da se u bazi nalaze izmenjeni podaci
        assertThat(houseForUpdate.getName()).isEqualTo(new_name);
        assertThat(houseForUpdate.getPromoDescription()).isEqualTo(new_promoDescription);

        verify(houseRepositoryMock, times(2)).getHouseById(id);
        verify(houseRepositoryMock, times(1)).save(houseForUpdate);
        verifyNoMoreInteractions(houseRepositoryMock);
    }

    @Test
    @Transactional
    @Rollback(true) // uključeno po default-u, ne mora se navesti
    public void testAdd() {
        House house = new House();
        house.setName(new_name);
        house.setPromoDescription(new_promoDescription);
        house.setGrade(new_grade);
        house.setNumberOfReviews(new_numberOfReviews);
        house.setBehaviourRules(new_behaviourRules);
        house.setPricePerDay(new_pricePerDay);
        house.setCancalletionFee(new_cancalletionFee);
        house.setCancalletionFree(new_isCancalletionFree);
        house.setId(new_id);

        // 1. Definisanje ponašanja mock objekata
        when(houseRepositoryMock.findAll()).thenReturn(Arrays.asList(new House(id, name, grade, numberOfReviews, promoDescription, behaviourRules, pricePerDay, isCancalletionFree, cancalletionFee)));
        when(houseRepositoryMock.save(house)).thenReturn(house);

        // 2. Akcija
        int dbSizeBeforeAdd = houseService.findAll().size();

        House dbHouse = houseService.save(house);

        when(houseRepositoryMock.findAll()).thenReturn(Arrays.asList(new House(id, name, grade, numberOfReviews, promoDescription, behaviourRules, pricePerDay, isCancalletionFree, cancalletionFee), house));

        // 3. Verifikacija: asertacije i/ili verifikacija interakcije sa mock objektima
        assertThat(dbHouse).isNotNull();

        List<House> houses = houseService.findAll();
        assertThat(houses).hasSize(dbSizeBeforeAdd + 1); //verifikacija da je nova vikendica upisan u bazu

        dbHouse = houses.get(houses.size() - 1); // preuzimanje poslednje vikendice

        assertThat(dbHouse.getName()).isEqualToIgnoringCase(new_name);
        assertThat(dbHouse.getPromoDescription()).isEqualTo(new_promoDescription);
        assertThat(dbHouse.getGrade()).isEqualTo(new_grade);
        assertThat(dbHouse.getPricePerDay()).isEqualTo(new_pricePerDay);
        assertThat(dbHouse.getNumberOfReviews()).isEqualTo(new_numberOfReviews);
        assertThat(dbHouse.getBehaviourRules()).isEqualTo(new_behaviourRules);
        assertThat(dbHouse.getCancalletionFee()).isEqualTo(new_cancalletionFee);
        assertThat(dbHouse.isCancalletionFree()).isEqualTo(new_isCancalletionFree);
        assertThat(dbHouse.getId()).isEqualTo(new_id);

        verify(houseRepositoryMock, times(2)).findAll();
        verify(houseRepositoryMock, times(1)).save(house);
        verifyNoMoreInteractions(houseRepositoryMock);
    }
}
