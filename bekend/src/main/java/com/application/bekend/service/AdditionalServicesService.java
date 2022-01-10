package com.application.bekend.service;

import com.application.bekend.DTO.AdditionalServicesDTO;
import com.application.bekend.model.AdditionalServices;
import com.application.bekend.model.FishingAdventure;
import com.application.bekend.repository.AdditionalServicesRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class AdditionalServicesService {

    private final AdditionalServicesRepository additionalServicesRepository;

    public AdditionalServicesService(AdditionalServicesRepository additionalServicesRepository) {
        this.additionalServicesRepository = additionalServicesRepository;
    }

    public AdditionalServices save(AdditionalServices additionalServices) {
        return this.additionalServicesRepository.save(additionalServices);
    }

    public List<AdditionalServices> getAllByHouseId(Long id) { return additionalServicesRepository.getAllByHouseId(id); };

    public List<AdditionalServices> getAllByBoatId(Long id) { return additionalServicesRepository.getAllByBoatId(id); };

    public Set<AdditionalServices> getAllByHouseReservationId(Long id) { return additionalServicesRepository.getAllByHouseReservationId(id); }

    public Set<AdditionalServices> getAllByBoatReservationId(Long id) { return additionalServicesRepository.getAllByBoatReservationId(id); }

    public AdditionalServices getAdditionalServicesById(Long id){return additionalServicesRepository.getAdditionalServicesById(id);}
    
    public List<AdditionalServices> getAllByFishingAdventureId(Long id) { return additionalServicesRepository.getAllByFishingAdventureId(id); };

    public void deleteById(Long id) { this.additionalServicesRepository.deleteById(id); }
    
    public void addMultipleFishingAdventureServices(FishingAdventure fishingAdventure, Set<AdditionalServicesDTO> newAdditionalServices) {
    	for(AdditionalServicesDTO a : newAdditionalServices) {
    		AdditionalServices additionalServices = new AdditionalServices();
    		additionalServices.setName(a.getName());
    		additionalServices.setPrice(a.getPrice());
    		Set<FishingAdventure> fishingAdventures = additionalServices.getFishingAdventures();
    		fishingAdventures.add(fishingAdventure);
            additionalServices.setFishingAdventures(fishingAdventures);
            additionalServices = save(additionalServices);
    	}
    }
    
    public Set<AdditionalServices> getAllByAdventureReservationId(Long id) { return additionalServicesRepository.getAllByAdventureReservationId(id); }
}
