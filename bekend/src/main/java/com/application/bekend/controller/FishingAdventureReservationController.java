package com.application.bekend.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.EnableTransactionManagement;
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
            adventureReservationDTO.setAvailabilityPeriod(a.isAvailabilityPeriod());
            adventureReservationDTO.setAction(a.isAction());
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
}
