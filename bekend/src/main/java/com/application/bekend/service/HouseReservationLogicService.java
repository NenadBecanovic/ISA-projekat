package com.application.bekend.service;

import com.application.bekend.DTO.AdditionalServicesDTO;
import com.application.bekend.DTO.HouseReservationDTO;
import com.application.bekend.model.*;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class HouseReservationLogicService {

    private final HouseReservationService houseReservationService;
    private final HouseService houseService;
    private final AdditionalServicesService additionalServicesService;
    private final MyUserService myUserService;
    private final BoatReservationService boatReservationService;
    private final UserCategoryService userCategoryService;
    private final CompanyService companyService;

    public HouseReservationLogicService(HouseReservationService houseReservationService, HouseService houseService, AdditionalServicesService additionalServicesService, MyUserService myUserService, BoatReservationService boatReservationService, UserCategoryService userCategoryService, CompanyService companyService) {
        this.houseReservationService = houseReservationService;
        this.houseService = houseService;
        this.additionalServicesService = additionalServicesService;
        this.myUserService = myUserService;
        this.boatReservationService = boatReservationService;
        this.userCategoryService = userCategoryService;
        this.companyService = companyService;
    }

    @Transactional
    public boolean save(HouseReservationDTO dto) throws MessagingException {
        House house = this.houseService.getHouseById(dto.getHouseId());
        MyUser owner = this.myUserService.findUserByHouseId(dto.getHouseId());

        if (isReservationFree(dto, house)) return false;

        HouseReservation houseReservation = setFields(dto, house);
        houseReservation = this.houseReservationService.save(houseReservation);
        setOwnerPoints(dto, owner);
        setAdditionalServices(dto, houseReservation);
        house.addHouseReservation(houseReservation);
        this.houseService.save(house);

        sendEmailIfClient(dto, house, houseReservation);
        sendEmailIfAction(dto, house);

        return true;
    }

    private void setOwnerPoints(HouseReservationDTO dto, MyUser owner) {
        if(!dto.isAction() && !dto.isAvailabilityPeriod()) {
            owner.setPoints(owner.getPoints() + this.companyService.getCompanyInfo((long) 1).getPointsPerReservationOwner());
            owner = this.checkUserCategory(owner);
        }
    }

    private boolean isReservationFree(HouseReservationDTO dto, House house) {
        if (!houseIsFree(dto, house)) return true;
        if (!clientHasNoHouseReservations(dto)) return true;
        if (!clientHasNoBoatReservations(dto)) return true;
        return false;
    }

    private HouseReservation setFields(HouseReservationDTO dto, House house) {
        Date startDate = new Date(Long.parseLong(dto.getStartDate()));
        Date endDate = new Date(Long.parseLong(dto.getEndDate()));

        HouseReservation houseReservation = new HouseReservation(dto.getId(), startDate, endDate, dto.getMaxGuests(), dto.getPrice(), dto.isAvailable(), house);
        houseReservation.setAvailabilityPeriod(dto.isAvailabilityPeriod());
        houseReservation.setAction(dto.isAction());
        houseReservation.setCancelled(dto.getCancelled());
        return houseReservation;
    }

    private void setAdditionalServices(HouseReservationDTO dto, HouseReservation houseReservation) {
        Set<AdditionalServices> additionalServicesSet = new HashSet<>();
        for(AdditionalServicesDTO add : dto.getAdditionalServices()){
            AdditionalServices additionalServices = this.additionalServicesService.getAdditionalServicesById(add.getId());
            additionalServices.addHouseReservation(houseReservation); // (***)
            additionalServicesSet.add(additionalServices);
            this.additionalServicesService.save(additionalServices);
        }
    }

    private void sendEmailIfClient(HouseReservationDTO dto, House house, HouseReservation houseReservation) throws MessagingException {
        if (dto.getGuestId() != null && dto.getGuestId() != 0 && dto.isAvailable() == false) {
            MyUser guest = this.myUserService.findUserById(dto.getGuestId());
            houseReservation.setGuest(guest);
            this.houseService.save(house);

            Set<HouseReservation> houseReservations1 = guest.getHouseReservations();
            houseReservations1.add(houseReservation);
            guest.setHouseReservations(houseReservations1);
            guest.setPoints(guest.getPoints() + this.companyService.getCompanyInfo((long) 1).getPointsPerReservationClient());
            this.checkUserCategory(guest);
            guest = this.myUserService.save(guest);

            // ako je vlasnik zakazao za klijenta, poslati mejl klijentu
            this.myUserService.sendMailToClient(dto, null, null, house.getName(), "", "");
        }
    }

    private void sendEmailIfAction(HouseReservationDTO dto, House house) throws MessagingException {
        // ako je akcije, poslati mejl svim pretplacenim klijentima
        if (dto.isAction() == true && dto.isAvailable() == true){
            this.myUserService.sendSubscribedUsersEmail(dto, null, null, house.getName(), "", "");
        }
    }

    private boolean clientHasNoBoatReservations(HouseReservationDTO dto) {
        List<BoatReservation> boatReservationsClient = this.boatReservationService.getBoatReservationsByGuestId(dto.getGuestId());
        for (BoatReservation h: boatReservationsClient) {
            Long start =  h.getStartDate().getTime();
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

    // provera da li vec postoji termin za klijenta u izabranom periodu
    private boolean clientHasNoHouseReservations(HouseReservationDTO dto) {
        List<HouseReservation> houseReservationsClient = this.houseReservationService.getHouseReservationsByGuestId(dto.getGuestId());
        for (HouseReservation h: houseReservationsClient) {
            Long start =  h.getStartDate().getTime();
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

    // provera da li vec postoji termin u vikednici u izabranom periodu
    private boolean houseIsFree(HouseReservationDTO dto, House house) {
        List<HouseReservation> houseReservations = this.houseReservationService.getAllByHouse_Id(house.getId());
        for (HouseReservation h: houseReservations) {
            Long start = h.getStartDate().getTime();
            Long end = h.getEndDate().getTime();

            if (!h.getCancelled()) {
                if (Long.parseLong(dto.getStartDate()) >= start && Long.parseLong(dto.getEndDate()) <= end ||
                        Long.parseLong(dto.getStartDate()) <= start && Long.parseLong(dto.getEndDate()) >= start ||
                        Long.parseLong(dto.getStartDate()) >= start && Long.parseLong(dto.getStartDate()) <= end) {
                    return false;  // poklapanje termina
                }
            }
        }
        return true;
    }

    private MyUser checkUserCategory(MyUser user) {
        List<UserCategory> allCategories = this.userCategoryService.findAll();
        int min = 0;
        Long id = (long) 0;
        for(UserCategory category: allCategories) {
            if(category.getPoints() > min && user.getPoints() > category.getPoints()) {
                min = category.getPoints();
                id = category.getId();
            }
        }
        UserCategory cat = this.userCategoryService.getCategoryById(id);
        user.setCategory(cat);
        return this.myUserService.save(user);
    }
}
