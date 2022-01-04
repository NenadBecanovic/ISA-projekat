package com.application.bekend.controller;

import com.application.bekend.DTO.*;
import com.application.bekend.model.AdditionalServices;
import com.application.bekend.model.House;
import com.application.bekend.model.HouseReservation;
import com.application.bekend.service.AdditionalServicesService;
import com.application.bekend.service.HouseReservationService;
import com.application.bekend.service.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.*;

@RestController
@RequestMapping("api/houseReservations")
@EnableTransactionManagement
public class HouseReservationController {

    private final HouseReservationService houseReservationService;
    private final HouseService houseService;
    private final AdditionalServicesService additionalServicesService;

    @Autowired
    public HouseReservationController(HouseReservationService houseReservationService, HouseService houseService, AdditionalServicesService additionalServicesService) {
        this.houseReservationService = houseReservationService;
        this.houseService = houseService;
        this.additionalServicesService = additionalServicesService;
    }

    @GetMapping("/getAllByHouseId/{id}")
    public ResponseEntity<List<HouseReservationSlideDTO>> getAllByHouseId(@PathVariable("id") Long id) {
        List<HouseReservation> houseReservations = this.houseReservationService.getAllByHouse_Id(id);

        List<HouseReservationDTO> houseReservationDTOS = new ArrayList<>();
        List<HouseReservationSlideDTO> houseReservationSlideDTOS = new ArrayList<>();

        for (HouseReservation a : houseReservations) {
            String startDate = (String.valueOf(a.getStartDate().getTime()));
            String endDate = (String.valueOf(a.getEndDate().getTime()));

            HouseReservationDTO houseReservationDTO = new HouseReservationDTO(a.getId(), startDate, endDate, a.getMaxGuests(), a.getPrice(), a.isAvailable());

            Set<AdditionalServicesDTO> additionalServicesDTOS = new HashSet<>();
            // dobavljamo set dodatnih usluga za onu konkretnu rezervaciju iz baze i pretvaramo u DTO (a mozemo samo i pristupiti setu dodatnih usluga direktno preko rezervacije (a.getAdditionalServices()))
            for(AdditionalServices add : this.additionalServicesService.getAllByHouseReservationId(a.getId())){  // a.getAdditionalServices()
                AdditionalServicesDTO newAddSer = new AdditionalServicesDTO(add.getId(), add.getName(), add.getPrice());
                additionalServicesDTOS.add(newAddSer);
            }

            houseReservationDTO.setAdditionalServices(additionalServicesDTOS);
            houseReservationDTOS.add(houseReservationDTO);  // lista svih HouseReservationDTO - treba nam zbog slidera (3 po 3 cemo slati)
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

    @PostMapping("/add")
    public ResponseEntity<HouseReservation> save(@RequestBody HouseReservationDTO dto) {
        House house = this.houseService.getHouseById(dto.getHouseId());

        Date startDate = new Date(Long.parseLong(dto.getStartDate()));
        Date endDate = new Date(Long.parseLong(dto.getEndDate()));
        HouseReservation houseReservation = new HouseReservation(dto.getId(), startDate, endDate, dto.getMaxGuests(), dto.getPrice(), dto.isAvailable(), house);

        houseReservation = this.houseReservationService.save(houseReservation); // sacuvali smo rezervaciju i povratna vrednost metode je tacno ta rezervacija iz baze (sa ispravno generisanim id-em ...)
        // ovaj korak je obavezan jer se rezervacija koju dodajemo ovde (***) mora nalaziti u bazi

        Set<AdditionalServices> additionalServicesSet = new HashSet<>();
        for(AdditionalServicesDTO add : dto.getAdditionalServices()){

            // iz baze dobavljamo (original) dodatnu uslugu i u njen set rezervacija, dodajemo ovu konkretnu rezervaciju (houseReservation)
            AdditionalServices additionalServices = this.additionalServicesService.getAdditionalServicesById(add.getId());

            additionalServices.addHouseReservation(houseReservation); // (***)
            // da je bio slucaj da smo dodali samo inicijalno kreiran houseReservation (nastao iz podataka od DTO), bio bi error: javax.persistence.EntityNotFoundException
            // jer u tabeli koja spaja AdditionalServices (id_a) i HouseReservation (id_h), id_h bi bio null i to vraca gresku, jer se u tabeli mora nalaziti neki vec postojeci id_h (radimo spajanje podataka dve postojece table, nema novih podataka)

            additionalServicesSet.add(additionalServices);   // u set koji cemo kasnije dodeliti rezervaciji dodajemo dodatnu uslugu

            // azuriramo (sacuvamo) izmenjenu dodatnu uslugu u bazi (additional service)
            this.additionalServicesService.save(additionalServices);
        }

        // nepotrebno, vec smo sacuvali u bazi
//        houseReservation.setAdditionalServices(additionalServicesSet);
//        houseReservation = this.houseReservationService.save(houseReservation); // sacuvana rezervacija vikenice u bazi

        // dodajem rezervaciju vikendice u samu vikendicu
        house.addHouseReservation(houseReservation);
        this.houseService.save(house);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // kod brisanja:
    // mora biti Transactional metoda + EnableTransactionManagement klasa
    @DeleteMapping("/delete/{id}")
    @Transactional
    public ResponseEntity<Boolean> delete(@PathVariable("id") Long id) {
        HouseReservation houseReservation = this.houseReservationService.getHouseReservationById(id);   // dobavimo rezervaciju iz baze

        Set<AdditionalServices> additionalServices =  houseReservation.getAdditionalServices();     // ne moramo direktno iz baze dobavljati jer ova lista u sebi ima objekte sa svojim pravim id-jevima
        for(AdditionalServices a: additionalServices){
            a.getHouseReservationsServices().remove(houseReservation);  // iz niza rezervacija dodatnih usluga izbacimo ovu rezervaciju koju brisemo - raskinuta u tabeli additional_services_house_reservation (sa vodece strane, jer je kod AdditionalService JoinTable)
            this.additionalServicesService.save(a);
        }

        // nepotrebno
//        houseReservation.setAdditionalServices(null);   // raskinuta veza s druge strane u tabeli additional_services_house_reservation
//        houseReservation = this.houseReservationService.save(houseReservation);

        houseReservation.setHouse(null);  // raskinuta veza u tabeli house_reservation_table (sa strane vodece veze u ManyToMany vezi)
        houseReservation = this.houseReservationService.save(houseReservation);

        this.houseReservationService.delete(houseReservation.getId());  // brisanje rezervacije iz house_reservation tabele

        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @GetMapping("/getHouseReservationById/{id}")
    public ResponseEntity<HouseReservationDTO> getHouseReservationById(@PathVariable("id") Long id) {
        HouseReservation houseReservation = this.houseReservationService.getHouseReservationById(id);

        HouseReservationDTO dto = new HouseReservationDTO(houseReservation.getId(), houseReservation.getStartDate().toString(), houseReservation.getEndDate().toString(),
                houseReservation.getMaxGuests(), houseReservation.getPrice(), houseReservation.isAvailable());

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
}