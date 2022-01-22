package com.application.bekend.service;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.mail.MessagingException;
import javax.transaction.Transactional;

import com.application.bekend.DTO.ReservationCheckDTO;
import com.application.bekend.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.application.bekend.DTO.AdditionalServicesDTO;
import com.application.bekend.DTO.AdventureReservationDTO;
import com.application.bekend.DTO.CompanyDTO;
import com.application.bekend.DTO.EmailDTO;
import com.application.bekend.DTO.UserInfoDTO;
import com.application.bekend.repository.FishingAdventureReservationRepository;

@Service
@Transactional
public class FishingAdventureReservationService {
	
	private final FishingAdventureReservationRepository fishingAdventureReservationsRepository;
	private final AdditionalServicesService additionalServicesService;
	private final MyUserService myUserService;
	private final UserCategoryService userCategoryService;
	private final CompanyService companyService;
	
	@Autowired
	public FishingAdventureReservationService(FishingAdventureReservationRepository fishingAdventureReservationRepository, AdditionalServicesService additionalServicesService,
			MyUserService myUserService, UserCategoryService userCategoryService, CompanyService companyService) {
		this.fishingAdventureReservationsRepository = fishingAdventureReservationRepository;
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
	
	public FishingAdventure saveReservation(FishingAdventure fishingAdventure, AdventureReservationDTO adventureReservationDTO) throws MessagingException {
		MyUser instructor = this.myUserService.findUserByAdventureId(fishingAdventure.getId());
        List<AdventureReservation> adventureReservations = this.getAllByFishingAdventure_Id(fishingAdventure.getId());
        for (AdventureReservation a: adventureReservations) {
            Long start =  a.getStartDate().getTime();
            Long end = a.getEndDate().getTime();
            if(!a.getCancelled()) {
	            if (Long.parseLong(adventureReservationDTO.getStartDate()) >= start && Long.parseLong(adventureReservationDTO.getEndDate()) <=  end ||
	                    Long.parseLong(adventureReservationDTO.getStartDate()) <= start && Long.parseLong(adventureReservationDTO.getEndDate()) >= start  ||
	                    Long.parseLong(adventureReservationDTO.getStartDate()) >= start && Long.parseLong(adventureReservationDTO.getStartDate()) <= end  )
	            {
	                return null;
	            }
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
        
        if(!adventureReservationDTO.getIsAction() && !adventureReservationDTO.getAvailabilityPeriod()) {
	        instructor.setPoints(instructor.getPoints() + this.companyService.getCompanyInfo((long) 1).getPointsPerReservationOwner());
	        this.myUserService.save(instructor);
	        this.checkUserCategory(instructor);
        }
        Set<AdditionalServices> additionalServicesSet = new HashSet<>();
        for(AdditionalServicesDTO add : adventureReservationDTO.getAdditionalServices()){
            AdditionalServices additionalServices = this.additionalServicesService.getAdditionalServicesById(add.getId());
            additionalServices.addAdventureReservation(adventureReservation);
            additionalServicesSet.add(additionalServices);  
            this.additionalServicesService.save(additionalServices);
        }
        fishingAdventure.addAdventureReservation(adventureReservation);
        this.myUserService.save(instructor);

        if (adventureReservationDTO.getGuestId() != null && adventureReservationDTO.getGuestId() != 0) {
            MyUser guest = this.myUserService.findUserById(adventureReservationDTO.getGuestId());
            adventureReservation.setGuest(guest);

            Set<AdventureReservation> adventureReservationsGuest = guest.getAdventureReservations();
            adventureReservationsGuest.add(adventureReservation);
            guest.setAdventureReservations(adventureReservationsGuest);
            guest.setPoints(guest.getPoints() + this.companyService.getCompanyInfo((long) 1).getPointsPerReservationClient());
            this.myUserService.save(guest);
            this.checkUserCategory(guest);
            this.myUserService.save(guest);
            
            this.myUserService.sendMailToClient(null, null, adventureReservationDTO, "", "", fishingAdventure.getName());
        }
        
        if (adventureReservationDTO.getIsAction() && adventureReservationDTO.getIsAvailable()){
            this.myUserService.sendSubscribedUsersEmail(null, null, adventureReservationDTO, "", "", fishingAdventure.getName());
        }
        
        return fishingAdventure;
	}
	
	public List<AdventureReservationDTO> getAdventureReservationsByInstructorId(Long id){
		List<AdventureReservation> allreservations =  this.fishingAdventureReservationsRepository.getAdventureReservationsByFishingAdventureInstructorId(id);
		List<AdventureReservationDTO> adventureReservationDTOS = new ArrayList<>();

        for (AdventureReservation a : allreservations) {
        	if(!a.isAvailable() && !a.isAvailabilityPeriod() && !a.getCancelled()) {
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
		if(adventureReservation.isAction() && adventureReservation.isAvailable() || adventureReservation.isAvailabilityPeriod()) {
	        Set<AdditionalServices> additionalServices =  adventureReservation.getAdditionalServices();     // ne moramo direktno iz baze dobavljati jer ova lista u sebi ima objekte sa svojim pravim id-jevima
	        for(AdditionalServices a: additionalServices){
	            a.getAdventureReservationsServices().remove(adventureReservation);  // iz niza rezervacija dodatnih usluga izbacimo ovu rezervaciju koju brisemo - raskinuta u tabeli additional_services_house_reservation (sa vodece strane, jer je kod AdditionalService JoinTable)
	            this.additionalServicesService.save(a);
	        }
	
	        adventureReservation.setGuest(null); // TODO: proveriit
	        adventureReservation.setFishingAdventure(null);  // raskinuta veza u tabeli house_reservation_table (sa strane vodece veze u ManyToMany vezi)
	        adventureReservation = this.save(adventureReservation);
	        this.fishingAdventureReservationsRepository.delete(adventureReservation);  // brisanje rezervacije iz house_reservation tabele
	        return true;
		}
		return false;
	}

    public void deleteAdventureReservationById(Long id){
        this.fishingAdventureReservationsRepository.deleteById(id);
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

	public List<AdventureReservationDTO> getAllAvaibilityPeriodsByInstructorId(Long id) {
		List<AdventureReservation> avaibilityPeriods =  this.fishingAdventureReservationsRepository.getAdventureReservationsByFishingAdventureInstructorId(id);
		List<AdventureReservationDTO> adventureReservationDTOS = new ArrayList<>();

        for (AdventureReservation a : avaibilityPeriods) {
        	if(a.isAvailabilityPeriod()) {
        		String startDate = (String.valueOf(a.getStartDate().getTime()));
	            String endDate = (String.valueOf(a.getEndDate().getTime()));
	
	            AdventureReservationDTO adventureReservationDTO = new AdventureReservationDTO(a.getId(), startDate, endDate, a.getMaxGuests(), a.getPrice(), a.isAvailable());
	            adventureReservationDTO.setAvailabilityPeriod(a.isAvailabilityPeriod());
	            adventureReservationDTOS.add(adventureReservationDTO);
        	}
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
    	Long id = (long) 0;
    	for(UserCategory category: allCategories) {
    		if(category.getPoints() >= min && user.getPoints() >= category.getPoints()) {
    			min = category.getPoints();
    			id = category.getId();
    		}
    	}
    	UserCategory cat = this.userCategoryService.getCategoryById(id);
   // 	cat.addUser(user);
   // 	this.userCategoryService.save(cat);
    	user.setCategory(cat);
    	this.myUserService.save(user);
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

	public double getCompanyProfit(String startDate, String endDate) {
		List<AdventureReservation> adventureReservations = this.fishingAdventureReservationsRepository.findAll();
		CompanyDTO company = this.companyService.getCompanyInfo((long) 1);
		double profit = 0;
        for (AdventureReservation a : adventureReservations) { 
        	Long reservationStartDate = a.getStartDate().getTime();
            Long today = new Date().getTime();
            double adventurePrice = 0;
            
            if(!a.getCancelled() && !a.isAction() && !a.isAvailabilityPeriod() && !a.isAvailable()) {
	            if ( Long.parseLong(startDate) <= reservationStartDate && Long.parseLong(endDate) >= reservationStartDate) {
	            	adventurePrice += a.getPrice();
	
		            Set<AdditionalServicesDTO> additionalServicesDTOS = new HashSet<>();
		            // dobavljamo set dodatnih usluga za onu konkretnu rezervaciju iz baze i pretvaramo u DTO (a mozemo samo i pristupiti setu dodatnih usluga direktno preko rezervacije (a.getAdditionalServices()))
		            for (AdditionalServices add : a.getAdditionalServices()) {  // a.getAdditionalServices()
		            	adventurePrice += add.getPrice();
		            }
		            double companyProfit = adventurePrice * company.getPercentagePerReservation() * 0.01;
		            double clientBenefit = a.getGuest().getCategory().getDiscountPercentage() * companyProfit * 0.01;
		            double ownerBenefit = a.getFishingAdventure().getInstructor().getCategory().getDiscountPercentage() * companyProfit * 0.01;
		            profit += companyProfit - clientBenefit - ownerBenefit;
	            }
            }
        }
        return profit;
	}
	
	public List<AdventureReservationDTO> getAllDTOByAdventureId(Long id) {
        List<AdventureReservation> adventureReservations = this.getAllByFishingAdventure_Id(id);

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

        return adventureReservationDTOS;
    }
	
	public List<AdventureReservationDTO> getFishingAdventureReservationsDTOByGuestId(Long id) {
        List<AdventureReservation> adventureReservations = this.getAdventureReservationsByGuestId(id);
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

            dto.setTotalPrice(this.findTotalPriceForAdventureReservation(h));
            dto.setEntityName(h.getFishingAdventure().getName());
            this.canBeCancelled(dto,h);
            dto.setCancelled(h.getCancelled());

            Set<AdditionalServicesDTO> additionalServicesDTOS = new HashSet<>();
            for(AdditionalServices add : this.additionalServicesService.getAllByBoatReservationId(h.getId())) {
                AdditionalServicesDTO newAddSer = new AdditionalServicesDTO(add.getId(), add.getName(), add.getPrice());
                additionalServicesDTOS.add(newAddSer);
            }
            dto.setAdditionalServices(additionalServicesDTOS);

            adventureReservationDTOS.add(dto);
        }
        return adventureReservationDTOS;
    }

}
