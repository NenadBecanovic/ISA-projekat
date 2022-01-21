package com.application.bekend.service;

import static com.application.bekend.constants.FishingAdventureConstants.behaviourRules;
import static com.application.bekend.constants.FishingAdventureConstants.cancalletionFee;
import static com.application.bekend.constants.FishingAdventureConstants.grade;
import static com.application.bekend.constants.FishingAdventureConstants.id;
import static com.application.bekend.constants.FishingAdventureConstants.instructorId;
import static com.application.bekend.constants.FishingAdventureConstants.isCancalletionFree;
import static com.application.bekend.constants.FishingAdventureConstants.name;
import static com.application.bekend.constants.FishingAdventureConstants.new_behaviourRules;
import static com.application.bekend.constants.FishingAdventureConstants.new_cancalletionFee;
import static com.application.bekend.constants.FishingAdventureConstants.new_grade;
import static com.application.bekend.constants.FishingAdventureConstants.new_id;
import static com.application.bekend.constants.FishingAdventureConstants.new_isCancalletionFree;
import static com.application.bekend.constants.FishingAdventureConstants.new_name;
import static com.application.bekend.constants.FishingAdventureConstants.new_numberOfReviews;
import static com.application.bekend.constants.FishingAdventureConstants.new_pricePerHour;
import static com.application.bekend.constants.FishingAdventureConstants.new_promoDescription;
import static com.application.bekend.constants.FishingAdventureConstants.numberOfReviews;
import static com.application.bekend.constants.FishingAdventureConstants.pricePerHour;
import static com.application.bekend.constants.FishingAdventureConstants.promoDescription;
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

