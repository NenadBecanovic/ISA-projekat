package com.application.bekend.controller;

import com.application.bekend.DTO.*;
import com.application.bekend.model.*;
import com.application.bekend.service.AdditionalServicesService;
import com.application.bekend.service.AddresService;
import com.application.bekend.service.FishingAdventureReservationService;
import com.application.bekend.service.FishingAdventureService;
import com.application.bekend.service.ImageService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    private final ModelMapper modelMapper;
    private final FishingAdventureReservationService fishingAdventureReservationService;

    @Autowired
    public FishingAdventureController(FishingAdventureService fishingAdventureService, AddresService addressService, AdditionalServicesService additionalServicesService, 
    		ImageService imageService, ModelMapper modelMapper, FishingAdventureReservationService fishingAdventureReservationService) {
        this.fishingAdventureService = fishingAdventureService;
        this.addressService = addressService;
        this.additionalServicesService = additionalServicesService;
        this.imageService = imageService;
        this.modelMapper = modelMapper;
        this.fishingAdventureReservationService = fishingAdventureReservationService;
    }

    @GetMapping("/getFishingAdventureById/{id}")
    public ResponseEntity<FishingAdventureDTO> getFishingAdventureById(@PathVariable("id") Long id){
        FishingAdventure fishingAdventure = this.fishingAdventureService.getFishingAdventureById(id);
        AddressDTO addressDTO = new AddressDTO(fishingAdventure.getAddress().getId(), fishingAdventure.getAddress().getStreet(), fishingAdventure.getAddress().getCity(),
        		fishingAdventure.getAddress().getState(), fishingAdventure.getAddress().getLongitude(), fishingAdventure.getAddress().getLatitude(), fishingAdventure.getAddress().getPostalCode());

        FishingAdventureDTO fishingAdventureDTO = new FishingAdventureDTO(fishingAdventure.getId(), fishingAdventure.getName(), addressDTO, fishingAdventure.getPromoDescription(), fishingAdventure.getCapacity(), fishingAdventure.getFishingEquipment(),
        		fishingAdventure.getBehaviourRules(), fishingAdventure.getPricePerHour(), fishingAdventure.isCancalletionFree(), fishingAdventure.getCancalletionFee());
        //fishingAdventureDTO.setInstructorId(fishingAdventure.getInstructor().getId());

        return new ResponseEntity<>(fishingAdventureDTO, HttpStatus.OK);
    }
    
    @GetMapping("/getFishingAdventuresByInstructor/{id}")
    public ResponseEntity<List<FishingAdventureDTO>> getFishingAdventuresByInstructor(@PathVariable("id") Long id){
        List<FishingAdventure> fishingAdventures = this.fishingAdventureService.getFishingAdventuresByInstructor(id);
        List<FishingAdventureDTO> instructorFishingAdventures = new ArrayList<FishingAdventureDTO>();
        
        for(FishingAdventure f: fishingAdventures) {
        	if(!f.isDeleted()) {
		        AddressDTO addressDTO = new AddressDTO(f.getAddress().getId(), f.getAddress().getStreet(), f.getAddress().getCity(),
		        		f.getAddress().getState(), f.getAddress().getLongitude(), f.getAddress().getLatitude(), f.getAddress().getPostalCode());
	            Set<ImageDTO> dtoSet = new HashSet<>();
	            for(Image i: f.getImages()){
	                ImageDTO imageDTO = modelMapper.map(i, ImageDTO.class);
	                dtoSet.add(imageDTO);
	            }
	            FishingAdventureDTO dto = new FishingAdventureDTO(f.getId(), f.getName(), addressDTO, f.getPromoDescription(), f.getCapacity(), f.getFishingEquipment(),
	                    f.getBehaviourRules(), f.getPricePerHour(), f.isCancalletionFree(), f.getCancalletionFee());
	            dto.setImages(dtoSet);
		        instructorFishingAdventures.add(dto);
        	}
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
        fishingAdventure.setDeleted(false);
        fishingAdventure = this.fishingAdventureService.save(fishingAdventure);
        imageService.uploadAdventureImage(newFishingAdventure.getImage(), fishingAdventure.getId());
        additionalServicesService.addMultipleFishingAdventureServices(fishingAdventure, newFishingAdventure.getAdditionalServices());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    
    @PutMapping("/edit/{id}")
    public ResponseEntity<FishingAdventureDTO> save(@RequestBody FishingAdventureDTO fishingAdventureDTO) {
        FishingAdventure fishingAdventure = this.fishingAdventureService.getFishingAdventureById(fishingAdventureDTO.getId());
        List<AdditionalServices> additionalServices = this.additionalServicesService.getAllByFishingAdventureId(fishingAdventureDTO.getId());
        
        if(!this.fishingAdventureReservationService.canAdventureBeChanged(fishingAdventure.getId())) {
        	return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        
        for (AdditionalServices a: additionalServices)
        {
            for (AdditionalServicesDTO additionalServicesDTO: fishingAdventureDTO.getAdditionalServices())
            {
                if(a.getId() == additionalServicesDTO.getId())
                {
                    a.setName(additionalServicesDTO.getName());
                    a.setPrice(additionalServicesDTO.getPrice());
                    this.additionalServicesService.save(a);
                }
            }
        }

        Address address = this.addressService.getAddressById(fishingAdventureDTO.getAddress().getId());
        address.setId(fishingAdventureDTO.getAddress().getId());
        address.setStreet(fishingAdventureDTO.getAddress().getStreet());
        address.setCity(fishingAdventureDTO.getAddress().getCity());
        address.setState(fishingAdventureDTO.getAddress().getState());
        address.setLongitude(fishingAdventureDTO.getAddress().getLongitude());
        address.setLatitude(fishingAdventureDTO.getAddress().getLatitude());
        address.setPostalCode(fishingAdventureDTO.getAddress().getPostalCode());
        this.addressService.save(address);

        fishingAdventure.setAddress(address);
        fishingAdventure.setName(fishingAdventureDTO.getName());
        fishingAdventure.setPromoDescription(fishingAdventureDTO.getPromoDescription());
        fishingAdventure.setBehaviourRules(fishingAdventureDTO.getBehaviourRules());
        fishingAdventure.setPricePerHour(fishingAdventureDTO.getPricePerHour());
        fishingAdventure.setCancalletionFee(fishingAdventureDTO.getCancellationFee());
        fishingAdventure.setCancalletionFree(fishingAdventureDTO.isCancellationFree());

        this.fishingAdventureService.save(fishingAdventure);

        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @GetMapping("/getFishingAdventureInstructor/{id}")
    public ResponseEntity<FishingAdventureInstructorInfoDTO> findUserByHouseReservationId(@PathVariable("id") Long id) {
        FishingAdventureInstructorInfoDTO instructor = this.fishingAdventureService.getFishingAdventureInstructor(id);
        return new ResponseEntity<>(instructor, HttpStatus.OK);
    }
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") Long id) {
        
        if(!this.fishingAdventureReservationService.canAdventureBeChanged(id)) {
        	return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        this.fishingAdventureService.delete(id);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }
    
}
