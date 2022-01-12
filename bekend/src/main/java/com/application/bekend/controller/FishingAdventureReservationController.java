package com.application.bekend.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.application.bekend.DTO.AdditionalServicesDTO;
import com.application.bekend.DTO.AdventureReservationDTO;
import com.application.bekend.DTO.HouseReservationDTO;
import com.application.bekend.DTO.HouseReservationSlideDTO;
import com.application.bekend.model.AdditionalServices;
import com.application.bekend.model.Address;
import com.application.bekend.model.AdventureReservation;
import com.application.bekend.model.House;
import com.application.bekend.model.HouseReservation;
import com.application.bekend.model.MyUser;
import com.application.bekend.service.AdditionalServicesService;
import com.application.bekend.service.FishingAdventureReservationService;
import com.application.bekend.service.FishingAdventureService;
import com.application.bekend.service.HouseReservationService;
import com.application.bekend.service.HouseService;
import com.application.bekend.service.MyUserService;

@RestController
@RequestMapping("api/fishingAdventureReservations")
@EnableTransactionManagement
public class FishingAdventureReservationController {

	private final FishingAdventureReservationService fishingAdventureReservationService;
    private final FishingAdventureService fishingAdventureService;
    private final AdditionalServicesService additionalServicesService;
    private final MyUserService myUserService;
    
    @Autowired
    public FishingAdventureReservationController(FishingAdventureReservationService fishingAdventureReservationService, FishingAdventureService fishingAdventureService, AdditionalServicesService additionalServicesService, MyUserService myUserService) {
    	this.fishingAdventureReservationService = fishingAdventureReservationService;
    	this.fishingAdventureService = fishingAdventureService;
    	this.additionalServicesService = additionalServicesService;
    	this.myUserService = myUserService;
    }
    
    @GetMapping("/getAllByAdventureId/{id}")
    public ResponseEntity<List<AdventureReservationDTO>> getAllByAdventureId(@PathVariable("id") Long id) {
        List<AdventureReservation> adventureReservations = this.fishingAdventureReservationService.getAllByFishingAdventure_Id(id);

        List<AdventureReservationDTO> adventureReservationDTOS = new ArrayList<>();

        for (AdventureReservation a : adventureReservations) {
            String startDate = (String.valueOf(a.getStartDate().getTime()));
            String endDate = (String.valueOf(a.getEndDate().getTime()));

            AdventureReservationDTO adventureReservationDTO = new AdventureReservationDTO(a.getId(), startDate, endDate, a.getMaxGuests(), a.getPrice(), a.isAvailable());
            adventureReservationDTO.setIAvailabilityPeriod(a.isAvailabilityPeriod());
            adventureReservationDTO.setIsAction(a.isAction());
            if (a.getGuest() != null) {
            	adventureReservationDTO.setGuestId(a.getGuest().getId());
            }

            Set<AdditionalServicesDTO> additionalServicesDTOS = new HashSet<>();
            for(AdditionalServices add : this.additionalServicesService.getAllByHouseReservationId(a.getId())){  // a.getAdditionalServices()
                AdditionalServicesDTO newAdditionalService = new AdditionalServicesDTO(add.getId(), add.getName(), add.getPrice());
                additionalServicesDTOS.add(newAdditionalService);
            }

            adventureReservationDTO.setAdditionalServices(additionalServicesDTOS);
            adventureReservationDTOS.add(adventureReservationDTO);  
        }

        return new ResponseEntity<>(adventureReservationDTOS, HttpStatus.OK);
    }
    
