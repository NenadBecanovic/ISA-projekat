package com.application.bekend.controller;

import com.application.bekend.DTO.*;
import com.application.bekend.model.AdditionalServices;
import com.application.bekend.model.House;
import com.application.bekend.model.HouseReservation;
import com.application.bekend.service.HouseReservationService;
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
@RequestMapping("api/houseReservations")
public class HouseReservationController {

    private final HouseReservationService houseReservationService;

    @Autowired
    public HouseReservationController(HouseReservationService houseReservationService) {
        this.houseReservationService = houseReservationService;
    }

    @GetMapping("/getAllByHouseId/{id}")
    public ResponseEntity<List<HouseReservationSlideDTO>> getAllByHouseId(@PathVariable("id") Long id){
        List<HouseReservation> houseReservations = this.houseReservationService.getAllByHouse_Id(id);
        List<HouseReservationDTO> houseReservationDTOS = new ArrayList<>();
        List<HouseReservationSlideDTO> houseReservationSlideDTOS = new ArrayList<>();

        for (HouseReservation a: houseReservations) {
            HouseReservationDTO houseReservationDTO = new HouseReservationDTO(a.getId(), a.getStartDate(), a.getEndDate(), a.getMaxGuests(), a.getAdditionalServices(), a.getPrice(), a.isAvailable());
            houseReservationDTOS.add(houseReservationDTO);
        }

        List<HouseReservationDTO> reservationDTOS = new ArrayList<>();
        int i = 1;
        for (HouseReservationDTO dto: houseReservationDTOS) {
            reservationDTOS.add(dto);
            if (i % 3 == 0) {
                HouseReservationSlideDTO houseReservationSlideDTO = new HouseReservationSlideDTO(reservationDTOS);
                houseReservationSlideDTOS.add(houseReservationSlideDTO);
                reservationDTOS = new ArrayList<>();
            }
            i = i +1;
        }

        if(reservationDTOS.size() != 0){
            HouseReservationSlideDTO houseReservationSlideDTO = new HouseReservationSlideDTO(reservationDTOS);
            houseReservationSlideDTOS.add(houseReservationSlideDTO);
        }

        return new ResponseEntity<>(houseReservationSlideDTOS, HttpStatus.OK);
    }
}
