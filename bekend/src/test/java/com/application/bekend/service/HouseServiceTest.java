package com.application.bekend.service;

import com.application.bekend.model.House;
import com.application.bekend.repository.HouseRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
    private House house;

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
        //assertEquals(students.get(0).getFirstName(), DB_FIRST_NAME.toUpperCase());

		/*
		Možemo verifikovati ponašanje mokovanih objekata pozivanjem verify* metoda.
		 */
        verify(houseRepositoryMock, times(1)).findAll();
        verifyNoMoreInteractions(houseRepositoryMock);
    }
}
