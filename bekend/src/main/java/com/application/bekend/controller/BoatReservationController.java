package com.application.bekend.controller;

import com.application.bekend.DTO.*;
import com.application.bekend.model.*;
import com.application.bekend.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.util.*;

@RestController
@RequestMapping("api/boatReservations")
@EnableTransactionManagement
public class BoatReservationController {

    private final BoatReservationService boatReservationService;
    private final AdditionalServicesService additionalServicesService;
    private final BoatService boatService;
    private final MyUserService myUserService;
    private final HouseReservationService houseReservationService;
    private final UserCategoryService userCategoryService;
	private final CompanyService companyService;
    private final BoatReservationLogicService boatReservationLogicService;

    @Autowired
    public BoatReservationController(BoatReservationService boatReservationService, AdditionalServicesService additionalServicesService, BoatService boatService, MyUserService myUserService, HouseReservationService houseReservationService, UserCategoryService userCategoryService, CompanyService companyService, BoatReservationLogicService boatReservationLogicService) {
        this.boatReservationService = boatReservationService;
        this.additionalServicesService = additionalServicesService;
        this.boatService = boatService;
        this.myUserService = myUserService;
        this.houseReservationService = houseReservationService;
        this.userCategoryService = userCategoryService;
        this.companyService = companyService;
        this.boatReservationLogicService = boatReservationLogicService;
    }

    @GetMapping("/getAllByBoatId/{id}")
    public ResponseEntity<List<BoatReservationSlideDTO>> getAllByBoatId(@PathVariable("id") Long id){
        List<BoatReservation> boatReservations = this.boatReservationService.getAllByBoat_Id(id);

        List<BoatReservationDTO> boatReservationDTOS = new ArrayList<>();
        List<BoatReservationSlideDTO> boatReservationSlideDTOS = new ArrayList<>();

        for (BoatReservation a: boatReservations) {
            String startDate = (String.valueOf(a.getStartDate().getTime()));
            String endDate = (String.valueOf(a.getEndDate().getTime()));

            BoatReservationDTO boatReservationDTO = new BoatReservationDTO(a.getId(), startDate, endDate, a.getMaxGuests(), a.getPrice(), a.isAvailable());
            boatReservationDTO.setCancelled(a.getCancelled());

            Set<AdditionalServicesDTO> additionalServicesDTOS = new HashSet<>();
            for(AdditionalServices add : this.additionalServicesService.getAllByBoatReservationId(a.getId())){  // a.getAdditionalServices()
                AdditionalServicesDTO newAddSer = new AdditionalServicesDTO(add.getId(), add.getName(), add.getPrice());
                additionalServicesDTOS.add(newAddSer);
            }

            boatReservationDTO.setAdditionalServices(additionalServicesDTOS);
            boatReservationDTOS.add(boatReservationDTO);
        }

        List<BoatReservationDTO> reservationDTOS = new ArrayList<>();
        int i = 1;
        for (BoatReservationDTO dto: boatReservationDTOS) {
            reservationDTOS.add(dto);
            if (i % 3 == 0) {
                BoatReservationSlideDTO boatReservationSlideDTO = new BoatReservationSlideDTO(reservationDTOS);
                boatReservationSlideDTOS.add(boatReservationSlideDTO);
                reservationDTOS = new ArrayList<>();
            }
            i = i +1;
        }

        if(reservationDTOS.size() != 0){
            BoatReservationSlideDTO boatReservationSlideDTO = new BoatReservationSlideDTO(reservationDTOS);
            boatReservationSlideDTOS.add(boatReservationSlideDTO);
        }

        return new ResponseEntity<>(boatReservationSlideDTOS, HttpStatus.OK);
    }

