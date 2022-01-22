package com.application.bekend.controller;

import java.io.IOException;
import java.util.List;

import javax.mail.MessagingException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.application.bekend.DTO.AdditionalServicesDTO;
import com.application.bekend.DTO.AdventureReservationDTO;
import com.application.bekend.DTO.FishingAdventureDTO;
import com.application.bekend.DTO.FishingAdventureInstructorInfoDTO;
import com.application.bekend.DTO.NewFishingAdventureDTO;
import com.application.bekend.model.AdditionalServices;
import com.application.bekend.service.FishingAdventureService;

@RestController
@RequestMapping("api/fishingAdventure")
public class FishingAdventureController {

    private final FishingAdventureService fishingAdventureService;

    @Autowired
    public FishingAdventureController(FishingAdventureService fishingAdventureService) {
        this.fishingAdventureService = fishingAdventureService;
    }

    @GetMapping("/getFishingAdventureById/{id}")
    public ResponseEntity<FishingAdventureDTO> getFishingAdventureDTOById(@PathVariable("id") Long id){
        FishingAdventureDTO fishingAdventure = this.fishingAdventureService.getFishingAdventureDTOById(id);
        
        if(fishingAdventure == null) {
        	return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(fishingAdventure, HttpStatus.OK);
    }
    
    @GetMapping("/getFishingAdventuresByInstructor/{id}")
    public ResponseEntity<List<FishingAdventureDTO>> getFishingAdventuresDTOSByInstructor(@PathVariable("id") Long id){
        List<FishingAdventureDTO> fishingAdventures = this.fishingAdventureService.getFishingAdventuresDTOSByInstructor(id);   
        
        return new ResponseEntity<>(fishingAdventures, HttpStatus.OK);
    }
    
    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_INSTRUCTOR')")
    public ResponseEntity<Long> save(@RequestBody NewFishingAdventureDTO newFishingAdventure) throws IOException {
        Long adventureId = this.fishingAdventureService.saveAdventure(newFishingAdventure);
        return new ResponseEntity<>(adventureId,HttpStatus.CREATED);
    }
    
    @PutMapping("/edit/{id}")
    @PreAuthorize("hasRole('ROLE_INSTRUCTOR')")
    public ResponseEntity<FishingAdventureDTO> edit(@RequestBody FishingAdventureDTO fishingAdventureDTO) {
        boolean isEdited = this.fishingAdventureService.edit(fishingAdventureDTO);
        
        if(!isEdited) {
        	return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @GetMapping("/getFishingAdventureInstructor/{id}")
    public ResponseEntity<FishingAdventureInstructorInfoDTO> getFishingInstructor(@PathVariable("id") Long id) {
        FishingAdventureInstructorInfoDTO instructor = this.fishingAdventureService.getFishingAdventureInstructor(id);
        return new ResponseEntity<>(instructor, HttpStatus.OK);
    }
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") Long id) {
        boolean isDeleted = this.fishingAdventureService.deleteAdventure(id);
        if(!isDeleted) {
        	return new ResponseEntity<>(isDeleted, HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(isDeleted, HttpStatus.OK);
    }
    
    @PostMapping("/addAdditionalService")
    @PreAuthorize("hasRole('ROLE_INSTRUCTOR')")
    public ResponseEntity saveAdditionalService(@RequestBody AdditionalServicesDTO dto) {
        this.fishingAdventureService.saveAdditionalService(dto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    
    @PostMapping("/addReservation")
    public ResponseEntity<AdventureReservationDTO> save(@RequestBody AdventureReservationDTO adventureReservationDTO) throws MessagingException {
        boolean isCreated = this.fishingAdventureService.saveReservation(adventureReservationDTO);
        
        if(!isCreated) {
        	return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    
    @PostMapping("/saveUnavailablePeriod/{id}")
    public ResponseEntity<AdventureReservationDTO> saveUnavailablePeriod(@PathVariable("id") Long instructorId, @RequestBody AdventureReservationDTO adventureReservationDTO) throws MessagingException {
        boolean isCreated = this.fishingAdventureService.saveUnavailablePeriod(instructorId,adventureReservationDTO);
        
        if(!isCreated) {
        	return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    
    @GetMapping("/getAllActionsByInstructorId/{id}")
    public ResponseEntity<List<AdventureReservationDTO>> getAllActionsByInstructorId(@PathVariable("id") Long id) {
        List<AdventureReservationDTO> allActions = this.fishingAdventureService.getAllActionsByInstructorId(id);

        return new ResponseEntity<>(allActions, HttpStatus.OK);
    }
    
    @DeleteMapping("/deleteAdditionalService/{id}/{adventureId}")
    @Transactional
    @PreAuthorize("hasRole('ROLE_INSTRUCTOR')")
    public ResponseEntity<Boolean> deleteAdditionalService(@PathVariable("id") Long id, @PathVariable("adventureId") Long adventureId) {
        boolean isDeleted = this.fishingAdventureService.deleteAdditionalService(id, adventureId);

        if(!isDeleted) {
        	return new ResponseEntity<>(isDeleted, HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(isDeleted, HttpStatus.OK);
    }
    
    @DeleteMapping("/deleteAllAdventuresByInstructor/{id}")
    @Transactional
    public ResponseEntity<Boolean> deleteAllAdventuresByInstructor(@PathVariable("id") Long id) {
        boolean isDeleted = this.fishingAdventureService.deleteAllAdventuresByInstructor(id);

        if(!isDeleted) {
        	return new ResponseEntity<>(isDeleted, HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(isDeleted, HttpStatus.OK);
    }
}
