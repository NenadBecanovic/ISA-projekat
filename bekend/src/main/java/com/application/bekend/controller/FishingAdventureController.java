package com.application.bekend.controller;

import com.application.bekend.DTO.AddressDTO;
import com.application.bekend.DTO.BoatDTO;
import com.application.bekend.DTO.FishingAdventureDTO;
import com.application.bekend.DTO.NewFishingAdventureDTO;
import com.application.bekend.model.Address;
import com.application.bekend.model.Boat;
import com.application.bekend.model.FishingAdventure;
import com.application.bekend.model.House;
import com.application.bekend.service.AdditionalServicesService;
import com.application.bekend.service.AddresService;
import com.application.bekend.service.BoatService;
import com.application.bekend.service.FishingAdventureService;
import com.application.bekend.service.ImageService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/fishingAdventure")
public class FishingAdventureController {

    private final FishingAdventureService fishingAdventureService;
    private final AddresService addressService;
    private final AdditionalServicesService additionalServicesService;
    private final ImageService imageService;

    @Autowired
    public FishingAdventureController(FishingAdventureService fishingAdventureService, AddresService addressService, AdditionalServicesService additionalServicesService, ImageService imageService) {
        this.fishingAdventureService = fishingAdventureService;
        this.addressService = addressService;
        this.additionalServicesService = additionalServicesService;
        this.imageService = imageService;
    }

    @GetMapping("/getFishingAdventureById/{id}")
    public ResponseEntity<FishingAdventureDTO> getFishingAdventureById(@PathVariable("id") Long id){
        FishingAdventure fishingAdventure = this.fishingAdventureService.getFishingAdventureById(id);
        AddressDTO addressDTO = new AddressDTO(fishingAdventure.getAddress().getId(), fishingAdventure.getAddress().getStreet(), fishingAdventure.getAddress().getCity(),
        		fishingAdventure.getAddress().getState(), fishingAdventure.getAddress().getLongitude(), fishingAdventure.getAddress().getLatitude(), fishingAdventure.getAddress().getPostalCode());

        FishingAdventureDTO fishingAdventureDTO = new FishingAdventureDTO(fishingAdventure.getId(), fishingAdventure.getName(), addressDTO, fishingAdventure.getPromoDescription(), fishingAdventure.getCapacity(), fishingAdventure.getFishingEquipment(),
        		fishingAdventure.getBehaviourRules(), fishingAdventure.getPricePerHour(), fishingAdventure.isCancalletionFree(), fishingAdventure.getCancalletionFee());

        return new ResponseEntity<>(fishingAdventureDTO, HttpStatus.OK);
    }
    
    @GetMapping("/getFishingAdventuresByInstructor/{id}")
    public ResponseEntity<List<FishingAdventureDTO>> getFishingAdventuresByInstructor(@PathVariable("id") Long id){
        List<FishingAdventure> fishingAdventures = this.fishingAdventureService.getFishingAdventuresByInstructor(id);
        List<FishingAdventureDTO> instructorFishingAdventures = new ArrayList<FishingAdventureDTO>();
        
        for(FishingAdventure f: fishingAdventures) {
	        AddressDTO addressDTO = new AddressDTO(f.getAddress().getId(), f.getAddress().getStreet(), f.getAddress().getCity(),
	        		f.getAddress().getState(), f.getAddress().getLongitude(), f.getAddress().getLatitude(), f.getAddress().getPostalCode());
	        
	        instructorFishingAdventures.add(new FishingAdventureDTO(f.getId(), f.getName(), addressDTO, f.getPromoDescription(), f.getCapacity(), f.getFishingEquipment(),
	        		f.getBehaviourRules(), f.getPricePerHour(), f.isCancalletionFree(), f.getCancalletionFee()));
        }
        
        return new ResponseEntity<>(instructorFishingAdventures, HttpStatus.OK);
    }
    
    @PostMapping("/add")
    public ResponseEntity<FishingAdventure> save(@RequestBody NewFishingAdventureDTO newFishingAdventure) throws IOException {
        Address address = new Address(newFishingAdventure.getAddress().getId(), newFishingAdventure.getAddress().getStreet(), newFishingAdventure.getAddress().getCity(), newFishingAdventure.getAddress().getState(),
        		newFishingAdventure.getAddress().getLongitude(), newFishingAdventure.getAddress().getLatitude(), newFishingAdventure.getAddress().getPostalCode());
        address = this.addressService.save(address);
        System.out.print(address.getCity());
        FishingAdventure fishingAdventure = new FishingAdventure(Long.valueOf(0),newFishingAdventure.getName(),address,newFishingAdventure.getPromoDescription(),newFishingAdventure.getCapacity(),newFishingAdventure.getFishingEquipment(),
        						new HashSet<>(),newFishingAdventure.getBehaviourRules(), newFishingAdventure.getPricePerHour(),new HashSet<>(),newFishingAdventure.isCancellationFree(),
        						newFishingAdventure.getCancellationFee(), new HashSet<>());
        fishingAdventure = this.fishingAdventureService.save(fishingAdventure);
        imageService.uploadAdventureImage(newFishingAdventure.getImage(), fishingAdventure.getId());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    
}
