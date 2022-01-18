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
    
    @Autowired
    public FishingAdventureReservationController(FishingAdventureReservationService fishingAdventureReservationService) {
    	this.fishingAdventureReservationService = fishingAdventureReservationService;
    }
    
    @GetMapping("/getAllByAdventureId/{id}")
    public ResponseEntity<List<AdventureReservationDTO>> getAllByAdventureId(@PathVariable("id") Long id) {
        List<AdventureReservationDTO> adventureReservations = this.fishingAdventureReservationService.getAllDTOByAdventureId(id);

        return new ResponseEntity<>(adventureReservations, HttpStatus.OK);
    }
    
    @GetMapping("/getAllActionsByFishingAdventureId/{id}")
    public ResponseEntity<List<AdventureReservationDTO>> getAllActionsByAdventureId(@PathVariable("id") Long id) {
        List<AdventureReservationDTO> adventureReservationDTOS = this.fishingAdventureReservationService.getAllActionsByAdventureId(id);

        return new ResponseEntity<>(adventureReservationDTOS, HttpStatus.OK);
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
        List<AdventureReservationDTO> adventureReservations = this.fishingAdventureReservationService.getFishingAdventureReservationsDTOByGuestId(id);
        
        return new ResponseEntity<>(adventureReservations, HttpStatus.OK);
    }
    
    @GetMapping("/getCompanyProfit/{startDate}/{endDate}")
    public ResponseEntity<Double> getCompanyInfo(@PathVariable("startDate") String startDate, @PathVariable("endDate") String endDate){
		double profit = this.fishingAdventureReservationService.getCompanyProfit(startDate,endDate);
        
        return new ResponseEntity<>(profit, HttpStatus.OK);
    }

}
