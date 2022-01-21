package com.application.bekend.service;

import com.application.bekend.DTO.*;
import com.application.bekend.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
@EnableTransactionManagement
public class ClientReservationService {

    private final BoatReservationService boatReservationService;
    private final HouseReservationService houseReservationService;
    private final FishingAdventureReservationService fishingAdventureReservationService;
    private final FishingInstructorService fishingInstructorService;
    private final AdditionalServicesService additionalServicesService;
    private final HouseService houseService;
    private final BoatService boatservice;
    private final FishingAdventureService fishingAdventureService;
    private final MyUserService myUserService;
    private final EmailService emailService;
    private final UserCategoryService userCategoryService;
	private final CompanyService companyService;

    @Autowired
    public ClientReservationService(BoatReservationService boatReservationService, HouseReservationService houseReservationService, FishingAdventureReservationService fishingAdventureReservationService, FishingInstructorService fishingInstructorService,
                                    AdditionalServicesService additionalServicesService, HouseService houseService, BoatService boatservice, FishingAdventureService fishingAdventureService,
                                    	MyUserService myUserService, EmailService emailService, UserCategoryService userCategoryService, CompanyService companyService) {
        this.boatReservationService = boatReservationService;
        this.houseReservationService = houseReservationService;
        this.fishingAdventureReservationService = fishingAdventureReservationService;
        this.fishingInstructorService = fishingInstructorService;
        this.additionalServicesService = additionalServicesService;
        this.houseService = houseService;
        this.boatservice = boatservice;
        this.fishingAdventureService = fishingAdventureService;
        this.myUserService = myUserService;
        this.emailService = emailService;
        this.userCategoryService = userCategoryService;
        this.companyService = companyService;
    }

