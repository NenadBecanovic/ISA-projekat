package com.application.bekend.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.mail.MessagingException;
import javax.transaction.Transactional;

import com.application.bekend.DTO.*;
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

import com.application.bekend.model.AdditionalServices;
import com.application.bekend.model.AdventureReservation;
import com.application.bekend.model.MyUser;
import com.application.bekend.service.AdditionalServicesService;
import com.application.bekend.service.FishingAdventureReservationService;
import com.application.bekend.service.FishingAdventureService;
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
        	if(!a.isAvailable() && !a.isAvailabilityPeriod()) {
	            String startDate = (String.valueOf(a.getStartDate().getTime()));
	            String endDate = (String.valueOf(a.getEndDate().getTime()));
	
	            AdventureReservationDTO adventureReservationDTO = new AdventureReservationDTO(a.getId(), startDate, endDate, a.getMaxGuests(), a.getPrice(), a.isAvailable());
	            adventureReservationDTO.setAvailabilityPeriod(a.isAvailabilityPeriod());
	            adventureReservationDTO.setIsAction(a.isAction());
                adventureReservationDTO.setCancelled(a.getCancelled());
	            if (a.getGuest() != null) {
	            	adventureReservationDTO.setGuestId(a.getGuest().getId());
	            	MyUser user = this.myUserService.findUserById(a.getGuest().getId());
	            	adventureReservationDTO.setGuest(new UserInfoDTO(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), ""));
	            }
	
	            Set<AdditionalServicesDTO> additionalServicesDTOS = new HashSet<>();
	            for(AdditionalServices add : this.additionalServicesService.getAllByHouseReservationId(a.getId())){  // a.getAdditionalServices()
	                AdditionalServicesDTO newAdditionalService = new AdditionalServicesDTO(add.getId(), add.getName(), add.getPrice());
	                additionalServicesDTOS.add(newAdditionalService);
	            }
	
	            adventureReservationDTO.setAdditionalServices(additionalServicesDTOS);
	            adventureReservationDTOS.add(adventureReservationDTO);
        	}
        }

        return new ResponseEntity<>(adventureReservationDTOS, HttpStatus.OK);
    }
    
    @PostMapping("/add")
    public ResponseEntity<AdventureReservationDTO> save(@RequestBody AdventureReservationDTO adventureReservationDTO) throws MessagingException {
        boolean isCreated = this.fishingAdventureReservationService.saveReservation(adventureReservationDTO);
        
        if(!isCreated) {
        	return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    
    @PostMapping("/saveUnavailablePeriod/{id}")
    public ResponseEntity<AdventureReservationDTO> saveUnavailablePeriod(@PathVariable("id") Long instructorId, @RequestBody AdventureReservationDTO adventureReservationDTO) throws MessagingException {
        boolean isCreated = this.fishingAdventureReservationService.saveUnavailablePeriod(instructorId,adventureReservationDTO);
        
        if(!isCreated) {
        	return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    
    @GetMapping("/getAllActionsByFishingAdventureId/{id}")
    public ResponseEntity<List<AdventureReservationDTO>> getAllActionsByAdventureId(@PathVariable("id") Long id) {
        List<AdventureReservationDTO> adventureReservationDTOS = this.fishingAdventureReservationService.getAllActionsByAdventureId(id);

        return new ResponseEntity<>(adventureReservationDTOS, HttpStatus.OK);
    }
    
    @GetMapping("/getAllActionsByInstructorId/{id}")
    public ResponseEntity<List<AdventureReservationDTO>> getAllActionsByInstructorId(@PathVariable("id") Long id) {
        List<AdventureReservationDTO> allActions = this.fishingAdventureReservationService.getAllActionsByInstructorId(id);

        return new ResponseEntity<>(allActions, HttpStatus.OK);
    }
    
    @GetMapping("/getAllAvaibilityPeriodsByInstructorId/{id}")
    public ResponseEntity<List<AdventureReservationDTO>> getAllAvaibilityPeriodsByInstructorId(@PathVariable("id") Long id) {
        List<AdventureReservationDTO> allAvaibilityPeriods = this.fishingAdventureReservationService.getAllAvaibilityPeriodsByInstructorId(id);

        return new ResponseEntity<>(allAvaibilityPeriods, HttpStatus.OK);
    }
    
    @GetMapping("/getAdventureReservationsByInstructorId/{id}")
    public ResponseEntity<List<AdventureReservationDTO>> getAdventureReservationByUserId(@PathVariable("id") Long id) {
        List<AdventureReservationDTO> adventureReservationDTOS = this.fishingAdventureReservationService.getAdventureReservationsByInstructorId(id);
       
        return new ResponseEntity<>(adventureReservationDTOS, HttpStatus.OK);
    }
    
    @DeleteMapping("/delete/{id}")
    @Transactional
    public ResponseEntity<Boolean> delete(@PathVariable("id") Long id) {
        boolean isDeleted = this.fishingAdventureReservationService.delete(id);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }


    @GetMapping("/getFishingAdventureReservationsByGuestId/{id}")
    public ResponseEntity<List<AdventureReservationDTO>> getFishingAdventureReservationsByGuestId(@PathVariable("id") Long id) {
        List<AdventureReservation> adventureReservations = this.fishingAdventureReservationService.getAdventureReservationsByGuestId(id);
        List<AdventureReservationDTO> adventureReservationDTOS = new ArrayList<>();

        for (AdventureReservation h: adventureReservations) {
            String startDate = (String.valueOf(h.getStartDate().getTime()));
            String endDate = (String.valueOf(h.getEndDate().getTime()));

            Long startDateMilis = h.getStartDate().getTime();
            Long endDateMilis = h.getEndDate().getTime();

            AdventureReservationDTO dto = new AdventureReservationDTO(h.getFishingAdventure().getId(), h.getId(), startDate, endDate, h.getMaxGuests(),
                    h.getPrice(), h.isAvailable());
            dto.setAvailabilityPeriod(h.isAvailabilityPeriod());
            dto.setIsAction(h.isAction());
            if (h.getGuest() != null) {
                dto.setGuestId(h.getGuest().getId());
            }

            dto.setMilisStartDate(startDateMilis);
            dto.setMilisEndDate(endDateMilis);
            dto.setHasAppealOwner(h.getHasAppealOwner());
            dto.setHasFeedbackOwner(h.getHasFeedbackOwner());

            dto.setTotalPrice(this.fishingAdventureReservationService.findTotalPriceForAdventureReservation(h));
            dto.setEntityName(h.getFishingAdventure().getName());
            this.fishingAdventureReservationService.canBeCancelled(dto,h);
            dto.setCancelled(h.getCancelled());

            Set<AdditionalServicesDTO> additionalServicesDTOS = new HashSet<>();
            for(AdditionalServices add : this.additionalServicesService.getAllByBoatReservationId(h.getId())) {
                AdditionalServicesDTO newAddSer = new AdditionalServicesDTO(add.getId(), add.getName(), add.getPrice());
                additionalServicesDTOS.add(newAddSer);
            }
            dto.setAdditionalServices(additionalServicesDTOS);

            adventureReservationDTOS.add(dto);
        }
        return new ResponseEntity<>(adventureReservationDTOS, HttpStatus.OK);
    }

}
