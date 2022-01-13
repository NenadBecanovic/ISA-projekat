package com.application.bekend.controller;

import java.util.List;
import java.util.Set;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.application.bekend.DTO.FeedbackDTO;
import com.application.bekend.DTO.FeedbackInfoDTO;
import com.application.bekend.model.AdditionalServices;
import com.application.bekend.model.Boat;
import com.application.bekend.model.BoatReservation;
import com.application.bekend.model.Image;
import com.application.bekend.model.NavigationEquipment;
import com.application.bekend.service.FeedbackService;

@RestController
@RequestMapping("api/feedback")
public class FeedbackController {
	
	private final FeedbackService feedbackService;
	
	@Autowired
	public FeedbackController(FeedbackService feedbackService) {
		this.feedbackService = feedbackService;
	}
	
	 @PostMapping("/saveFeedback")
	 public ResponseEntity<FeedbackDTO> saveFeedbackEntity(@RequestBody FeedbackDTO dto){
		 if(dto.isHasHouse()){
			 this.feedbackService.saveFeedbackHouse(dto);
	     }else if(dto.isHasHouseOwner()){
	         this.feedbackService.saveFeedbackHouseOwner(dto);
	     }else if(dto.isHasBoat()){
	         this.feedbackService.saveFeedbackBoat(dto);
	     }else if(dto.isHasBoatOwner()){
	         this.feedbackService.saveFeedbackBoatOwner(dto);
	     }else{
	         this.feedbackService.saveFeedbackInstructor(dto);
	     }
		 
	     return new ResponseEntity<>(dto, HttpStatus.OK);
	 }
	 
	 @GetMapping("/getAllFeedbacks")
	 @PreAuthorize("hasRole('ROLE_ADMIN')")
	 public ResponseEntity<List<FeedbackInfoDTO>> getAll(){
		 List<FeedbackInfoDTO> allFeedbacks = this.feedbackService.getAll();
		 return new ResponseEntity<>(allFeedbacks, HttpStatus.OK);
	 }
	 
	 @DeleteMapping("/delete/{id}")
	 @Transactional
	 public ResponseEntity<Boolean> delete(@PathVariable("id") Long id) {
	     boolean isDeleted = this.feedbackService.delete(id);   
		 
		 return new ResponseEntity<>(isDeleted, HttpStatus.OK);
	 }
	 
	 @DeleteMapping("/approve/{id}")
	 @Transactional
	 public ResponseEntity<Boolean> approve(@PathVariable("id") Long id, @RequestBody FeedbackInfoDTO feedbackInfo) throws MessagingException {
	     boolean approved = this.feedbackService.approve(feedbackInfo);   
		 
		 return new ResponseEntity<>(approved, HttpStatus.OK);
	 }
}