    @Transactional
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
            guest.setPoints(guest.getPoints() + this.companyService.getCompanyInfo((long) 1).getPointsPerReservationClient());
            this.checkUserCategory(guest);
            houseReservation = this.houseReservationService.save(houseReservation);
            addAdditionalServices(dto, houseReservation);
            sendEmailClient(guest, house.getName(), "", "");
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
            guest.setPoints(guest.getPoints() + this.companyService.getCompanyInfo((long) 1).getPointsPerReservationClient());
            this.checkUserCategory(guest);
            boatReservation = this.boatReservationService.save(boatReservation);
            addAdditionalServices(dto, boatReservation);
            sendEmailClient(guest, "", boat.getName(), "");
        }
        return isAvailable;
    }

    public boolean addAdventureReservationClient(AdventureReservationDTO dto){
        ReservationCheckDTO reservationCheckDTO = getReservationCheckDTO(dto);
        FishingAdventure fishingAdventure = this.fishingAdventureService.getFishingAdventureById(dto.getAdventureId());
        MyUser guest = this.myUserService.findUserById(dto.getGuestId());
        boolean isAvailable =  this.fishingInstructorService.findInstructorAvailability(reservationCheckDTO, dto.getId());
        if(isAvailable){
            Date startDate = new Date(dto.getMilisStartDate());
            Date endDate = new Date(dto.getMilisEndDate());
            AdventureReservation adventureReservation = new AdventureReservation(dto.getId(), startDate, endDate, dto.getMaxGuests(), dto.getPrice(), dto.getIsAvailable(), fishingAdventure);
            adventureReservation.setAvailabilityPeriod(dto.getAvailabilityPeriod());
            adventureReservation.setAction(dto.getIsAction());
            adventureReservation.setGuest(guest);
            guest.setPoints(guest.getPoints() + this.companyService.getCompanyInfo((long) 1).getPointsPerReservationClient());
            this.checkUserCategory(guest);
            adventureReservation = this.fishingAdventureReservationService.save(adventureReservation);
            addAdditionalServices(dto, adventureReservation);
            sendEmailClient(guest,fishingAdventure.getName());
        }
        return isAvailable;
    }


    private void addAdditionalServices(HouseReservationDTO dto, HouseReservation houseReservation) {
        for(AdditionalServicesDTO add : dto.getAdditionalServices()){
            AdditionalServices additionalServices = this.additionalServicesService.getAdditionalServicesById(add.getId());
            additionalServices.addHouseReservation(houseReservation);
            this.additionalServicesService.save(additionalServices);
        }
    }

    private void addAdditionalServices(BoatReservationDTO dto, BoatReservation houseReservation) {
        for(AdditionalServicesDTO add : dto.getAdditionalServices()){
            AdditionalServices additionalServices = this.additionalServicesService.getAdditionalServicesById(add.getId());
            additionalServices.addBoatReservation(houseReservation);
            this.additionalServicesService.save(additionalServices);
        }
    }

    private void addAdditionalServices(AdventureReservationDTO dto, AdventureReservation adventureReservation) {
        for(AdditionalServicesDTO add : dto.getAdditionalServices()){
            AdditionalServices additionalServices = this.additionalServicesService.getAdditionalServicesById(add.getId());
            additionalServices.addAdventureReservation(adventureReservation);
            this.additionalServicesService.save(additionalServices);
        }
    }


    private ReservationCheckDTO getReservationCheckDTO(HouseReservationDTO dto) {
        ReservationCheckDTO reservationCheckDTO = new ReservationCheckDTO();
        reservationCheckDTO.setEndMilis(dto.getMilisEndDate());
        reservationCheckDTO.setStartMilis(dto.getMilisStartDate());
        return reservationCheckDTO;
    }

    private ReservationCheckDTO getReservationCheckDTO(AdventureReservationDTO dto) {
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

    private void sendEmailClient(MyUser guest,String houseName, String boatName, String adventureName){
        try {
            this.emailService.sendMailForClient(guest,houseName, boatName, adventureName);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private void sendEmailClientAction(MyUser guest,String houseName, String boatName){
        try {
            this.emailService.sendMailForClientAction(guest,houseName, boatName);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private void sendEmailClientActionAdventure(MyUser guest,String adventureName){
        try {
            this.emailService.sendMailForClientActionAdventure(guest,adventureName);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private void sendEmailClient(MyUser guest,String adventureName){
        try {
            this.emailService.sendMailForClientAdventure(guest, adventureName);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }


    public Boolean addHouseActionClient(ActionDTO dto) {
        MyUser guest = this.myUserService.findUserById(dto.getUserId());
        HouseReservation houseReservation = this.houseReservationService.getHouseReservationById(dto.getEntityId());
        if(houseReservation == null || !houseReservation.isAction()){
            return false;
        }
        houseReservation.setGuest(guest);
        houseReservation.setAvailable(false);
        guest.setPoints(guest.getPoints() + this.companyService.getCompanyInfo((long) 1).getPointsPerReservationClient());
        this.checkUserCategory(guest);
        this.houseReservationService.save(houseReservation);
        this.sendEmailClientAction(guest,houseReservation.getHouse().getName(),"" );
        return true;
    }

    public Boolean addBoatActionClient(ActionDTO dto) {
        MyUser guest = this.myUserService.findUserById(dto.getUserId());
        BoatReservation boatReservation = this.boatReservationService.getBoatReservationById(dto.getEntityId());
        if(boatReservation == null || !boatReservation.isAction()){
            return false;
        }
        boatReservation.setGuest(guest);
        boatReservation.setAvailable(false);
        guest.setPoints(guest.getPoints() + this.companyService.getCompanyInfo((long) 1).getPointsPerReservationClient());
        this.checkUserCategory(guest);
        this.boatReservationService.save(boatReservation);
        this.sendEmailClientAction(guest,"", boatReservation.getBoat().getName());
        return true;
    }

    public Boolean addAdventureActionClient(ActionDTO dto) {
        MyUser guest = this.myUserService.findUserById(dto.getUserId());
        AdventureReservation adventureReservation = this.fishingAdventureReservationService.getFishingAdventureReservationById(dto.getEntityId());
        if(adventureReservation == null || !adventureReservation.isAction()){
            return false;
        }
        adventureReservation.setGuest(guest);
        adventureReservation.setAvailable(false);
        guest.setPoints(guest.getPoints() + this.companyService.getCompanyInfo((long) 1).getPointsPerReservationClient());
        this.checkUserCategory(guest);
        this.fishingAdventureReservationService.save(adventureReservation);
        this.sendEmailClientActionAdventure(guest, adventureReservation.getFishingAdventure().getName());
        return true;
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




