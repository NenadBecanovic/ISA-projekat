package com.application.bekend.service;
import java.util.ArrayList;
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
import com.application.bekend.DTO.UserInfoDTO;
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
	
	public AdventureReservation getFishingAdventureReservationById(Long id) {
        return this.fishingAdventureReservationsRepository.getFishingAdventureReservationById(id);
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
	
	public List<AdventureReservationDTO> getAdventureReservationsByInstructorId(Long id){
		List<AdventureReservation> allreservations =  this.fishingAdventureReservationsRepository.getAdventureReservationsByInstructorId(id);
		List<AdventureReservationDTO> adventureReservationDTOS = new ArrayList<>();

        for (AdventureReservation a : allreservations) {
        	if(!a.isAvailable() && !a.isAvailabilityPeriod()) {
	            String startDate = (String.valueOf(a.getStartDate().getTime()));
	            String endDate = (String.valueOf(a.getEndDate().getTime()));
	
	            AdventureReservationDTO adventureReservationDTO = new AdventureReservationDTO(a.getId(), startDate, endDate, a.getMaxGuests(), a.getPrice(), a.isAvailable());
	            adventureReservationDTO.setIAvailabilityPeriod(a.isAvailabilityPeriod());
	            adventureReservationDTO.setIsAction(a.isAction());
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
        return adventureReservationDTOS;
	}
	
	public boolean delete(Long id) {
		AdventureReservation adventureReservation = this.getFishingAdventureReservationById(id);   // dobavimo rezervaciju iz baze

        Set<AdditionalServices> additionalServices =  adventureReservation.getAdditionalServices();     // ne moramo direktno iz baze dobavljati jer ova lista u sebi ima objekte sa svojim pravim id-jevima
        for(AdditionalServices a: additionalServices){
            a.getAdventureReservationsServices().remove(adventureReservation);  // iz niza rezervacija dodatnih usluga izbacimo ovu rezervaciju koju brisemo - raskinuta u tabeli additional_services_house_reservation (sa vodece strane, jer je kod AdditionalService JoinTable)
            this.additionalServicesService.save(a);
        }

        adventureReservation.setGuest(null); // TODO: proveriit
        adventureReservation.setFishingAdventure(null);  // raskinuta veza u tabeli house_reservation_table (sa strane vodece veze u ManyToMany vezi)
        adventureReservation = this.save(adventureReservation);
        //this.delete(adventureReservation.getId());  // brisanje rezervacije iz house_reservation tabele
        return true;
	}

	public List<AdventureReservationDTO> getAllActionsByAdventureId(Long id) {
		List<AdventureReservation> adventureReservations = this.getAllByFishingAdventure_Id(id);

        List<AdventureReservationDTO> adventureReservationDTOS = new ArrayList<>();

        for (AdventureReservation a : adventureReservations) {
            if (a.isAction() && a.isAvailable()) {  
                Long startDate = a.getStartDate().getTime();
                Long endDate = a.getEndDate().getTime();
                Long today = new Date().getTime();

                if (startDate < today)
                {
                    boolean isDeleted = this.delete(a.getId());
                    continue;
                }
                AdventureReservationDTO aventureReservationDTO = new AdventureReservationDTO(a.getId(), startDate.toString(), endDate.toString(), a.getMaxGuests(), a.getPrice(), a.isAvailable());
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
        return adventureReservationDTOS;
	}

	public List<AdventureReservationDTO> getAllActionsByInstructorId(Long id) {
		List<FishingAdventure> allAdventures = this.fishingAdventureService.getFishingAdventuresByInstructor(id);
		List<AdventureReservationDTO> allActions = new ArrayList<AdventureReservationDTO>();
		for(FishingAdventure a: allAdventures) {
			List<AdventureReservationDTO> adventureActions = this.getAllActionsByAdventureId(a.getId());
			for(AdventureReservationDTO action: adventureActions) {
				allActions.add(action);
			}
		}
		return allActions;
	}

	public List<AdventureReservationDTO> getAllAvaibilityPeriodsByInstructorId(Long id) {
		List<AdventureReservation> avaibilityPeriods =  this.fishingAdventureReservationsRepository.getAvaibilityPeriodsByInstructorId(id);
		List<AdventureReservationDTO> adventureReservationDTOS = new ArrayList<>();

        for (AdventureReservation a : avaibilityPeriods) {
	            String startDate = (String.valueOf(a.getStartDate().getTime()));
	            String endDate = (String.valueOf(a.getEndDate().getTime()));
	
	            AdventureReservationDTO adventureReservationDTO = new AdventureReservationDTO(a.getId(), startDate, endDate, a.getMaxGuests(), a.getPrice(), a.isAvailable());
	            adventureReservationDTO.setIAvailabilityPeriod(a.isAvailabilityPeriod());
	            adventureReservationDTOS.add(adventureReservationDTO);
        	
        }
        return adventureReservationDTOS;
	}
	
	public boolean delete(Long id) {
		AdventureReservation adventureReservation = this.getFishingAdventureReservationById(id);   // dobavimo rezervaciju iz baze

        Set<AdditionalServices> additionalServices =  adventureReservation.getAdditionalServices();     // ne moramo direktno iz baze dobavljati jer ova lista u sebi ima objekte sa svojim pravim id-jevima
        for(AdditionalServices a: additionalServices){
            a.getAdventureReservationsServices().remove(adventureReservation);  // iz niza rezervacija dodatnih usluga izbacimo ovu rezervaciju koju brisemo - raskinuta u tabeli additional_services_house_reservation (sa vodece strane, jer je kod AdditionalService JoinTable)
            this.additionalServicesService.save(a);
        }

        adventureReservation.setGuest(null); // TODO: proveriit
        adventureReservation.setFishingAdventure(null);  // raskinuta veza u tabeli house_reservation_table (sa strane vodece veze u ManyToMany vezi)
        adventureReservation = this.save(adventureReservation);
        //this.delete(adventureReservation.getId());  // brisanje rezervacije iz house_reservation tabele
        return true;
	}
}
