package com.application.bekend.controller;

import com.application.bekend.DTO.AdditionalServicesDTO;
import com.application.bekend.model.AdditionalServices;
import com.application.bekend.model.House;
import com.application.bekend.service.AdditionalServicesService;
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

    @Autowired
    public AdditionalServicesContoller(AdditionalServicesService additionalServicesService, HouseService houseService) {
        this.additionalServicesService = additionalServicesService;
        this.houseService = houseService;
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

    @DeleteMapping("/delete/{id}")
    @Transactional
    public ResponseEntity<Boolean> delete(@PathVariable("id") Long id) {
        AdditionalServices additionalServices = this.additionalServicesService.getAdditionalServicesById(id);
        additionalServices.setHouses(null);
        additionalServices.setHouseReservationsServices(null);

        this.additionalServicesService.deleteById(id);  // brisanje iz additional_services

        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<AdditionalServices> save(@RequestBody AdditionalServicesDTO dto) {
        AdditionalServices additionalServices = new AdditionalServices(dto.getId(), dto.getName(), dto.getPrice(), new HashSet<>());

        House house = this.houseService.getHouseById(dto.getHouseId());

        Set<House> houses = additionalServices.getHouses();
        houses.add(house);
        additionalServices.setHouses(houses);
        this.additionalServicesService.save(additionalServices);

//
//        Date startDate = new Date(Long.parseLong(dto.getStartDate()));
//        Date endDate = new Date(Long.parseLong(dto.getEndDate()));
//        HouseReservation houseReservation = new HouseReservation(dto.getId(), startDate, endDate, dto.getMaxGuests(), dto.getPrice(), dto.isAvailable(), house);
//
//        houseReservation = this.houseReservationService.save(houseReservation); // sacuvali smo rezervaciju i povratna vrednost metode je tacno ta rezervacija iz baze (sa ispravno generisanim id-em ...)
//        // ovaj korak je obavezan jer se rezervacija koju dodajemo ovde (***) mora nalaziti u bazi
//
//        Set<AdditionalServices> additionalServicesSet = new HashSet<>();
//        for(AdditionalServicesDTO add : dto.getAdditionalServices()){
//
//            // iz baze dobavljamo (original) dodatnu uslugu i u njen set rezervacija, dodajemo ovu konkretnu rezervaciju (houseReservation)
//            AdditionalServices additionalServices = this.additionalServicesService.getAdditionalServicesById(add.getId());
//
//            additionalServices.addHouseReservation(houseReservation); // (***)
//            // da je bio slucaj da smo dodali samo inicijalno kreiran houseReservation (nastao iz podataka od DTO), bio bi error: javax.persistence.EntityNotFoundException
//            // jer u tabeli koja spaja AdditionalServices (id_a) i HouseReservation (id_h), id_h bi bio null i to vraca gresku, jer se u tabeli mora nalaziti neki vec postojeci id_h (radimo spajanje podataka dve postojece table, nema novih podataka)
//
//            additionalServicesSet.add(additionalServices);   // u set koji cemo kasnije dodeliti rezervaciji dodajemo dodatnu uslugu
//
//            // azuriramo (sacuvamo) izmenjenu dodatnu uslugu u bazi (additional service)
//            this.additionalServicesService.save(additionalServices);
//        }
////
//        // dodajem rezervaciju vikendice u samu vikendicu
//        house.addHouseReservation(houseReservation);
//        this.houseService.save(house);
//
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
