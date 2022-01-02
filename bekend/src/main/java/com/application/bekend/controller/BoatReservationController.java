package com.application.bekend.controller;

import com.application.bekend.DTO.BoatReservationDTO;
import com.application.bekend.DTO.BoatReservationSlideDTO;
import com.application.bekend.DTO.HouseReservationDTO;
import com.application.bekend.DTO.HouseReservationSlideDTO;
import com.application.bekend.model.BoatReservation;
import com.application.bekend.model.HouseReservation;
import com.application.bekend.service.BoatReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/boatReservations")
public class BoatReservationController {

    private final BoatReservationService boatReservationService;

    @Autowired
    public BoatReservationController(BoatReservationService boatReservationService) {
        this.boatReservationService = boatReservationService;
    }

    @GetMapping("/getAllByBoatId/{id}")
    public ResponseEntity<List<BoatReservationSlideDTO>> getAllByBoatId(@PathVariable("id") Long id){
        List<BoatReservation> boatReservations = this.boatReservationService.getAllByBoat_Id(id);
        List<BoatReservationDTO> boatReservationDTOS = new ArrayList<>();
        List<BoatReservationSlideDTO> boatReservationSlideDTOS = new ArrayList<>();

        for (BoatReservation a: boatReservations) {
            BoatReservationDTO boatReservationDTO = new BoatReservationDTO(a.getId(), a.getStartDate(), a.getEndDate(), a.getMaxGuests(), a.getAdditionalServices(), a.getPrice(), a.isAvailable());
            boatReservationDTOS.add(boatReservationDTO);
        }

        List<BoatReservationDTO> reservationDTOS = new ArrayList<>();
        int i = 1;
        for (BoatReservationDTO dto: boatReservationDTOS) {
            reservationDTOS.add(dto);
            if (i % 3 == 0) {
                BoatReservationSlideDTO boatReservationSlideDTO = new BoatReservationSlideDTO(reservationDTOS);
                boatReservationSlideDTOS.add(boatReservationSlideDTO);
                reservationDTOS = new ArrayList<>();
            }
            i = i +1;
        }

        if(reservationDTOS.size() != 0){
            BoatReservationSlideDTO boatReservationSlideDTO = new BoatReservationSlideDTO(reservationDTOS);
            boatReservationSlideDTOS.add(boatReservationSlideDTO);
        }

        return new ResponseEntity<>(boatReservationSlideDTOS, HttpStatus.OK);
    }
}