    @PostMapping("/add")
    public ResponseEntity<AdventureReservationDTO> save(@RequestBody AdventureReservationDTO adventureReservationDTO) {
        boolean isCreated = this.fishingAdventureReservationService.saveReservation(adventureReservationDTO);
        
        if(!isCreated) {
        	return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        // TODO: ako je vlasnik zakazao za klijenta, poslati mejl klijentu

        // TODO: ako je akcije, poslati mejl svim pretplacenim klijentima

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    
    @PostMapping("/saveUnavailablePeriod/{id}")
    public ResponseEntity<AdventureReservationDTO> saveUnavailablePeriod(@PathVariable("id") Long instructorId, @RequestBody AdventureReservationDTO adventureReservationDTO) {
        boolean isCreated = this.fishingAdventureReservationService.saveUnavailablePeriod(instructorId,adventureReservationDTO);
        
        if(!isCreated) {
        	return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        // TODO: ako je vlasnik zakazao za klijenta, poslati mejl klijentu

        // TODO: ako je akcije, poslati mejl svim pretplacenim klijentima

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    
    @GetMapping("/getAllActionsByFishingAdventureId/{id}")
    public ResponseEntity<List<AdventureReservationDTO>> getAllActionsByHouseId(@PathVariable("id") Long id) {
        List<AdventureReservation> adventureReservations = this.fishingAdventureReservationService.getAllByFishingAdventure_Id(id);

        List<AdventureReservationDTO> adventureReservationDTOS = new ArrayList<>();

        for (AdventureReservation a : adventureReservations) {
            if (a.isAction() && a.isAvailable()) {  
                String startDate = (String.valueOf(a.getStartDate().getTime()));
                String endDate = (String.valueOf(a.getEndDate().getTime()));

                AdventureReservationDTO aventureReservationDTO = new AdventureReservationDTO(a.getId(), startDate, endDate, a.getMaxGuests(), a.getPrice(), a.isAvailable());
                aventureReservationDTO.setIAvailabilityPeriod(a.isAvailabilityPeriod());
                aventureReservationDTO.setIsAction(a.isAction());
                if (a.getGuest() != null) {
                	aventureReservationDTO.setGuestId(a.getGuest().getId());
                }

                Set<AdditionalServicesDTO> additionalServicesDTOS = new HashSet<>();
                // dobavljamo set dodatnih usluga za onu konkretnu rezervaciju iz baze i pretvaramo u DTO (a mozemo samo i pristupiti setu dodatnih usluga direktno preko rezervacije (a.getAdditionalServices()))
                for (AdditionalServices add : this.additionalServicesService.getAllByAdventureReservationId(a.getId())) {  // a.getAdditionalServices()
                    AdditionalServicesDTO newAddSer = new AdditionalServicesDTO(add.getId(), add.getName(), add.getPrice());
                    additionalServicesDTOS.add(newAddSer);
                }

                aventureReservationDTO.setAdditionalServices(additionalServicesDTOS);
                adventureReservationDTOS.add(aventureReservationDTO);  // lista svih HouseReservationDTO - treba nam zbog slidera (3 po 3 cemo slati)
            }
        }

        return new ResponseEntity<>(adventureReservationDTOS, HttpStatus.OK);
    }
    
    @GetMapping("/getAdventureReservationsByInstructorId/{id}")
    public ResponseEntity<List<AdventureReservationDTO>> getHouseReservationByUserId(@PathVariable("id") Long id) {
        List<AdventureReservation> houseReservations = this.fishingAdventureReservationService.getAdventureReservationsByInstructorId(id);
        List<AdventureReservationDTO> adventureReservationDTOS = new ArrayList<AdventureReservationDTO>();
/*
        for(HouseReservation houseReservation: houseReservations){
            HouseReservationDTO dto = new HouseReservationDTO(houseReservation.getId(), houseReservation.getStartDate().toString(), houseReservation.getEndDate().toString(),
                    houseReservation.getMaxGuests(), houseReservation.getPrice(), houseReservation.isAvailable());
            houseReservationDTOS.add(dto);
        }
*/
        return new ResponseEntity<>(adventureReservationDTOS, HttpStatus.OK);
    }
    
/*
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

        houseReservation.setGuest(null);    // TODO: proveriti kad se dodaju gosti sa rezervacijama
        houseReservation.setHouse(null);  // raskinuta veza u tabeli house_reservation_table (sa strane vodece veze u ManyToMany vezi)
        houseReservation = this.houseReservationService.save(houseReservation);

        this.houseReservationService.delete(houseReservation.getId());  // brisanje rezervacije iz house_reservation tabele

        return new ResponseEntity<>(true, HttpStatus.OK);
    }*/
}