import com.application.bekend.model.FishingAdventure;
import com.application.bekend.repository.FishingAdventureRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FishingAdventureServiceTest {


    @Mock
    private FishingAdventureRepository fishingAdventureRepositoryMock;

    @Mock
    private FishingAdventure fishingAdventureMock;

    @InjectMocks
    private FishingAdventureService fishingAdventureService;

    @Test
    public void testGetAll() {
		/*
		Kako za testove koristimo mokovane repository objekte moramo da definišemo šta će se desiti kada se
		pozove određena metoda kombinacijom "when"-"then" Mockito metoda.
		 */

        // 1. Definisanje ponašanja mock objekata (ja mu kazem kada se pozove metoda mock-a, da mi vrati tu konkretnu kucu (definisem ponasanje metode))
        when(fishingAdventureRepositoryMock.findAll()).thenReturn(Arrays.asList(new FishingAdventure( id, name, grade, numberOfReviews, promoDescription, behaviourRules, pricePerHour, isCancalletionFree, cancalletionFee)));

        // 2. Akcija
        List<FishingAdventure> fishingAdventures = fishingAdventureService.findAll();

        // 3. Verifikacija: asertacije i/ili verifikacija interakcije sa mock objektima
        assertThat(fishingAdventures).hasSize(1);

		/*
		Možemo verifikovati ponašanje mokovanih objekata pozivanjem verify* metoda.
		 */
   /*     verify(houseRepositoryMock, times(1)).findAll();
        verifyNoMoreInteractions(houseRepositoryMock);*/
    }

    @Test
    public void testGetFishingAdventureById() {
        // 1. Definisanje ponašanja mock objekata
        when(fishingAdventureRepositoryMock.getFishingAdventureById(id)).thenReturn(fishingAdventureMock);

        // 2. Akcija
        FishingAdventure adventure = fishingAdventureService.getFishingAdventureById(id);

        // 3. Verifikacija: asertacije i/ili verifikacija interakcije sa mock objektima
        assertEquals(fishingAdventureMock, adventure);

        verify(fishingAdventureRepositoryMock, times(1)).getFishingAdventureById(id);
        verifyNoMoreInteractions(fishingAdventureRepositoryMock);
    }

    @Test
    public void testGetFishingAdventureByInstructor() {
        // 1. Definisanje ponašanja mock objekata
        when(fishingAdventureRepositoryMock.getFishingAdventuresByInstructor_Id(instructorId)).thenReturn(Arrays.asList(new FishingAdventure( id, name, grade, numberOfReviews, promoDescription, behaviourRules, pricePerHour, isCancalletionFree, cancalletionFee)));

        // 2. Akcija
        List<FishingAdventure> fishingAdventures = fishingAdventureService.getFishingAdventuresByInstructor(instructorId);

        // 3. Verifikacija: asertacije i/ili verifikacija interakcije sa mock objektima
        assertThat(fishingAdventures).hasSize(1);

        verify(fishingAdventureRepositoryMock, times(1)).getFishingAdventuresByInstructor_Id(instructorId);
        verifyNoMoreInteractions(fishingAdventureRepositoryMock);
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testUpdate() {
        // 1. Definisanje ponašanja mock objekata
        when(fishingAdventureRepositoryMock.getFishingAdventureById(id)).thenReturn(new FishingAdventure( id, name, grade, numberOfReviews, promoDescription, behaviourRules, pricePerHour, isCancalletionFree, cancalletionFee));

        // 2. Akcija
        FishingAdventure fishingAdventureForUpdate = fishingAdventureService.getFishingAdventureById(id);
        fishingAdventureForUpdate.setName(new_name);
        fishingAdventureForUpdate.setPromoDescription(new_promoDescription);

        when(fishingAdventureRepositoryMock.save(fishingAdventureForUpdate)).thenReturn(fishingAdventureForUpdate);

        fishingAdventureForUpdate = fishingAdventureService.save(fishingAdventureForUpdate);

        // 3. Verifikacija: asertacije i/ili verifikacija interakcije sa mock objektima
        assertThat(fishingAdventureForUpdate).isNotNull();

        fishingAdventureForUpdate = fishingAdventureService.getFishingAdventureById(id); // verifikacija da se u bazi nalaze izmenjeni podaci
        assertThat(fishingAdventureForUpdate.getName()).isEqualTo(new_name);
        assertThat(fishingAdventureForUpdate.getPromoDescription()).isEqualTo(new_promoDescription);

    /*    verify(fishingAdventureRepositoryMock, times(2)).getFishingAdventureById(id);
        verify(fishingAdventureRepositoryMock, times(1)).save(fishingAdventureForUpdate);
        verifyNoMoreInteractions(fishingAdventureRepositoryMock);*/
    }

    @Test
    @Transactional
    @Rollback(true) // uključeno po default-u, ne mora se navesti
    public void testAdd() {
        FishingAdventure adventure = new FishingAdventure();
        adventure.setName(new_name);
        adventure.setPromoDescription(new_promoDescription);
        adventure.setGrade(new_grade);
        adventure.setNumberOfReviews(new_numberOfReviews);
        adventure.setBehaviourRules(new_behaviourRules);
        adventure.setPricePerHour(new_pricePerHour);
        adventure.setCancalletionFee(new_cancalletionFee);
        adventure.setCancalletionFree(new_isCancalletionFree);
        adventure.setId(new_id);

        // 1. Definisanje ponašanja mock objekata
        when(fishingAdventureRepositoryMock.findAll()).thenReturn(Arrays.asList(new FishingAdventure(id, name, grade, numberOfReviews, promoDescription, behaviourRules, pricePerHour, isCancalletionFree, cancalletionFee)));
        when(fishingAdventureRepositoryMock.save(adventure)).thenReturn(adventure);

        // 2. Akcija
        int dbSizeBeforeAdd = fishingAdventureService.findAll().size();

        FishingAdventure newAdventure = fishingAdventureService.save(adventure);

        when(fishingAdventureRepositoryMock.findAll()).thenReturn(Arrays.asList(new FishingAdventure(id, name, grade, numberOfReviews, promoDescription, behaviourRules, pricePerHour, isCancalletionFree, cancalletionFee), adventure));

        // 3. Verifikacija: asertacije i/ili verifikacija interakcije sa mock objektima
        assertThat(newAdventure).isNotNull();

        List<FishingAdventure> fishingAdventures = fishingAdventureService.findAll();
        assertThat(fishingAdventures).hasSize(dbSizeBeforeAdd + 1); //verifikacija da je nova vikendica upisan u bazu

        newAdventure = fishingAdventures.get(fishingAdventures.size() - 1); // preuzimanje poslednje vikendice

        assertThat(newAdventure.getName()).isEqualToIgnoringCase(new_name);
        assertThat(newAdventure.getPromoDescription()).isEqualTo(new_promoDescription);
        assertThat(newAdventure.getGrade()).isEqualTo(new_grade);
        assertThat(newAdventure.getPricePerHour()).isEqualTo(new_pricePerHour);
        assertThat(newAdventure.getNumberOfReviews()).isEqualTo(new_numberOfReviews);
        assertThat(newAdventure.getBehaviourRules()).isEqualTo(new_behaviourRules);
        assertThat(newAdventure.getCancalletionFee()).isEqualTo(new_cancalletionFee);
        assertThat(newAdventure.isCancalletionFree()).isEqualTo(new_isCancalletionFree);
        assertThat(newAdventure.getId()).isEqualTo(new_id);
/*
        verify(fishingAdventureRepositoryMock, times(2)).findAll();
        verify(fishingAdventureRepositoryMock, times(1)).save(adventure);
        verifyNoMoreInteractions(fishingAdventureRepositoryMock);*/
    }

}