    @PostMapping("/add")
    @Transactional
    public ResponseEntity<BoatReservation> save(@RequestBody BoatReservationDTO dto) throws MessagingException {
        boolean response = this.boatReservationLogicService.save(dto);
        if (response)
        {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        else
        {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("/delete/{id}")
    @Transactional
    public ResponseEntity<Boolean> delete(@PathVariable("id") Long id) {
        boolean result = this.boatReservationLogicService.delete(id);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @GetMapping("/getAllActionsByBoatId/{id}")
    public ResponseEntity<List<BoatReservationSlideDTO>> getAllActionsByBoatId(@PathVariable("id") Long id) {
        List<BoatReservation> boatReservations = this.boatReservationService.getAllByBoat_Id(id);

        List<BoatReservationDTO> boatReservationDTOS = new ArrayList<>();
        List<BoatReservationSlideDTO> boatReservationSlideDTOS = new ArrayList<>();

        for (BoatReservation a : boatReservations) {
            if (a.isAction() == true && a.isAvailable() == true) {   // ako je akcija koja je slobodna (nije zauzeta)
                String startDate = (String.valueOf(a.getStartDate().getTime()));
                String endDate = (String.valueOf(a.getEndDate().getTime()));

                BoatReservationDTO boatReservationDTO = new BoatReservationDTO(a.getId(), startDate, endDate, a.getMaxGuests(), a.getPrice(), a.isAvailable());
                boatReservationDTO.setAvailabilityPeriod(a.isAvailabilityPeriod());
                boatReservationDTO.setAction(a.isAction());
                boatReservationDTO.setCancelled(a.getCancelled());

                if (a.getGuest() != null) {
                    boatReservationDTO.setGuestId(a.getGuest().getId());
                }

                Set<AdditionalServicesDTO> additionalServicesDTOS = new HashSet<>();
                // dobavljamo set dodatnih usluga za onu konkretnu rezervaciju iz baze i pretvaramo u DTO (a mozemo samo i pristupiti setu dodatnih usluga direktno preko rezervacije (a.getAdditionalServices()))
                for (AdditionalServices add : this.additionalServicesService.getAllByBoatReservationId(a.getId())) {  // a.getAdditionalServices()
                    AdditionalServicesDTO newAddSer = new AdditionalServicesDTO(add.getId(), add.getName(), add.getPrice());
                    additionalServicesDTOS.add(newAddSer);
                }

                boatReservationDTO.setAdditionalServices(additionalServicesDTOS);
                boatReservationDTOS.add(boatReservationDTO);  // lista svih HouseReservationDTO - treba nam zbog slidera (3 po 3 cemo slati)
            }
        }

        List<BoatReservationDTO> reservationDTOS = new ArrayList<>();
        int i = 1;
        for (BoatReservationDTO dto : boatReservationDTOS) {
            reservationDTOS.add(dto);
            if (i % 3 == 0) {
                BoatReservationSlideDTO boatReservationSlideDTO = new BoatReservationSlideDTO(reservationDTOS);
                boatReservationSlideDTOS.add(boatReservationSlideDTO);
                reservationDTOS = new ArrayList<>();
            }
            i = i + 1;
        }

        if (reservationDTOS.size() != 0) {
            BoatReservationSlideDTO boatReservationSlideDTO = new BoatReservationSlideDTO(reservationDTOS);
            boatReservationSlideDTOS.add(boatReservationSlideDTO);
        }

        return new ResponseEntity<>(boatReservationSlideDTOS, HttpStatus.OK);
    }

    @GetMapping("/getAllByBoatIdPlane/{id}")  // TODO: iskoristiti za prikaz svih rezervacija na kalendaru (nije potreban slajder)
    public ResponseEntity<List<BoatReservationDTO>> getAllByBoatIdPlane(@PathVariable("id") Long id) {
        List<BoatReservation> boatReservations = this.boatReservationService.getAllByBoat_Id(id);

        List<BoatReservationDTO> boatReservationDTOS = new ArrayList<>();

        for (BoatReservation a : boatReservations) {
            String startDate = (String.valueOf(a.getStartDate().getTime()));
            String endDate = (String.valueOf(a.getEndDate().getTime()));

            BoatReservationDTO boatReservationDTO = new BoatReservationDTO(a.getId(), startDate, endDate, a.getMaxGuests(), a.getPrice(), a.isAvailable());
            boatReservationDTO.setAvailabilityPeriod(a.isAvailabilityPeriod());
            boatReservationDTO.setAction(a.isAction());
            boatReservationDTO.setCancelled(a.getCancelled());

            if (a.getGuest() != null) {
                boatReservationDTO.setGuestId(a.getGuest().getId());
            }

            Set<AdditionalServicesDTO> additionalServicesDTOS = new HashSet<>();
            // dobavljamo set dodatnih usluga za onu konkretnu rezervaciju iz baze i pretvaramo u DTO (a mozemo samo i pristupiti setu dodatnih usluga direktno preko rezervacije (a.getAdditionalServices()))
            for(AdditionalServices add : this.additionalServicesService.getAllByBoatReservationId(a.getId())){  // a.getAdditionalServices()
                AdditionalServicesDTO newAddSer = new AdditionalServicesDTO(add.getId(), add.getName(), add.getPrice());
                additionalServicesDTOS.add(newAddSer);
            }

            boatReservationDTO.setAdditionalServices(additionalServicesDTOS);
            boatReservationDTOS.add(boatReservationDTO);
        }

        return new ResponseEntity<>(boatReservationDTOS, HttpStatus.OK);
    }

    @GetMapping("/getBoatReservationsByGuestId/{id}")
    public ResponseEntity<List<BoatReservationDTO>> getBoatReservationsByGuestId(@PathVariable("id") Long id) {
        List<BoatReservation> boatReservations = this.boatReservationService.getBoatReservationsByGuestId(id);
        List<BoatReservationDTO> boatReservationDTOS = new ArrayList<>();

        for (BoatReservation h: boatReservations) {
            String startDate = (String.valueOf(h.getStartDate().getTime()));
            String endDate = (String.valueOf(h.getEndDate().getTime()));

            Long startDateMilis = h.getStartDate().getTime();
            Long endDateMilis = h.getEndDate().getTime();

            BoatReservationDTO dto = new BoatReservationDTO(h.getBoat().getId(), h.getId(), startDate, endDate, h.getMaxGuests(),
                    h.getPrice(), h.isAvailable());
            dto.setAvailabilityPeriod(h.isAvailabilityPeriod());
            dto.setAction(h.isAction());
            dto.setCancelled(h.getCancelled());

            if (h.getGuest() != null) {
                dto.setGuestId(h.getGuest().getId());
            }

            dto.setMilisStartDate(startDateMilis);
            dto.setMilisEndDate(endDateMilis);
            dto.setHasAppealEntity(h.getHasAppealEntity());
            dto.setHasAppealOwner(h.getHasAppealOwner());
            dto.setHasFeedbackEntity(h.getHasFeedbackEntity());
            dto.setHasFeedbackOwner(h.getHasFeedbackOwner());

            dto.setTotalPrice(this.boatReservationService.findTotalPriceForBoatReservation(h));
            dto.setEntityName(h.getBoat().getName());
            this.boatReservationService.canBeCancelled(dto,h);
            dto.setCancelled(h.getCancelled());

            Set<AdditionalServicesDTO> additionalServicesDTOS = new HashSet<>();
            for(AdditionalServices add : this.additionalServicesService.getAllByBoatReservationId(h.getId())) {
                AdditionalServicesDTO newAddSer = new AdditionalServicesDTO(add.getId(), add.getName(), add.getPrice());
                additionalServicesDTOS.add(newAddSer);
            }
            dto.setAdditionalServices(additionalServicesDTOS);

            boatReservationDTOS.add(dto);
        }
        return new ResponseEntity<>(boatReservationDTOS, HttpStatus.OK);
    }

    @GetMapping("/getBoatReservationByBoatOwnerId/{id}")
    public ResponseEntity<List<BoatReservationDTO>> getBoatReservationByBoatOwnerId(@PathVariable("id") Long id) {
        List<BoatReservation> boatReservations = this.boatReservationService.getBoatReservationByBoatOwnerId(id);
        List<BoatReservationDTO> boatReservationDTOS = new ArrayList<>();

        for (BoatReservation h: boatReservations) {
            String startDate = (String.valueOf(h.getStartDate().getTime()));
            String endDate = (String.valueOf(h.getEndDate().getTime()));

            BoatReservationDTO dto = new BoatReservationDTO(h.getBoat().getId(), h.getId(), startDate, endDate, h.getMaxGuests(),
                    h.getPrice(), h.isAvailable());
            dto.setAvailabilityPeriod(h.isAvailabilityPeriod());
            dto.setAction(h.isAction());
            dto.setCancelled(h.getCancelled());

            if (h.getGuest() != null) {
                dto.setGuestId(h.getGuest().getId());
            }

            Set<AdditionalServicesDTO> additionalServicesDTOS = new HashSet<>();
            for(AdditionalServices add : this.additionalServicesService.getAllByBoatReservationId(h.getId())) {
                AdditionalServicesDTO newAddSer = new AdditionalServicesDTO(add.getId(), add.getName(), add.getPrice());
                additionalServicesDTOS.add(newAddSer);
            }
            dto.setAdditionalServices(additionalServicesDTOS);

            boatReservationDTOS.add(dto);
        }
        return new ResponseEntity<>(boatReservationDTOS, HttpStatus.OK);
    }

    @GetMapping("/getCompanyProfit/{startDate}/{endDate}")
    public ResponseEntity<Double> getCompanyInfo(@PathVariable("startDate") String startDate, @PathVariable("endDate") String endDate){
    	List<BoatReservation> boatReservations = this.boatReservationService.findAll();
		CompanyDTO company = this.companyService.getCompanyInfo((long) 1);
		double profit = 0;
        for (BoatReservation a : boatReservations) { 
        	Long reservationStartDate = a.getStartDate().getTime();
            Long today = new Date().getTime();
            double adventurePrice = 0;

            if (reservationStartDate < today && Long.parseLong(startDate) <= reservationStartDate && Long.parseLong(endDate) >= reservationStartDate) {
            	adventurePrice += a.getPrice();

	            Set<AdditionalServicesDTO> additionalServicesDTOS = new HashSet<>();
	            // dobavljamo set dodatnih usluga za onu konkretnu rezervaciju iz baze i pretvaramo u DTO (a mozemo samo i pristupiti setu dodatnih usluga direktno preko rezervacije (a.getAdditionalServices()))
	            for (AdditionalServices add : a.getAdditionalServices()) {  // a.getAdditionalServices()
	            	adventurePrice += add.getPrice();
	            }
	            double companyProfit = adventurePrice * company.getPercentagePerReservation() * 0.01;
	            double clientBenefit = a.getGuest().getCategory().getDiscountPercentage() * companyProfit * 0.01;
	            double ownerBenefit = a.getBoat().getOwner().getCategory().getDiscountPercentage() * companyProfit * 0.01;
	            profit += companyProfit - clientBenefit - ownerBenefit;
            }
        }
        
        return new ResponseEntity<>(profit, HttpStatus.OK);
    }
}
