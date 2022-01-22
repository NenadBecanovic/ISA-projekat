package com.application.bekend.service;

import com.application.bekend.DTO.AdditionalServicesDTO;
import com.application.bekend.DTO.BoatReservationDTO;
import com.application.bekend.model.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class BoatReservationLogicService {

    private final BoatReservationService boatReservationService;
    private final AdditionalServicesService additionalServicesService;
    private final BoatService boatService;
    private final MyUserService myUserService;
    private final HouseReservationService houseReservationService;
    private final UserCategoryService userCategoryService;
    private final CompanyService companyService;

    public BoatReservationLogicService(BoatReservationService boatReservationService, AdditionalServicesService additionalServicesService, BoatService boatService, MyUserService myUserService, HouseReservationService houseReservationService, UserCategoryService userCategoryService, CompanyService companyService) {
        this.boatReservationService = boatReservationService;
        this.additionalServicesService = additionalServicesService;
        this.boatService = boatService;
        this.myUserService = myUserService;
        this.houseReservationService = houseReservationService;
        this.userCategoryService = userCategoryService;
        this.companyService = companyService;
    }

    @Transactional
    public boolean save(BoatReservationDTO dto) throws MessagingException {
        Boat boat = this.boatService.getBoatById(dto.getBoatId());
        MyUser owner = this.myUserService.findUserByBoatId(dto.getBoatId());

        if (isReservationFree(dto, boat)) return false;

        BoatReservation boatReservation = setFields(dto, boat);
        boatReservation =this.boatReservationService.save(boatReservation);
        setOwnerPoints(dto, owner);
        setAdditionalServices(dto, boatReservation);
        boat.addBoatReservation(boatReservation);
        this.boatService.save(boat);

        sendEmailIfClient(dto, boat, boatReservation);
        sendEmailIfAction(dto, boat);

        return true;
    }

    private boolean isReservationFree(BoatReservationDTO dto, Boat boat) {
        if (!boatIsFree(dto, boat)) return true;
        if (!clientHasNoHouseReservations(dto)) return true;
        if (!clientHasNoBoatReservations(dto)) return true;
        return false;
    }

    private boolean boatIsFree(BoatReservationDTO dto, Boat boat) {
        List<BoatReservation> boatReservations = this.boatReservationService.getAllByBoat_Id(boat.getId());
        for(BoatReservation h:boatReservations)
        {
            Long start = h.getStartDate().getTime();
            Long end = h.getEndDate().getTime();

            if (!h.getCancelled()) {
                if (Long.parseLong(dto.getStartDate()) >= start && Long.parseLong(dto.getEndDate()) <= end ||
                        Long.parseLong(dto.getStartDate()) <= start && Long.parseLong(dto.getEndDate()) >= start ||
                        Long.parseLong(dto.getStartDate()) >= start && Long.parseLong(dto.getStartDate()) <= end) {
                    return false; // poklapanje termina
                }
            }
        }
        return true;
    }

    private boolean clientHasNoHouseReservations(BoatReservationDTO dto) {
        List<HouseReservation> houseReservationsClient = this.houseReservationService.getHouseReservationsByGuestId(dto.getGuestId());
        for(HouseReservation h:houseReservationsClient)
        {
            Long start = h.getStartDate().getTime();
            Long end = h.getEndDate().getTime();

            if (!h.getCancelled()) {
                if (Long.parseLong(dto.getStartDate()) >= start && Long.parseLong(dto.getEndDate()) <= end ||
                        Long.parseLong(dto.getStartDate()) <= start && Long.parseLong(dto.getEndDate()) >= start ||
                        Long.parseLong(dto.getStartDate()) >= start && Long.parseLong(dto.getStartDate()) <= end) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean clientHasNoBoatReservations(BoatReservationDTO dto) {
        List<BoatReservation> boatReservationsClient = this.boatReservationService.getBoatReservationsByGuestId(dto.getGuestId());
        for(BoatReservation h:boatReservationsClient)
        {
            Long start = h.getStartDate().getTime();
            Long end = h.getEndDate().getTime();

            if (!h.getCancelled()) {
                if (Long.parseLong(dto.getStartDate()) >= start && Long.parseLong(dto.getEndDate()) <= end ||
                        Long.parseLong(dto.getStartDate()) <= start && Long.parseLong(dto.getEndDate()) >= start ||
                        Long.parseLong(dto.getStartDate()) >= start && Long.parseLong(dto.getStartDate()) <= end) {
                    return false;
                }
            }
        }
        return true;
    }

    private void sendEmailIfAction(BoatReservationDTO dto, Boat boat) throws MessagingException {
        if(dto.isAction()==true&& dto.isAvailable()==true)
        {
            this.myUserService.sendSubscribedUsersEmail(null, dto, null, "", boat.getName(), "");
        }
    }

    private void sendEmailIfClient(BoatReservationDTO dto, Boat boat, BoatReservation boatReservation) throws MessagingException {
        if(dto.getGuestId()!=null && dto.getGuestId()!=0 && dto.isAvailable()==false)
        {
            MyUser guest = this.myUserService.findUserById(dto.getGuestId());
            boatReservation.setGuest(guest);
            this.boatService.save(boat);

            Set<BoatReservation> boatReservations1 = guest.getBoatReservations();
            boatReservations1.add(boatReservation);
            guest.setBoatReservations(boatReservations1);
            guest.setPoints(guest.getPoints() + this.companyService.getCompanyInfo((long) 1).getPointsPerReservationClient());
            this.checkUserCategory(guest);
            guest = this.myUserService.save(guest);

            // ako je vlasnik zakazao za klijenta, poslati mejl klijentu
            this.myUserService.sendMailToClient(null, dto, null, "", boat.getName(), "");
        }
    }

    private void setAdditionalServices(BoatReservationDTO dto, BoatReservation boatReservation) {
        Set<AdditionalServices> additionalServicesSet = new HashSet<>();
        for(AdditionalServicesDTO add : dto.getAdditionalServices())
        {
            AdditionalServices additionalServices = this.additionalServicesService.getAdditionalServicesById(add.getId());
            additionalServices.addBoatReservation(boatReservation); // (***)
            additionalServicesSet.add(additionalServices);
            this.additionalServicesService.save(additionalServices);
        }
    }

    private BoatReservation setFields(BoatReservationDTO dto, Boat boat) {
        Date startDate = new Date(Long.parseLong(dto.getStartDate()));
        Date endDate = new Date(Long.parseLong(dto.getEndDate()));
        BoatReservation boatReservation = new BoatReservation(dto.getId(), startDate, endDate, dto.getMaxGuests(), dto.getPrice(), dto.isAvailable(), boat);
        boatReservation.setAvailabilityPeriod(dto.isAvailabilityPeriod());
        boatReservation.setAction(dto.isAction());
        boatReservation.setCancelled(dto.getCancelled());
        return boatReservation;
    }

    private void setOwnerPoints(BoatReservationDTO dto, MyUser owner) {
        if(!dto.isAction()&&!dto.isAvailabilityPeriod()) {
            owner.setPoints(owner.getPoints() + this.companyService.getCompanyInfo((long) 1).getPointsPerReservationOwner());
            owner = this.checkUserCategory(owner);
        }
    }

    private MyUser checkUserCategory(MyUser user) {
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
        return this.myUserService.save(user);
    }

    @Transactional
    public boolean delete(Long id) {
        BoatReservation boatReservation = this.boatReservationService.getBoatReservationById(id);

        boatReservation = deleteAllFields(boatReservation);
        Boat boat = this.boatService.getBoatById(boatReservation.getBoat().getId());
        boat.removeBoatReservation(boatReservation);
        this.boatService.save(boat);

        return true;
    }

    private BoatReservation deleteAllFields(BoatReservation boatReservation) {
        removeAdditionalServices(boatReservation);
        boatReservation.setGuest(null);
        boatReservation = this.boatReservationService.save(boatReservation);
        this.boatReservationService.delete(boatReservation.getId());
        return boatReservation;
    }

    private void removeAdditionalServices(BoatReservation boatReservation) {
        Set<AdditionalServices> additionalServices =  boatReservation.getAdditionalServices();
        for(AdditionalServices a: additionalServices){
            a.getBoatReservationsServices().remove(boatReservation);
            this.additionalServicesService.save(a);
        }
    }
}
