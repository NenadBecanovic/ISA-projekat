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
        // TODO: svu logiku prebaciti u servis
        // provera da li vec postoji termin u izabranom periodu
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

        adventureReservation = this.save(adventureReservation); // sacuvali smo rezervaciju i povratna vrednost metode je tacno ta rezervacija iz baze (sa ispravno generisanim id-em ...)
        // ovaj korak je obavezan jer se rezervacija koju dodajemo ovde (***) mora nalaziti u bazi

        Set<AdditionalServices> additionalServicesSet = new HashSet<>();
        for(AdditionalServicesDTO add : adventureReservationDTO.getAdditionalServices()){

            // iz baze dobavljamo (original) dodatnu uslugu i u njen set rezervacija, dodajemo ovu konkretnu rezervaciju (houseReservation)
            AdditionalServices additionalServices = this.additionalServicesService.getAdditionalServicesById(add.getId());

            additionalServices.addAdventureReservation(adventureReservation); // (***)
            // da je bio slucaj da smo dodali samo inicijalno kreiran houseReservation (nastao iz podataka od DTO), bio bi error: javax.persistence.EntityNotFoundException
            // jer u tabeli koja spaja AdditionalServices (id_a) i HouseReservation (id_h), id_h bi bio null i to vraca gresku, jer se u tabeli mora nalaziti neki vec postojeci id_h (radimo spajanje podataka dve postojece table, nema novih podataka)

            additionalServicesSet.add(additionalServices);   // u set koji cemo kasnije dodeliti rezervaciji dodajemo dodatnu uslugu

            // azuriramo (sacuvamo) izmenjenu dodatnu uslugu u bazi (additional service)
            this.additionalServicesService.save(additionalServices);
        }

        // dodajem rezervaciju vikendice u samu vikendicu
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
}
