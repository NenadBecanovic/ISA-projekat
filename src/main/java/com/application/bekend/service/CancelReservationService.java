package com.application.bekend.service;

import com.application.bekend.DTO.ReservationCancelDTO;
import com.application.bekend.model.BoatReservation;
import com.application.bekend.model.HouseReservation;
import com.application.bekend.model.MyUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CancelReservationService {

    private final MyUserService myUserService;
    private final HouseReservationService houseReservationService;
    private final BoatReservationService boatReservationService;

    @Autowired
    public CancelReservationService(MyUserService myUserService, HouseReservationService houseReservationService, BoatReservationService boatReservationService) {
        this.myUserService = myUserService;
        this.houseReservationService = houseReservationService;
        this.boatReservationService = boatReservationService;
    }

    public void cancelReservation(ReservationCancelDTO reservationCancelDTO){
        if(reservationCancelDTO.getIsHouse()){
            HouseReservation houseReservation = this.houseReservationService.getHouseReservationById(reservationCancelDTO.getReservationId());
            houseReservation.setCancelled(true);
            houseReservationService.save(houseReservation);
            if(houseReservation.isAction()){
                houseReservation.setId(null);
                houseReservation.setGuest(null);
                houseReservationService.save(houseReservation);
            }

        }else if(reservationCancelDTO.getIsBoat()){
            BoatReservation boatReservation = this.boatReservationService.getBoatReservationById(reservationCancelDTO.getReservationId());
            boatReservation.setCancelled(true);
            boatReservationService.save(boatReservation);
            if(boatReservation.isAction()){
                boatReservation.setId(null);
                boatReservation.setGuest(null);
                boatReservationService.save(boatReservation);
            }
        }else if(reservationCancelDTO.getIsAdventure()){

        }

        MyUser user = this.myUserService.findUserById(reservationCancelDTO.getGuestId());
        user.setPenalties(user.getPenalties() + 1);
        this.myUserService.save(user);
    }
}
