package com.application.bekend.service;

import com.application.bekend.DTO.EmailDTO;
import com.application.bekend.DTO.FeedbackDTO;
import com.application.bekend.DTO.FeedbackInfoDTO;
import com.application.bekend.model.*;
import com.application.bekend.repository.FeedbackRepository;

import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final HouseReservationService houseReservationService;
    private final BoatReservationService boatReservationService;
    private final FishingAdventureReservationService fishingAdventureReservationService;
    private final EmailService emailService;
    private final MyUserService myUserService;
    private final FishingAdventureService fishingAdventureService;
    private final HouseService houseService;
    private final BoatService boatService;

    @Autowired
    public FeedbackService(FeedbackRepository feedbackRepository, HouseReservationService houseReservationService, BoatReservationService boatReservationService, FishingAdventureService fishingAdventureService,
    		FishingAdventureReservationService fishingAdventureReservationService, EmailService emailService, MyUserService myUserService, HouseService houseService, BoatService boatService) {
        this.feedbackRepository = feedbackRepository;
        this.houseReservationService = houseReservationService;
        this.boatReservationService = boatReservationService;
        this.fishingAdventureReservationService = fishingAdventureReservationService;
        this.emailService = emailService;
        this.myUserService = myUserService;
        this.fishingAdventureService = fishingAdventureService;
        this.boatService = boatService;
        this.houseService = houseService;
    }

    public Feedback save(Feedback feedback){
        return this.feedbackRepository.save(feedback);
    }
    
    public void saveFeedbackHouse(FeedbackDTO feedbackDTO){
        HouseReservation houseReservation = this.houseReservationService.getHouseReservationById(feedbackDTO.getReservationId());
        houseReservation.setHasFeedbackEntity(true);
        this.houseReservationService.save(houseReservation);
        Feedback feedback = this.setFeedback(feedbackDTO);
        feedback.setHouse(houseReservation.getHouse());
        this.save(feedback);
    }

    public void saveFeedbackBoat(FeedbackDTO feedbackDTO){
        BoatReservation boatReservation = this.boatReservationService.getBoatReservationById(feedbackDTO.getReservationId());
        boatReservation.setHasFeedbackEntity(true);
        this.boatReservationService.save(boatReservation);
        Feedback feedback = this.setFeedback(feedbackDTO);
        feedback.setBoat(boatReservation.getBoat());
        this.save(feedback);
    }

    public void saveFeedbackHouseOwner(FeedbackDTO feedbackDTO){
        HouseReservation houseReservation = this.houseReservationService.getHouseReservationById(feedbackDTO.getReservationId());
        houseReservation.setHasFeedbackOwner(true);
        this.houseReservationService.save(houseReservation);
        Feedback feedback = this.setFeedback(feedbackDTO);
        feedback.setMyUser(this.myUserService.findUserById(feedbackDTO.getOwnerId()));;
        this.save(feedback);
    }

    public void saveFeedbackBoatOwner(FeedbackDTO feedbackDTO){
        BoatReservation boatReservation = this.boatReservationService.getBoatReservationById(feedbackDTO.getReservationId());
        boatReservation.setHasFeedbackOwner(true);
        this.boatReservationService.save(boatReservation);
        Feedback feedback = this.setFeedback(feedbackDTO);
        feedback.setMyUser(this.myUserService.findUserById(feedbackDTO.getOwnerId()));;
        this.save(feedback);
    }

    public void saveFeedbackInstructor(FeedbackDTO feedbackDTO){
        AdventureReservation adventureReservation = this.fishingAdventureReservationService.getFishingAdventureReservationById(feedbackDTO.getReservationId());
        adventureReservation.setHasFeedbackOwner(true);
        this.fishingAdventureReservationService.save(adventureReservation);
        Feedback feedback = this.setFeedback(feedbackDTO);
        feedback.setMyUser(this.myUserService.findUserById(feedbackDTO.getOwnerId()));;
        this.save(feedback);
    }

    private Feedback setFeedback(FeedbackDTO feedbackDTO){
        Feedback feedback = new Feedback();
        feedback.setGrade(feedbackDTO.getGrade());
        feedback.setReview(feedbackDTO.getReview());
        feedback.setApproved(false);
        return feedback;
    }
    
    public List<FeedbackInfoDTO> getAll(){
    	List<Feedback> allFeedbacks = this.feedbackRepository.findAll();
    	List<FeedbackInfoDTO> allFeedbacksDTO = new ArrayList<FeedbackInfoDTO>();
    	for(Feedback f: allFeedbacks) {
    		FeedbackInfoDTO feedbackDTO = new FeedbackInfoDTO(f.getId(), f.getGrade(), f.getReview(), f.getApproved());
    		if(f.getBoat() != null) {
    			feedbackDTO.setName(f.getBoat().getName());
    			feedbackDTO.setType("Brod");
    			feedbackDTO.setOwnerId(f.getBoat().getOwner().getId());
    		}else if(f.getHouse() != null) {
    			feedbackDTO.setName(f.getHouse().getName());
    			feedbackDTO.setType("Kuća");
    			feedbackDTO.setOwnerId(f.getHouse().getOwner().getId());
    		}else if(f.getFishingAdventure() != null) {
    			feedbackDTO.setName(f.getFishingAdventure().getName());
    			feedbackDTO.setType("Avantura");
    			feedbackDTO.setOwnerId(f.getFishingAdventure().getInstructor().getId());
    		}else {
    			feedbackDTO.setName(f.getMyUser().getFirstName() + " " + f.getMyUser().getLastName());
    			feedbackDTO.setType("Instruktor");
    			feedbackDTO.setOwnerId(f.getMyUser().getId());
    		}
    		allFeedbacksDTO.add(feedbackDTO);
    	}
    	return allFeedbacksDTO;
    }
    
    public boolean delete(Long id) {
    	Feedback feedback = this.feedbackRepository.getById(id);
    	feedback.setBoat(null);
    	feedback.setFishingAdventure(null);
    	feedback.setHouse(null);
    	feedback.setMyUser(null);
    	this.feedbackRepository.delete(feedback);
    	return true;
    }

	public boolean approve(FeedbackInfoDTO feedbackDTO) throws MessagingException {
		Feedback feedback = this.feedbackRepository.getById(feedbackDTO.getId());
		MyUser owner = this.myUserService.findUserById(feedbackDTO.getOwnerId());
		if(feedback.getMyUser() != null) {
			MyUser user = this.myUserService.findUserById(feedback.getMyUser().getId());
			user.setGrade(user.getGrade() + feedback.getGrade());
			user.setNumberOfReviews(user.getNumberOfReviews() + 1);
			this.myUserService.save(user);
		} else if(feedback.getFishingAdventure() != null){
			FishingAdventure adventure = this.fishingAdventureService.getFishingAdventureById(feedback.getFishingAdventure().getId());
			adventure.setGrade(adventure.getGrade() + feedback.getGrade());
			adventure.setNumberOfReviews(adventure.getNumberOfReviews() + 1);
			this.fishingAdventureService.save(adventure);
		} else if(feedback.getBoat() != null) {
			Boat boat = this.boatService.getBoatById(feedback.getBoat().getId());
			boat.setGrade(boat.getGrade() + feedback.getGrade());
			boat.setNumberOfReviews(boat.getNumberOfReviews() + 1);
			this.boatService.save(boat);
		} else if(feedback.getHouse() != null) {
			House house = this.houseService.getHouseById(feedback.getHouse().getId());
			house.setGrade(house.getGrade() + feedback.getGrade());
			house.setNumberOfReviews(house.getNumberOfReviews() + 1);
			this.houseService.save(house);
		}
		feedback.setApproved(true);
		this.save(feedback);
		this.emailService.sendAnswerEmail(new EmailDTO("Nova ocena", "Ostavljen je novi komentar: " + feedbackDTO.getReview() + " sa ocenom: " + feedbackDTO.getGrade(), owner.getEmail()));
		return true;
	}

	public List<FeedbackInfoDTO> getAllByAdventure(Long id) {
    	List<Feedback> allFeedbacks = this.feedbackRepository.findAll();
    	List<FeedbackInfoDTO> allFeedbacksDTO = new ArrayList<FeedbackInfoDTO>();
    	for(Feedback f: allFeedbacks) {
    		if(f.getFishingAdventure().getId().equals(id) && f.getApproved()) {
    			FeedbackInfoDTO feedbackDTO = new FeedbackInfoDTO(f.getId(), f.getGrade(), f.getReview(), f.getApproved());
    			feedbackDTO.setName(f.getFishingAdventure().getName());
    			feedbackDTO.setType("Avantura");
    		}
    	}
		return allFeedbacksDTO;
	}
	
	public List<FeedbackInfoDTO> getAllByInstructor(Long id) {
    	List<Feedback> allFeedbacks = this.feedbackRepository.findAll();
    	List<FeedbackInfoDTO> allFeedbacksDTO = new ArrayList<FeedbackInfoDTO>();
    	for(Feedback f: allFeedbacks) {
    		if(f.getMyUser().getId().equals(id) && f.getApproved()) {
    			FeedbackInfoDTO feedbackDTO = new FeedbackInfoDTO(f.getId(), f.getGrade(), f.getReview(), f.getApproved());
    			feedbackDTO.setName(f.getFishingAdventure().getName());
    			feedbackDTO.setType("Instruktor");
    		}
    	}
		return allFeedbacksDTO;
	}
}
