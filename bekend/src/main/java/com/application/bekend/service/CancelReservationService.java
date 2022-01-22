package com.application.bekend.service;

import com.application.bekend.DTO.ReservationCancelDTO;
import com.application.bekend.model.BoatReservation;
import com.application.bekend.model.HouseReservation;
import com.application.bekend.model.MyUser;
import com.application.bekend.model.UserCategory;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CancelReservationService {

    private final MyUserService myUserService;
    private final HouseReservationService houseReservationService;
    private final BoatReservationService boatReservationService;
    private final UserCategoryService userCategoryService;
	private final CompanyService companyService;

    @Autowired
    public CancelReservationService(MyUserService myUserService, HouseReservationService houseReservationService, BoatReservationService boatReservationService,
    		UserCategoryService userCategoryService, CompanyService companyService) {
        this.myUserService = myUserService;
        this.houseReservationService = houseReservationService;
        this.boatReservationService = boatReservationService;
        this.userCategoryService = userCategoryService;
        this.companyService = companyService;
    }

    public void cancelReservation(ReservationCancelDTO reservationCancelDTO){
    	MyUser guest = this.myUserService.findUserById(reservationCancelDTO.getGuestId());
        if(reservationCancelDTO.getIsHouse()){
            HouseReservation houseReservation = this.houseReservationService.getHouseReservationById(reservationCancelDTO.getReservationId());
            houseReservation.setCancelled(true);
            houseReservationService.save(houseReservation);
            if(houseReservation.isAction()){
                houseReservation.setId(null);
                houseReservation.setGuest(null);
                guest.setPoints(guest.getPoints() - this.companyService.getCompanyInfo((long) 1).getPointsPerReservationClient());
                this.checkUserCategory(guest);
                houseReservationService.save(houseReservation);
            }

        }else if(reservationCancelDTO.getIsBoat()){
            BoatReservation boatReservation = this.boatReservationService.getBoatReservationById(reservationCancelDTO.getReservationId());
            boatReservation.setCancelled(true);
            boatReservationService.save(boatReservation);
            if(boatReservation.isAction()){
                boatReservation.setId(null);
                boatReservation.setGuest(null);
                guest.setPoints(guest.getPoints() - this.companyService.getCompanyInfo((long) 1).getPointsPerReservationClient());
                this.checkUserCategory(guest);
                boatReservationService.save(boatReservation);
            }
        }else if(reservationCancelDTO.getIsAdventure()){

        }

        MyUser user = this.myUserService.findUserById(reservationCancelDTO.getGuestId());
        user.setPenalties(user.getPenalties() + 1);
        this.myUserService.save(user);
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
        user.setCategory(cat);
        this.myUserService.save(user);
    }
}
