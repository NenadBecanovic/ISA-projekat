package com.application.bekend.service;

import com.application.bekend.DTO.ReservationCheckDTO;
import com.application.bekend.model.HouseReservation;
import com.application.bekend.model.MyUser;
import com.application.bekend.model.Subscription;
import com.application.bekend.repository.HouseReservationRepository;
import com.application.bekend.repository.SubscriptionRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.application.bekend.constants.HouseConstants.*;
import static com.application.bekend.constants.HouseConstants.new_pricePerDay;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HouseReservationServiceTest {

    @Mock
    private HouseReservationRepository houseReservationRepository;

    @Mock
    private HouseReservation houseReservationMock;



    @InjectMocks
    private HouseReservationService houseReservationServiceMock;

    //Postoji termin vec
    @Test
    public void findHouseAvailability() {
        ReservationCheckDTO dto = new ReservationCheckDTO();
        dto.setStartMilis(1664972430000L);
        dto.setEndMilis(1665404430000L);
        dto.setMaxGuest(5);
        HouseReservation houseReservation = new HouseReservation();
        houseReservation.setStartDate(new Date(1665058830000L));
        houseReservation.setEndDate(new Date(1665231630000L));
        houseReservation.setAvailable(false);
        houseReservation.setCancelled(false);
        houseReservation.setAction(false);
        when(houseReservationRepository.getAllByHouse_Id(1L)).thenReturn(Arrays.asList(houseReservation));
        Boolean isAvailable = houseReservationServiceMock.findHouseAvailability(dto, 1L);
        assertEquals(isAvailable, false);
        verify(houseReservationRepository, times(1)).getAllByHouse_Id(1L);
        verifyNoMoreInteractions(houseReservationRepository);
    }

    @Test
    //treba da prekoraci akciju
    public void findHouseAvailabilityaAction() {
        ReservationCheckDTO dto = new ReservationCheckDTO();
        dto.setStartMilis(1664713230000L);
        dto.setEndMilis(1665750030000L);
        dto.setMaxGuest(5);
        HouseReservation houseReservation = new HouseReservation();
        houseReservation.setStartDate(new Date(1664972430000L));
        houseReservation.setEndDate(new Date(1665145230000L));
        houseReservation.setAvailable(true);
        houseReservation.setCancelled(false);
        houseReservation.setAction(true);
        when(houseReservationRepository.getAllByHouse_Id(1L)).thenReturn(Arrays.asList(houseReservation));
        Boolean isAvailable = houseReservationServiceMock.findHouseAvailability(dto, 1L);
        assertEquals(isAvailable, true);
        verify(houseReservationRepository, times(1)).getAllByHouse_Id(1L);
        verifyNoMoreInteractions(houseReservationRepository);
    }

    @Test
    //treba da prekoraci ignorise otkazanu rezervaciju
    public void findHouseAvailabilityCancelReservation() {
        ReservationCheckDTO dto = new ReservationCheckDTO();
        dto.setStartMilis(1664713230000L);
        dto.setEndMilis(1665750030000L);
        dto.setMaxGuest(5);
        HouseReservation houseReservation = new HouseReservation();
        houseReservation.setStartDate(new Date(1664972430000L));
        houseReservation.setEndDate(new Date(1665145230000L));
        houseReservation.setAvailable(false);
        houseReservation.setCancelled(true);
        houseReservation.setAction(false);
        when(houseReservationRepository.getAllByHouse_Id(1L)).thenReturn(Arrays.asList(houseReservation));
        Boolean isAvailable = houseReservationServiceMock.findHouseAvailability(dto, 1L);
        assertEquals(isAvailable, true);
        verify(houseReservationRepository, times(1)).getAllByHouse_Id(1L);
        verifyNoMoreInteractions(houseReservationRepository);
    }



    @Test
    public void getHouseReservationById() {
        HouseReservation houseReservation = new HouseReservation();
        houseReservation.setId(1L);
        houseReservation.setStartDate(new Date(1665058830000L));
        houseReservation.setEndDate(new Date(1665231630000L));
        houseReservation.setAvailable(false);
        houseReservation.setCancelled(false);
        houseReservation.setAction(false);

        when(houseReservationRepository.getHouseReservationById(1L)).thenReturn(houseReservation);
        HouseReservation houseReservation1 = houseReservationServiceMock.getHouseReservationById(1L);

        assertThat(houseReservation1.getId()).isEqualTo(1L);
        assertThat(houseReservation1.getStartDate()).isEqualTo(new Date(1665058830000L));
        assertThat(houseReservation1.getEndDate()).isEqualTo(new Date(1665231630000L));
        assertThat(houseReservation1.getCancelled()).isEqualTo(false);

        verify(houseReservationRepository, times(1)).getHouseReservationById(1L);
        verifyNoMoreInteractions(houseReservationRepository);
    }

    @Test
    public void getHouseReservationByUserId() {
        HouseReservation houseReservation = new HouseReservation();
        houseReservation.setId(1L);
        MyUser myUser = new MyUser();
        myUser.setId(1L);
        houseReservation.setGuest(myUser);
        houseReservation.setStartDate(new Date(1665058830000L));
        houseReservation.setEndDate(new Date(1665231630000L));
        houseReservation.setAvailable(false);
        houseReservation.setCancelled(false);
        houseReservation.setAction(false);

        when(houseReservationRepository.findHouseReservationByGuestId(1L)).thenReturn(Arrays.asList(houseReservation));
        List<HouseReservation> houseReservation1 = houseReservationServiceMock.getHouseReservationByUserId(1L);
        assertThat(houseReservation1).hasSize(1);
        assertThat(houseReservation1.get(0).getId()).isEqualTo(1L);
        assertThat(houseReservation1.get(0).getStartDate()).isEqualTo(new Date(1665058830000L));
        assertThat(houseReservation1.get(0).getEndDate()).isEqualTo(new Date(1665231630000L));
        assertThat(houseReservation1.get(0).getCancelled()).isEqualTo(false);
        assertThat(houseReservation1.get(0).getGuest().getId()).isEqualTo(1L);

        verify(houseReservationRepository, times(1)).findHouseReservationByGuestId(1L);
        verifyNoMoreInteractions(houseReservationRepository);
    }
}
