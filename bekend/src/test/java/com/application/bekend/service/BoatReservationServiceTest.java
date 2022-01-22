package com.application.bekend.service;

import com.application.bekend.DTO.ReservationCheckDTO;
import com.application.bekend.model.BoatReservation;
import com.application.bekend.model.HouseReservation;
import com.application.bekend.model.MyUser;
import com.application.bekend.repository.BoatReservationRepository;
import com.application.bekend.repository.HouseReservationRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BoatReservationServiceTest {

    @Mock
    private BoatReservationRepository boatReservationRepositoryMock;

    @Mock
    private BoatReservation boatReservationMock;

    @InjectMocks
    private BoatReservationService boatReservationServiceMock;

    //Postoji termin vec
    @Test
    public void findBoatAvailability() {
        ReservationCheckDTO dto = new ReservationCheckDTO();
        dto.setStartMilis(1664972430000L);
        dto.setEndMilis(1665404430000L);
        dto.setMaxGuest(5);
        BoatReservation boatReservation = new BoatReservation();
        boatReservation.setStartDate(new Date(1665058830000L));
        boatReservation.setEndDate(new Date(1665231630000L));
        boatReservation.setAvailable(false);
        boatReservation.setCancelled(false);
        boatReservation.setAction(false);
        when(boatReservationRepositoryMock.getAllByBoat_Id(1L)).thenReturn(Arrays.asList(boatReservation));
        Boolean isAvailable = boatReservationServiceMock.findBoatAvailability(dto, 1L);
        assertEquals(isAvailable, false);
        verify(boatReservationRepositoryMock, times(1)).getAllByBoat_Id(1L);
        verifyNoMoreInteractions(boatReservationRepositoryMock);
    }

    //Postoji termin vec
    @Test
    public void findBoatAvailabilityAction() {
        ReservationCheckDTO dto = new ReservationCheckDTO();
        dto.setStartMilis(1664713230000L);
        dto.setEndMilis(1665750030000L);
        dto.setMaxGuest(5);
        BoatReservation boatReservation = new BoatReservation();
        boatReservation.setStartDate(new Date(1664972430000L));
        boatReservation.setEndDate(new Date(1665145230000L));
        boatReservation.setAvailable(true);
        boatReservation.setCancelled(false);
        boatReservation.setAction(true);
        when(boatReservationRepositoryMock.getAllByBoat_Id(1L)).thenReturn(Arrays.asList(boatReservation));
        Boolean isAvailable = boatReservationServiceMock.findBoatAvailability(dto, 1L);
        assertEquals(isAvailable, true);
        verify(boatReservationRepositoryMock, times(1)).getAllByBoat_Id(1L);
        verifyNoMoreInteractions(boatReservationRepositoryMock);
    }

    @Test
    public void findBoatAvailabilityCancelledReservation() {
        ReservationCheckDTO dto = new ReservationCheckDTO();
        dto.setStartMilis(1664713230000L);
        dto.setEndMilis(1665750030000L);
        dto.setMaxGuest(5);
        BoatReservation boatReservation = new BoatReservation();
        boatReservation.setStartDate(new Date(1664972430000L));
        boatReservation.setEndDate(new Date(1665145230000L));
        boatReservation.setAvailable(false);
        boatReservation.setCancelled(true);
        boatReservation.setAction(false);
        when(boatReservationRepositoryMock.getAllByBoat_Id(1L)).thenReturn(Arrays.asList(boatReservation));
        Boolean isAvailable = boatReservationServiceMock.findBoatAvailability(dto, 1L);
        assertEquals(isAvailable, true);
        verify(boatReservationRepositoryMock, times(1)).getAllByBoat_Id(1L);
        verifyNoMoreInteractions(boatReservationRepositoryMock);
    }
}
