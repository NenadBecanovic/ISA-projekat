package com.application.bekend.service;

import com.application.bekend.DTO.AppealDTO;
import com.application.bekend.DTO.FeedbackDTO;
import com.application.bekend.model.*;
import com.application.bekend.repository.AppealRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppealService {

    private final AppealRepository appealRepository;
    private final HouseReservationService houseReservationService;
    private final BoatReservationService boatReservationService;
    private final MyUserService myUserService;

    @Autowired
    public AppealService(AppealRepository appealRepository, HouseReservationService houseReservationService, BoatReservationService boatReservationService, MyUserService myUserService) {
        this.appealRepository = appealRepository;
        this.houseReservationService = houseReservationService;
        this.boatReservationService = boatReservationService;
        this.myUserService = myUserService;
    }

    public void saveAppealHouse(AppealDTO appealDTO){
        HouseReservation houseReservation = this.houseReservationService.getHouseReservationById(appealDTO.getReservationId());
        houseReservation.setHasAppealEntity(true);
        this.houseReservationService.save(houseReservation);
        Appeal appeal = this.setAppeal(appealDTO);
        appeal.setHouse(houseReservation.getHouse());
        this.appealRepository.save(appeal);
    }

    public void saveAppealBoat(AppealDTO appealDTO){
        BoatReservation boatReservation = this.boatReservationService.getBoatReservationById(appealDTO.getReservationId());
        boatReservation.setHasAppealEntity(true);
        this.boatReservationService.save(boatReservation);
        Appeal appeal = this.setAppeal(appealDTO);
        appeal.setBoat(boatReservation.getBoat());
        this.appealRepository.save(appeal);
    }

    public void saveAppealHouseOwner(AppealDTO appealDTO){
        HouseReservation houseReservation = this.houseReservationService.getHouseReservationById(appealDTO.getReservationId());
        houseReservation.setHasAppealOwner(true);
        this.houseReservationService.save(houseReservation);
        Appeal appeal = this.setAppeal(appealDTO);
        this.appealRepository.save(appeal);;
    }

    public void saveAppealBoatOwner(AppealDTO appealDTO){
        BoatReservation boatReservation = this.boatReservationService.getBoatReservationById(appealDTO.getReservationId());
        boatReservation.setHasAppealOwner(true);
        this.boatReservationService.save(boatReservation);
        Appeal appeal = this.setAppeal(appealDTO);
        this.appealRepository.save(appeal);
    }

    public Appeal save(Appeal appeal){
        return this.appealRepository.save(appeal);
    }

    public void saveAppealInstructor(AppealDTO appealDTO){
//        AdventureReservation adventureReservation = this.adneture.getFishingAdventureById(feedbackDTO.getReservationId())
//        adventureReservation.setHasFeedbackOwner(true);
//        this.fishingAdventureService.save(adventureReservation);
//        Feedback feedback = this.setFeedback(feedbackDTO);
//        feedback.setMyUser(this.findUserById(feedbackDTO.getOwnerId()));;
//        this.feedbackService.save(feedback);
    }

    private Appeal setAppeal(AppealDTO appealDTO){
        Appeal appeal = new Appeal();
        MyUser owner = this.myUserService.findUserById(appealDTO.getOwnerId());
        MyUser sender = this.myUserService.findUserById(appealDTO.getSenderId());
        appeal.setReview(appealDTO.getReview());
        appeal.setOwnerId(owner);
        appeal.setSenderId(sender);
        appeal.setAnswered(false);
        return appeal;
    }

}
