package com.application.bekend.service;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.mail.MessagingException;

import com.application.bekend.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.application.bekend.DTO.AdditionalServicesDTO;
import com.application.bekend.DTO.AdventureReservationDTO;
import com.application.bekend.DTO.EmailDTO;
import com.application.bekend.DTO.UserInfoDTO;
import com.application.bekend.repository.FishingAdventureReservationRepository;

@Service
public class FishingAdventureReservationService {
	
	private final FishingAdventureReservationRepository fishingAdventureReservationsRepository;
	private final FishingAdventureService fishingAdventureService;
	private final AdditionalServicesService additionalServicesService;
	private final MyUserService myUserService;
	private final UserCategoryService userCategoryService;
	private final CompanyService companyService;
	
	@Autowired
	public FishingAdventureReservationService(FishingAdventureReservationRepository fishingAdventureReservationRepository, FishingAdventureService fishingAdventureService, AdditionalServicesService additionalServicesService,
			MyUserService myUserService, UserCategoryService userCategoryService, CompanyService companyService) {
		this.fishingAdventureReservationsRepository = fishingAdventureReservationRepository;
		this.fishingAdventureService = fishingAdventureService;
		this.additionalServicesService = additionalServicesService;
		this.myUserService = myUserService;
		this.userCategoryService = userCategoryService;
		this.companyService = companyService;
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
	
	public boolean saveReservation(AdventureReservationDTO adventureReservationDTO) throws MessagingException {
		FishingAdventure fishingAdventure = this.fishingAdventureService.getFishingAdventureById(adventureReservationDTO.getAdventureId());
		MyUser instructor = this.myUserService.findUserByAdventureId(fishingAdventure.getId());
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
        adventureReservation.setAvailabilityPeriod(adventureReservationDTO.getAvailabilityPeriod());
        adventureReservation.setAction(adventureReservationDTO.getIsAction());
        if(adventureReservation.isAction()) {
        //	this.emailService.sendAnswerEmail(new EmailDTO("Odgovor na žalbu", answerDTO.getGuestResponse(), appeal.getSenderId().getEmail()));
        }
        adventureReservation = this.save(adventureReservation);
        
        instructor.setPoints(instructor.getPoints() + this.companyService.getCompanyInfo((long) 1).getPointsPerReservationOwner());
        this.checkUserCategory(instructor);
        
        Set<AdditionalServices> additionalServicesSet = new HashSet<>();
        for(AdditionalServicesDTO add : adventureReservationDTO.getAdditionalServices()){
            AdditionalServices additionalServices = this.additionalServicesService.getAdditionalServicesById(add.getId());
            additionalServices.addAdventureReservation(adventureReservation);
            additionalServicesSet.add(additionalServices);  
            this.additionalServicesService.save(additionalServices);
        }
        fishingAdventure.addAdventureReservation(adventureReservation);
        this.fishingAdventureService.save(fishingAdventure);
        this.myUserService.save(instructor);

        if (adventureReservationDTO.getGuestId() != null && adventureReservationDTO.getGuestId() != 0) {
            MyUser guest = this.myUserService.findUserById(adventureReservationDTO.getGuestId());
            adventureReservation.setGuest(guest);
            this.fishingAdventureService.save(fishingAdventure);

            Set<AdventureReservation> adventureReservationsGuest = guest.getAdventureReservations();
            adventureReservationsGuest.add(adventureReservation);
            guest.setAdventureReservations(adventureReservationsGuest);
            guest.setPoints(guest.getPoints() + this.companyService.getCompanyInfo((long) 1).getPointsPerReservationClient());
            this.checkUserCategory(guest);
            this.myUserService.save(guest);
            
            if(adventureReservationDTO.getIsOwnerReservation()) {
            	this.myUserService.sendMailToClient(null, null, adventureReservationDTO, "", "", fishingAdventure.getName());
            }
        }
        
        if (adventureReservationDTO.getIsAction() && adventureReservationDTO.getIsAvailable()){
            this.myUserService.sendSubscribedUsersEmail(null, null, adventureReservationDTO, "", "", fishingAdventure.getName());
        }
        
        return true;
	}

	public boolean saveUnavailablePeriod(Long instructorId, AdventureReservationDTO adventureReservationDTO) throws MessagingException {
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
	            adventureReservationDTO.setAvailabilityPeriod(a.isAvailabilityPeriod());
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
                aventureReservationDTO.setAvailabilityPeriod(a.isAvailabilityPeriod());
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
	            adventureReservationDTO.setAvailabilityPeriod(a.isAvailabilityPeriod());
	            adventureReservationDTOS.add(adventureReservationDTO);
        	
        }
        return adventureReservationDTOS;
	}


    public List<AdventureReservation> getAdventureReservationsByGuestId(Long id) {
        return this.fishingAdventureReservationsRepository.getAdventureReservationByGuestId(id);
    }

    public double findTotalPriceForAdventureReservation(AdventureReservation adventureReservation){
        int hoursDifference = this.getHoursDifference(adventureReservation.getStartDate(), adventureReservation.getEndDate());
        double totalPrice = hoursDifference*adventureReservation.getPrice();
        return totalPrice;
    }

    private int getHoursDifference(Date startData, Date endDate){
        long date1InMs = startData.getTime();
        long date2InMs = endDate.getTime();

        long timeDiff = 0;
        if(date1InMs > date2InMs) {
            timeDiff = date1InMs - date2InMs;
        } else {
            timeDiff = date2InMs - date1InMs;
        }

        // converting diff into days
        int hoursDiff = (int) (timeDiff / (1000 * 60 * 60));

        return hoursDiff;
    }

    public void canBeCancelled(AdventureReservationDTO dto, AdventureReservation b){

        Date currentDate = new Date();
        long startDateTime = b.getStartDate().getTime();
        long currentMilis = currentDate.getTime();
        long endDateTime = b.getEndDate().getTime();
        if(currentMilis < endDateTime) {
            if(currentMilis >= startDateTime && currentMilis <=endDateTime){
                dto.setOnGoing(true);
            }else{
                long timeDiff = startDateTime -  currentMilis;
                float daysDiff = timeDiff / (1000 * 60 * 60* 24);
                if (daysDiff >= 3 && b.getCancelled() ==  false){
                    dto.setCanBeCancelled(true);
                }
            }
        }
    }
    
    private void checkUserCategory(MyUser user) {
    	List<UserCategory> allCategories = this.userCategoryService.findAll();
    	int min = 0;
    	UserCategory cat = new UserCategory();
    	for(UserCategory category: allCategories) {
    		if(category.getPoints() > min && user.getPoints() > category.getPoints()) {
    			min = category.getPoints();
    			cat = category;
    		}
    	}
    	user.setCategory(cat);
    }

	public boolean canAdventureBeChanged(Long id) {
		List<AdventureReservation> allReservations = this.getAllByFishingAdventure_Id(id);
		Long currentTime = new Date().getTime();
		
		for(AdventureReservation a: allReservations) {
			if(a.getStartDate().getTime() > currentTime || a.getEndDate().getTime() > currentTime) {
				return false;
			}
		}
		return true;
	}

}
