package com.application.bekend.controller;

import com.application.bekend.DTO.*;
import com.application.bekend.model.*;
import com.application.bekend.service.AdditionalServicesService;
import com.application.bekend.service.BoatReservationService;
import com.application.bekend.service.BoatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.*;

@RestController
@RequestMapping("api/boatReservations")
@EnableTransactionManagement
public class BoatReservationController {

    private final BoatReservationService boatReservationService;
    private final AdditionalServicesService additionalServicesService;
    private final BoatService boatService;

    @Autowired
    public BoatReservationController(BoatReservationService boatReservationService, AdditionalServicesService additionalServicesService, BoatService boatService) {
        this.boatReservationService = boatReservationService;
        this.additionalServicesService = additionalServicesService;
        this.boatService = boatService;
    }

    @GetMapping("/getAllByBoatId/{id}")
    public ResponseEntity<List<BoatReservationSlideDTO>> getAllByBoatId(@PathVariable("id") Long id){
        List<BoatReservation> boatReservations = this.boatReservationService.getAllByBoat_Id(id);

        List<BoatReservationDTO> boatReservationDTOS = new ArrayList<>();
        List<BoatReservationSlideDTO> boatReservationSlideDTOS = new ArrayList<>();

        for (BoatReservation a: boatReservations) {
            String startDate = (String.valueOf(a.getStartDate().getTime()));
            String endDate = (String.valueOf(a.getEndDate().getTime()));

            BoatReservationDTO boatReservationDTO = new BoatReservationDTO(a.getId(), startDate, endDate, a.getMaxGuests(), a.getPrice(), a.isAvailable());

            Set<AdditionalServicesDTO> additionalServicesDTOS = new HashSet<>();
            for(AdditionalServices add : this.additionalServicesService.getAllByBoatReservationId(a.getId())){  // a.getAdditionalServices()
                AdditionalServicesDTO newAddSer = new AdditionalServicesDTO(add.getId(), add.getName(), add.getPrice());
                additionalServicesDTOS.add(newAddSer);
            }

            boatReservationDTO.setAdditionalServices(additionalServicesDTOS);
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

    @PostMapping("/add")
    public ResponseEntity<BoatReservation> save(@RequestBody BoatReservationDTO dto) {
        Boat boat = this.boatService.getBoatById(dto.getBoatId());

        Date startDate = new Date(Long.parseLong(dto.getStartDate()));
        Date endDate = new Date(Long.parseLong(dto.getEndDate()));
        BoatReservation boatReservation = new BoatReservation(dto.getId(), startDate, endDate, dto.getMaxGuests(), dto.getPrice(), dto.isAvailable(), boat);

        boatReservation = this.boatReservationService.save(boatReservation); // sacuvali smo rezervaciju i povratna vrednost metode je tacno ta rezervacija iz baze (sa ispravno generisanim id-em ...)
        // ovaj korak je obavezan jer se rezervacija koju dodajemo ovde (***) mora nalaziti u bazi

        Set<AdditionalServices> additionalServicesSet = new HashSet<>();
        for(AdditionalServicesDTO add : dto.getAdditionalServices()){

            // iz baze dobavljamo (original) dodatnu uslugu i u njen set rezervacija, dodajemo ovu konkretnu rezervaciju (houseReservation)
            AdditionalServices additionalServices = this.additionalServicesService.getAdditionalServicesById(add.getId());

            additionalServices.addBoatReservation(boatReservation); // (***)
            // da je bio slucaj da smo dodali samo inicijalno kreiran houseReservation (nastao iz podataka od DTO), bio bi error: javax.persistence.EntityNotFoundException
            // jer u tabeli koja spaja AdditionalServices (id_a) i HouseReservation (id_h), id_h bi bio null i to vraca gresku, jer se u tabeli mora nalaziti neki vec postojeci id_h (radimo spajanje podataka dve postojece table, nema novih podataka)

            additionalServicesSet.add(additionalServices);   // u set koji cemo kasnije dodeliti rezervaciji dodajemo dodatnu uslugu

            // azuriramo (sacuvamo) izmenjenu dodatnu uslugu u bazi (additional service)
            this.additionalServicesService.save(additionalServices);
        }

        // dodajem rezervaciju vikendice u samu vikendicu
        boat.addBoatReservation(boatReservation);
        this.boatService.save(boat);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    @Transactional
    public ResponseEntity<Boolean> delete(@PathVariable("id") Long id) {
        BoatReservation boatReservation = this.boatReservationService.getBoatReservationById(id);   // dobavimo rezervaciju iz baze

        Set<AdditionalServices> additionalServices =  boatReservation.getAdditionalServices();     // ne moramo direktno iz baze dobavljati jer ova lista u sebi ima objekte sa svojim pravim id-jevima
        for(AdditionalServices a: additionalServices){
            a.getBoatReservationsServices().remove(boatReservation);  // iz niza rezervacija dodatnih usluga izbacimo ovu rezervaciju koju brisemo - raskinuta u tabeli additional_services_house_reservation (sa vodece strane, jer je kod AdditionalService JoinTable)
            this.additionalServicesService.save(a);
        }

        boatReservation.setBoat(null);  // raskinuta veza u tabeli house_reservation_table (sa strane vodece veze u ManyToMany vezi)
        boatReservation = this.boatReservationService.save(boatReservation);

        this.boatReservationService.delete(boatReservation.getId());  // brisanje rezervacije iz house_reservation tabele

        return new ResponseEntity<>(true, HttpStatus.OK);
    }
}
