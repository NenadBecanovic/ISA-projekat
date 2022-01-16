package com.application.bekend.service;

import com.application.bekend.DTO.AdditionalServicesDTO;
import com.application.bekend.DTO.BoatReservationDTO;
import com.application.bekend.DTO.HouseReservationDTO;
import com.application.bekend.DTO.ReservationCheckDTO;
import com.application.bekend.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Service
public class ClientReservationService {

    private final BoatReservationService boatReservationService;
    private final HouseReservationService houseReservationService;
    private final FishingInstructorService fishingInstructorService;
    private final AdditionalServicesService additionalServicesService;
    private final HouseService houseService;
    private final BoatService boatservice;
    private final MyUserService myUserService;
    private final EmailService emailService;

    @Autowired
    public ClientReservationService(BoatReservationService boatReservationService, HouseReservationService houseReservationService, FishingInstructorService fishingInstructorService,
                                    AdditionalServicesService additionalServicesService, HouseService houseService, BoatService boatservice, MyUserService myUserService, EmailService emailService) {
        this.boatReservationService = boatReservationService;
        this.houseReservationService = houseReservationService;
        this.fishingInstructorService = fishingInstructorService;
        this.additionalServicesService = additionalServicesService;
        this.houseService = houseService;
        this.boatservice = boatservice;
        this.myUserService = myUserService;
        this.emailService = emailService;
    }


    public boolean addHouseReservationClient(HouseReservationDTO dto)  {
        ReservationCheckDTO reservationCheckDTO = getReservationCheckDTO(dto);
        House house = this.houseService.getHouseById(dto.getHouseId());
        MyUser guest = this.myUserService.findUserById(dto.getGuestId());
        boolean isAvailable = this.houseReservationService.findHouseAvailability(reservationCheckDTO, dto.getHouseId());
        if(isAvailable){
            Date startDate = new Date(dto.getMilisStartDate());
            Date endDate = new Date(dto.getMilisEndDate());
            HouseReservation houseReservation = new HouseReservation(dto.getId(), startDate, endDate, dto.getMaxGuests(), dto.getPrice(), dto.isAvailable(), house);
            houseReservation.setAvailabilityPeriod(dto.isAvailabilityPeriod());
            houseReservation.setAction(dto.isAction());
            houseReservation.setHouse(house);
            houseReservation.setGuest(guest);
            houseReservation = this.houseReservationService.save(houseReservation);
            addAdditionalServices(dto, houseReservation);
            sendEmailClient(guest, house.getName(), "");
        }

        return isAvailable;
    }

    public boolean addBoatReservationClient(BoatReservationDTO dto){
        ReservationCheckDTO reservationCheckDTO = getReservationCheckDTO(dto);
        Boat boat = this.boatservice.getBoatById(dto.getBoatId());
        MyUser guest = this.myUserService.findUserById(dto.getGuestId());
        boolean isAvailable = this.boatReservationService.findBoatAvailability(reservationCheckDTO, boat.getId());
        if(isAvailable){
            Date startDate = new Date(dto.getMilisStartDate());
            Date endDate = new Date(dto.getMilisEndDate());
            BoatReservation boatReservation = new BoatReservation(dto.getId(), startDate, endDate, dto.getMaxGuests(), dto.getPrice(), dto.isAvailable(), boat);
            boatReservation.setAvailabilityPeriod(dto.isAvailabilityPeriod());
            boatReservation.setAction(dto.isAction());
            boatReservation.setGuest(guest);
            boatReservation = this.boatReservationService.save(boatReservation);
            addAdditionalServices(dto, boatReservation);
            sendEmailClient(guest, "", boat.getName());
        }
        return isAvailable;
    }

    public boolean addAdventureReservationClient(BoatReservationDTO dto){

        return true;
    }


    private void addAdditionalServices(HouseReservationDTO dto, HouseReservation houseReservation) {
        Set<AdditionalServices> additionalServicesSet = new HashSet<>();
        for(AdditionalServicesDTO add : dto.getAdditionalServices()){
            AdditionalServices additionalServices = this.additionalServicesService.getAdditionalServicesById(add.getId());
            additionalServices.addHouseReservation(houseReservation);
            additionalServicesSet.add(additionalServices);
            this.additionalServicesService.save(additionalServices);
        }
    }

    private void addAdditionalServices(BoatReservationDTO dto, BoatReservation houseReservation) {
        Set<AdditionalServices> additionalServicesSet = new HashSet<>();
        for(AdditionalServicesDTO add : dto.getAdditionalServices()){
            AdditionalServices additionalServices = this.additionalServicesService.getAdditionalServicesById(add.getId());
            additionalServices.addBoatReservation(houseReservation);
            additionalServicesSet.add(additionalServices);
            this.additionalServicesService.save(additionalServices);
        }
    }


    private ReservationCheckDTO getReservationCheckDTO(HouseReservationDTO dto) {
        ReservationCheckDTO reservationCheckDTO = new ReservationCheckDTO();
        reservationCheckDTO.setEndMilis(dto.getMilisEndDate());
        reservationCheckDTO.setStartMilis(dto.getMilisStartDate());
        return reservationCheckDTO;
    }

    private ReservationCheckDTO getReservationCheckDTO(BoatReservationDTO dto) {
        ReservationCheckDTO reservationCheckDTO = new ReservationCheckDTO();
        reservationCheckDTO.setEndMilis(dto.getMilisEndDate());
        reservationCheckDTO.setStartMilis(dto.getMilisStartDate());
        return reservationCheckDTO;
    }

    private void sendEmailClient(MyUser guest,String houseName, String boatName){
        try {
            this.emailService.sendMailForClient(guest,houseName, boatName);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }



}




