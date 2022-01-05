package com.application.bekend.controller;

import com.application.bekend.DTO.AdditionalServicesDTO;
import com.application.bekend.model.AdditionalServices;
import com.application.bekend.model.Boat;
import com.application.bekend.model.House;
import com.application.bekend.service.AdditionalServicesService;
import com.application.bekend.service.BoatService;
import com.application.bekend.service.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.*;

@RestController
@RequestMapping("api/additionalServices")
@EnableTransactionManagement
public class AdditionalServicesContoller {

    private final AdditionalServicesService additionalServicesService;
    private final HouseService houseService;
    private final BoatService boatService;

    @Autowired
    public AdditionalServicesContoller(AdditionalServicesService additionalServicesService, HouseService houseService, BoatService boatService) {
        this.additionalServicesService = additionalServicesService;
        this.houseService = houseService;
        this.boatService = boatService;
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

    @GetMapping("/getAllByBoatReservationId/{id}")
    public ResponseEntity<Set<AdditionalServicesDTO>> getAllByBoatReservationId(@PathVariable("id") Long id){
        Set<AdditionalServices> additionalServices = this.additionalServicesService.getAllByBoatReservationId(id);
        Set<AdditionalServicesDTO> additionalServicesDTOS = new HashSet<>();

        for (AdditionalServices a: additionalServices) {
            AdditionalServicesDTO additionalServicesDTO = new AdditionalServicesDTO(a.getId(), a.getName(), a.getPrice(), true);
            additionalServicesDTOS.add(additionalServicesDTO);
        }

        return new ResponseEntity<>(additionalServicesDTOS, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    @Transactional
    public ResponseEntity<Boolean> delete(@PathVariable("id") Long id) {
        AdditionalServices additionalServices = this.additionalServicesService.getAdditionalServicesById(id);
        additionalServices.setHouses(null);
        additionalServices.setHouseReservationsServices(null);

        additionalServices.setBoats(null);
        additionalServices.setBoatReservationsServices(null);

        this.additionalServicesService.deleteById(id);  // brisanje iz additional_services

        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<AdditionalServices> save(@RequestBody AdditionalServicesDTO dto) {
        AdditionalServices additionalServices = new AdditionalServices(dto.getId(), dto.getName(), dto.getPrice(), new HashSet<>(), new HashSet<>());
        House house = this.houseService.getHouseById(dto.getHouseId());
        Boat boat = this.boatService.getBoatById(dto.getBoatId());

        if (house != null) {
            Set<House> houses = additionalServices.getHouses();
            houses.add(house);
            additionalServices.setHouses(houses);
        }
        else if (boat != null) {
            Set<Boat> boats = additionalServices.getBoats();
            boats.add(boat);
            additionalServices.setBoats(boats);
        }

        this.additionalServicesService.save(additionalServices);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
