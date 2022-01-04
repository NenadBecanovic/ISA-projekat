package com.application.bekend.controller;

import com.application.bekend.DTO.AdditionalServicesDTO;
import com.application.bekend.DTO.RoomDTO;
import com.application.bekend.model.AdditionalServices;
import com.application.bekend.model.Room;
import com.application.bekend.service.AdditionalServicesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("api/additionalServices")
public class AdditionalServicesContoller {

    private final AdditionalServicesService additionalServicesService;

    @Autowired
    public AdditionalServicesContoller(AdditionalServicesService additionalServicesService) {
        this.additionalServicesService = additionalServicesService;
    }

    @GetMapping("/getAllByHouseId/{id}")
    public ResponseEntity<List<AdditionalServicesDTO>> getAllByHouseId(@PathVariable("id") Long id){
        List<AdditionalServices> additionalServices = this.additionalServicesService.getAllByHouseId(id);
        List<AdditionalServicesDTO> additionalServicesDTOS = new ArrayList<>();

        for (AdditionalServices a: additionalServices) {
            AdditionalServicesDTO additionalServicesDTO = new AdditionalServicesDTO(a.getId(), a.getName(), a.getPrice());
            additionalServicesDTOS.add(additionalServicesDTO);
        }

        return new ResponseEntity<>(additionalServicesDTOS, HttpStatus.OK);
    }

    @GetMapping("/getAllByBoatId/{id}")
    public ResponseEntity<List<AdditionalServicesDTO>> getAllByBoatId(@PathVariable("id") Long id){
        List<AdditionalServices> additionalServices = this.additionalServicesService.getAllByBoatId(id);
        List<AdditionalServicesDTO> additionalServicesDTOS = new ArrayList<>();

        for (AdditionalServices a: additionalServices) {
            AdditionalServicesDTO additionalServicesDTO = new AdditionalServicesDTO(a.getId(), a.getName(), a.getPrice());
            additionalServicesDTOS.add(additionalServicesDTO);
        }

        return new ResponseEntity<>(additionalServicesDTOS, HttpStatus.OK);
    }

    @GetMapping("/getAllByHouseReservationId/{id}")
    public ResponseEntity<Set<AdditionalServicesDTO>> getAllByHouseReservationId(@PathVariable("id") Long id){
        Set<AdditionalServices> additionalServices = this.additionalServicesService.getAllByHouseReservationId(id);
        Set<AdditionalServicesDTO> additionalServicesDTOS = new HashSet<>();

        for (AdditionalServices a: additionalServices) {
            // TODO: proveriti ovaj deo checked u kostruktoru
            AdditionalServicesDTO additionalServicesDTO = new AdditionalServicesDTO(a.getId(), a.getName(), a.getPrice(), true);
            additionalServicesDTOS.add(additionalServicesDTO);
        }

        return new ResponseEntity<>(additionalServicesDTOS, HttpStatus.OK);
    }

}
