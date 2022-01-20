package com.application.bekend.service;

import com.application.bekend.model.Boat;
import com.application.bekend.repository.BoatRepository;
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

import static com.application.bekend.constants.BoatConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BoatServiceTest {

    @Mock
    private BoatRepository boatRepositoryMock;

    @Mock
    private Boat boatMock;

    @InjectMocks
    private BoatService boatService;

    @Test
    public void testGetAll() {
        // 1. Definisanje ponašanja mock objekata (ja mu kazem kada se pozove metoda mock-a, da mi vrati tu konkretnu kucu (definisem ponasanje metode))
        when(boatRepositoryMock.findAll()).thenReturn(Arrays.asList(new Boat( id, name, type, length, engineNumber, enginePower, maxSpeed,
                promoDescription, capacity, grade, numberOfReviews, behaviourRules, fishingEquipment, pricePerDay, isCancalletionFree, cancalletionFee)));

        // 2. Akcija
        List<Boat> boats = boatService.findAll();

        // 3. Verifikacija: asertacije i/ili verifikacija interakcije sa mock objektima
        assertThat(boats).hasSize(1);

		/*
		Možemo verifikovati ponašanje mokovanih objekata pozivanjem verify* metoda.
		 */
        verify(boatRepositoryMock, times(1)).findAll();
        verifyNoMoreInteractions(boatRepositoryMock);
    }

    @Test
    public void testGetBoatById(){
        // 1. Definisanje ponašanja mock objekata
        when(boatRepositoryMock.getBoatById(id)).thenReturn(boatMock);

        // 2. Akcija
        Boat dbBoat = boatService.getBoatById(id);

        // 3. Verifikacija: asertacije i/ili verifikacija interakcije sa mock objektima
        assertEquals(boatMock, dbBoat);

        verify(boatRepositoryMock, times(1)).getBoatById(id);
        verifyNoMoreInteractions(boatRepositoryMock);
    }

    @Test
    public void testGetBoatByName() {
        // 1. Definisanje ponašanja mock objekata
        when(boatRepositoryMock.getBoatByName(name)).thenReturn(boatMock);

        // 2. Akcija
        Boat dbBoat = boatService.getBoatByName(name);

        // 3. Verifikacija: asertacije i/ili verifikacija interakcije sa mock objektima
        assertEquals(boatMock, dbBoat);

        verify(boatRepositoryMock, times(1)).getBoatByName(name);
        verifyNoMoreInteractions(boatRepositoryMock);
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testUpdate() {
        // 1. Definisanje ponašanja mock objekata
        when(boatRepositoryMock.getBoatById(id)).thenReturn(new Boat( id, name, type, length, engineNumber, enginePower, maxSpeed,
                promoDescription, capacity, grade, numberOfReviews, behaviourRules, fishingEquipment, pricePerDay, isCancalletionFree, cancalletionFee));

        // 2. Akcija
        Boat boatForUpdate = boatService.getBoatById(id);
        boatForUpdate.setName(new_name_boat);
        boatForUpdate.setType(new_type);
        boatForUpdate.setFishingEquipment(new_fishingEquipment);

        when(boatRepositoryMock.save(boatForUpdate)).thenReturn(boatForUpdate);
        boatForUpdate = boatService.save(boatForUpdate);

        // 3. Verifikacija: asertacije i/ili verifikacija interakcije sa mock objektima
        assertThat(boatForUpdate).isNotNull();

        boatForUpdate = boatService.getBoatById(id); // verifikacija da se u bazi nalaze izmenjeni podaci
        assertThat(boatForUpdate.getName()).isEqualTo(new_name_boat);
        assertThat(boatForUpdate.getType()).isEqualTo(new_type);
        assertThat(boatForUpdate.getFishingEquipment()).isEqualTo(new_fishingEquipment);

        verify(boatRepositoryMock, times(2)).getBoatById(id);
        verify(boatRepositoryMock, times(1)).save(boatForUpdate);
        verifyNoMoreInteractions(boatRepositoryMock);
    }

    @Test
    @Transactional
    @Rollback(true) // uključeno po default-u, ne mora se navesti
    public void testAdd() {
        Boat boat = new Boat();
        boat.setName(new_name_boat);
        boat.setPromoDescription(new_promoDescription);
        boat.setGrade(new_grade);
        boat.setNumberOfReviews(new_numberOfReviews);
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
        boat.setId(new_id);

        // 1. Definisanje ponašanja mock objekata
        when(boatRepositoryMock.findAll()).thenReturn(Arrays.asList(new Boat( id, name, type, length, engineNumber, enginePower, maxSpeed,
                promoDescription, capacity, grade, numberOfReviews, behaviourRules, fishingEquipment, pricePerDay, isCancalletionFree, cancalletionFee)));
        when(boatRepositoryMock.save(boat)).thenReturn(boat);

        // 2. Akcija
        int dbSizeBeforeAdd = boatService.findAll().size();

        Boat dbBoat = boatService.save(boat);

        when(boatRepositoryMock.findAll()).thenReturn(Arrays.asList(new Boat( id, name, type, length, engineNumber, enginePower, maxSpeed,
                promoDescription, capacity, grade, numberOfReviews, behaviourRules, fishingEquipment, pricePerDay, isCancalletionFree, cancalletionFee), boat));

        // 3. Verifikacija: asertacije i/ili verifikacija interakcije sa mock objektima
        assertThat(dbBoat).isNotNull();

        List<Boat> boats = boatService.findAll();
        assertThat(boats).hasSize(dbSizeBeforeAdd + 1); //verifikacija da je novi brod upisan u bazu

        dbBoat = boats.get(boats.size() - 1); // preuzimanje poslednjeg broda

        assertThat(dbBoat.getName()).isEqualToIgnoringCase(new_name_boat);
        assertThat(dbBoat.getPromoDescription()).isEqualTo(new_promoDescription);
        assertThat(dbBoat.getGrade()).isEqualTo(new_grade);
        assertThat(dbBoat.getPricePerDay()).isEqualTo(new_pricePerDay);
        assertThat(dbBoat.getNumberOfReviews()).isEqualTo(new_numberOfReviews);
        assertThat(dbBoat.getBehaviourRules()).isEqualTo(new_behaviourRules);
        assertThat(dbBoat.getCancalletionFee()).isEqualTo(new_cancalletionFee);
        assertThat(dbBoat.isCancalletionFree()).isEqualTo(new_isCancalletionFree);
        assertThat(dbBoat.getFishingEquipment()).isEqualTo(new_fishingEquipment);
        assertThat(dbBoat.getType()).isEqualTo(new_type);
        assertThat(dbBoat.getLength()).isEqualTo(new_length);
        assertThat(dbBoat.getEngineNumber()).isEqualTo(new_engineNumber);
        assertThat(dbBoat.getEnginePower()).isEqualTo(new_enginePower);
        assertThat(dbBoat.getMaxSpeed()).isEqualTo(new_maxSpeed);
        assertThat(dbBoat.getCapacity()).isEqualTo(new_capacity);
        assertThat(dbBoat.getId()).isEqualTo(new_id);

        verify(boatRepositoryMock, times(2)).findAll();
        verify(boatRepositoryMock, times(1)).save(boat);
        verifyNoMoreInteractions(boatRepositoryMock);
    }

}
