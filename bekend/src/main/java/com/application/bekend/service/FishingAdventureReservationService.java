package com.application.bekend.service;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.application.bekend.DTO.AdditionalServicesDTO;
import com.application.bekend.DTO.AdventureReservationDTO;
import com.application.bekend.model.AdditionalServices;
import com.application.bekend.model.AdventureReservation;
import com.application.bekend.model.FishingAdventure;
import com.application.bekend.model.House;
import com.application.bekend.model.HouseReservation;
import com.application.bekend.model.MyUser;
import com.application.bekend.repository.FishingAdventureReservationRepository;

@Service
public class FishingAdventureReservationService {
	
	private final FishingAdventureReservationRepository fishingAdventureReservationsRepository;
	private final FishingAdventureService fishingAdventureService;
	private final AdditionalServicesService additionalServicesService;
	private final MyUserService myUserService;
	
	@Autowired
	public FishingAdventureReservationService(FishingAdventureReservationRepository fishingAdventureReservationRepository, FishingAdventureService fishingAdventureService, AdditionalServicesService additionalServicesService,
			MyUserService myUserService) {
		this.fishingAdventureReservationsRepository = fishingAdventureReservationRepository;
		this.fishingAdventureService = fishingAdventureService;
		this.additionalServicesService = additionalServicesService;
		this.myUserService = myUserService;
	}
	
	public List<AdventureReservation> getAllByFishingAdventure_Id(Long id) {
        return fishingAdventureReservationsRepository.getAllByFishingAdventure_Id(id);
    }
	
	public AdventureReservation save(AdventureReservation adventureReservation) {
        return this.fishingAdventureReservationsRepository.save(adventureReservation);
    }
	
	public boolean saveReservation(AdventureReservationDTO adventureReservationDTO) {
		FishingAdventure fishingAdventure = this.fishingAdventureService.getFishingAdventureById(adventureReservationDTO.getAdventureId());
        List<AdventureReservation> adventureReservations = this.getAllByFishingAdventure_Id(fishingAdventure.getId());
        for (AdventureReservation a: adventureReservations) {
            Long start =  a.getStartDate().getTime();
            Long end = a.getEndDate().getTime();

            if (Long.parseLong(adventureReservationDTO.getStartDate()) >= start && Long.parseLong(adventureReservationDTO.getEndDate()) <=  end ||
                    Long.parseLong(adventureReservationDTO.getStartDate()) <= start && Long.parseLong(adventureReservationDTO.getEndDate()) >= start  ||
                    Long.parseLong(adventureReservationDTO.getStartDate()) >= start && Long.parseLong(adventureReservationDTO.getStartDate()) <= end  )
            {
                return false;
            }
        }

        Date startDate = new Date(Long.parseLong(adventureReservationDTO.getStartDate()));
        Date endDate = new Date(Long.parseLong(adventureReservationDTO.getEndDate()));
        AdventureReservation adventureReservation = new AdventureReservation(adventureReservationDTO.getId(), startDate, endDate, adventureReservationDTO.getMaxGuests(), adventureReservationDTO.getPrice(), adventureReservationDTO.getIsAvailable(), fishingAdventure);
        adventureReservation.setAvailabilityPeriod(adventureReservationDTO.getIsAvailabilityPeriod());
        adventureReservation.setAction(adventureReservationDTO.getIsAction());
        adventureReservation = this.save(adventureReservation); 

        Set<AdditionalServices> additionalServicesSet = new HashSet<>();
        for(AdditionalServicesDTO add : adventureReservationDTO.getAdditionalServices()){
            AdditionalServices additionalServices = this.additionalServicesService.getAdditionalServicesById(add.getId());
            additionalServices.addAdventureReservation(adventureReservation);
            additionalServicesSet.add(additionalServices);  
            this.additionalServicesService.save(additionalServices);
        }
        fishingAdventure.addAdventureReservation(adventureReservation);
        this.fishingAdventureService.save(fishingAdventure);

        if (adventureReservationDTO.getGuestId() != null && adventureReservationDTO.getGuestId() != 0) {
            MyUser guest = this.myUserService.findUserById(adventureReservationDTO.getGuestId());
            adventureReservation.setGuest(guest);
            this.fishingAdventureService.save(fishingAdventure);

            Set<AdventureReservation> adventureReservationsGuest = guest.getAdventureReservations();
            adventureReservationsGuest.add(adventureReservation);
            guest.setAdventureReservations(adventureReservationsGuest);
            this.myUserService.save(guest);
        }
        return true;
	}

	public boolean saveUnavailablePeriod(Long instructorId, AdventureReservationDTO adventureReservationDTO) {
		// TODO Auto-generated method stub
		List<FishingAdventure> fishingAdventures = this.fishingAdventureService.getFishingAdventuresByInstructor(instructorId);
		for(FishingAdventure fishingAdventure : fishingAdventures) {
	        adventureReservationDTO.setAdventureId(fishingAdventure.getId());
	        if(!saveReservation(adventureReservationDTO)) {
	        	return false;
	        }
		}
        return true;
	}
	
	public Long getCurrentGuest(Date currentDateAndTime, Long instructorId) {
		//return this.fishingAdventureReservationsRepository.getAllByCurrentGuest(currentDateAndTime, instructorId);
		return null;
	}
}
