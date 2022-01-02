package com.application.bekend.controller;

import com.application.bekend.DTO.*;
import com.application.bekend.model.House;
import com.application.bekend.model.HouseReservation;
import com.application.bekend.service.HouseReservationService;
import com.application.bekend.service.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("api/houseReservations")
public class HouseReservationController {

    private final HouseReservationService houseReservationService;
    private final HouseService houseService;

    @Autowired
    public HouseReservationController(HouseReservationService houseReservationService, HouseService houseService) {
        this.houseReservationService = houseReservationService;
        this.houseService = houseService;
    }

    @GetMapping("/getAllByHouseId/{id}")
    public ResponseEntity<List<HouseReservationSlideDTO>> getAllByHouseId(@PathVariable("id") Long id) {
        List<HouseReservation> houseReservations = this.houseReservationService.getAllByHouse_Id(id);
        List<HouseReservationDTO> houseReservationDTOS = new ArrayList<>();
        List<HouseReservationSlideDTO> houseReservationSlideDTOS = new ArrayList<>();

        for (HouseReservation a : houseReservations) {
            String startDate = (String.valueOf(a.getStartDate().getTime()));
            String endDate = (String.valueOf(a.getEndDate().getTime()));

            HouseReservationDTO houseReservationDTO = new HouseReservationDTO(a.getId(), startDate, endDate, a.getMaxGuests(), a.getAdditionalServices(), a.getPrice(), a.isAvailable());
            houseReservationDTOS.add(houseReservationDTO);
        }

        List<HouseReservationDTO> reservationDTOS = new ArrayList<>();
        int i = 1;
        for (HouseReservationDTO dto : houseReservationDTOS) {
            reservationDTOS.add(dto);
            if (i % 3 == 0) {
                HouseReservationSlideDTO houseReservationSlideDTO = new HouseReservationSlideDTO(reservationDTOS);
                houseReservationSlideDTOS.add(houseReservationSlideDTO);
                reservationDTOS = new ArrayList<>();
            }
            i = i + 1;
        }

        if (reservationDTOS.size() != 0) {
            HouseReservationSlideDTO houseReservationSlideDTO = new HouseReservationSlideDTO(reservationDTOS);
            houseReservationSlideDTOS.add(houseReservationSlideDTO);
        }

        return new ResponseEntity<>(houseReservationSlideDTOS, HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<HouseReservation> save(@RequestBody HouseReservationDTO dto) {
        House house = this.houseService.getHouseById(dto.getHouseId());
        Date startDate = new Date(Long.parseLong(dto.getStartDate()));
        Date endDate = new Date(Long.parseLong(dto.getEndDate()));

        HouseReservation houseReservation = new HouseReservation(dto.getId(), startDate, endDate, dto.getMaxGuests(),
                dto.getAdditionalServices(), dto.getPrice(), dto.isAvailable(), house);

        houseReservation = this.houseReservationService.save(houseReservation);        // sacuvana rezervacija vikenice u bazi

        // dodajem rezervaciju vikendice u samu vikendicu
        house.addHouseReservation(houseReservation);
        this.houseService.save(house);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
