package com.application.bekend.controller;

import com.application.bekend.DTO.*;
import com.application.bekend.model.*;
import com.application.bekend.service.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.util.*;

@RestController
@RequestMapping("api/houseReservations")
@EnableTransactionManagement
public class HouseReservationController {

    private final HouseReservationService houseReservationService;
    private final HouseService houseService;
    private final AdditionalServicesService additionalServicesService;
    private final MyUserService myUserService;
    private final ModelMapper modelMapper;
    private final BoatReservationService boatReservationService;
    private final UserCategoryService userCategoryService;
	private final CompanyService companyService;
    private final HouseReservationLogicService houseReservationLogicService;

    @Autowired
    public HouseReservationController(HouseReservationService houseReservationService, HouseService houseService, AdditionalServicesService additionalServicesService, MyUserService myUserService, ModelMapper modelMapper, BoatReservationService boatReservationService, UserCategoryService userCategoryService, CompanyService companyService, HouseReservationLogicService houseReservationLogicService) {
        this.houseReservationService = houseReservationService;
        this.houseService = houseService;
        this.additionalServicesService = additionalServicesService;
        this.myUserService = myUserService;
        this.modelMapper = modelMapper;
        this.boatReservationService = boatReservationService;
        this.userCategoryService = userCategoryService;
        this.companyService = companyService;
        this.houseReservationLogicService = houseReservationLogicService;
    }

    @GetMapping("/getAllByHouseId/{id}")
    public ResponseEntity<List<HouseReservationSlideDTO>> getAllByHouseId(@PathVariable("id") Long id) {
        List<HouseReservation> houseReservations = this.houseReservationService.getAllByHouse_Id(id);

        List<HouseReservationDTO> houseReservationDTOS = new ArrayList<>();
        List<HouseReservationSlideDTO> houseReservationSlideDTOS = new ArrayList<>();

        for (HouseReservation a : houseReservations) {
            String startDate = (String.valueOf(a.getStartDate().getTime()));
            String endDate = (String.valueOf(a.getEndDate().getTime()));

            HouseReservationDTO houseReservationDTO = new HouseReservationDTO(a.getId(), startDate, endDate, a.getMaxGuests(), a.getPrice(), a.isAvailable());
            houseReservationDTO.setAvailabilityPeriod(a.isAvailabilityPeriod());
            houseReservationDTO.setAction(a.isAction());
            houseReservationDTO.setCancelled(a.getCancelled());
            if (a.getGuest() != null) {
                houseReservationDTO.setGuestId(a.getGuest().getId());
            }

            Set<AdditionalServicesDTO> additionalServicesDTOS = new HashSet<>();
            // dobavljamo set dodatnih usluga za onu konkretnu rezervaciju iz baze i pretvaramo u DTO (a mozemo samo i pristupiti setu dodatnih usluga direktno preko rezervacije (a.getAdditionalServices()))
            for(AdditionalServices add : this.additionalServicesService.getAllByHouseReservationId(a.getId())){  // a.getAdditionalServices()
                AdditionalServicesDTO newAddSer = new AdditionalServicesDTO(add.getId(), add.getName(), add.getPrice());
                additionalServicesDTOS.add(newAddSer);
            }

            houseReservationDTO.setAdditionalServices(additionalServicesDTOS);
            houseReservationDTOS.add(houseReservationDTO);  // lista svih HouseReservationDTO - treba nam zbog slidera (3 po 3 cemo slati)
        }

        List<HouseReservationDTO> reservationDTOS = new ArrayList<>();
        int i = 1;
        for (HouseReservationDTO dto : houseReservationDTOS) {
            reservationDTOS.add(dto);
            if (i % 3 == 0) {
                HouseReservationSlideDTO houseReservationSlideDTO = new HouseReservationSlideDTO(reservationDTOS);
                houseReservationSlideDTOS.add(houseReservationSlideDTO);
                reservationDTOS = new ArrayList<>();
            }
            i = i + 1;
        }

        if (reservationDTOS.size() != 0) {
            HouseReservationSlideDTO houseReservationSlideDTO = new HouseReservationSlideDTO(reservationDTOS);
            houseReservationSlideDTOS.add(houseReservationSlideDTO);
        }

        return new ResponseEntity<>(houseReservationSlideDTOS, HttpStatus.OK);
    }

    @GetMapping("/getAllActionsByHouseId/{id}")
    public ResponseEntity<List<HouseReservationSlideDTO>> getAllActionsByHouseId(@PathVariable("id") Long id) {
        List<HouseReservation> houseReservations = this.houseReservationService.getAllByHouse_Id(id);

        List<HouseReservationDTO> houseReservationDTOS = new ArrayList<>();
        List<HouseReservationSlideDTO> houseReservationSlideDTOS = new ArrayList<>();

        for (HouseReservation a : houseReservations) {
            if (a.isAction() == true && a.isAvailable() == true) {   // ako je akcija koja je slobodna (nije zauzeta)
                String startDate = (String.valueOf(a.getStartDate().getTime()));
                String endDate = (String.valueOf(a.getEndDate().getTime()));

                HouseReservationDTO houseReservationDTO = new HouseReservationDTO(a.getId(), startDate, endDate, a.getMaxGuests(), a.getPrice(), a.isAvailable());
                houseReservationDTO.setAvailabilityPeriod(a.isAvailabilityPeriod());
                houseReservationDTO.setAction(a.isAction());
                houseReservationDTO.setCancelled(a.getCancelled());
                if (a.getGuest() != null) {
                    houseReservationDTO.setGuestId(a.getGuest().getId());
                }

                Set<AdditionalServicesDTO> additionalServicesDTOS = new HashSet<>();
                // dobavljamo set dodatnih usluga za onu konkretnu rezervaciju iz baze i pretvaramo u DTO (a mozemo samo i pristupiti setu dodatnih usluga direktno preko rezervacije (a.getAdditionalServices()))
                for (AdditionalServices add : this.additionalServicesService.getAllByHouseReservationId(a.getId())) {  // a.getAdditionalServices()
                    AdditionalServicesDTO newAddSer = new AdditionalServicesDTO(add.getId(), add.getName(), add.getPrice());
                    additionalServicesDTOS.add(newAddSer);
                }

                houseReservationDTO.setAdditionalServices(additionalServicesDTOS);
                houseReservationDTOS.add(houseReservationDTO);  // lista svih HouseReservationDTO - treba nam zbog slidera (3 po 3 cemo slati)
            }
        }

        List<HouseReservationDTO> reservationDTOS = new ArrayList<>();
        int i = 1;
        for (HouseReservationDTO dto : houseReservationDTOS) {
            reservationDTOS.add(dto);
            if (i % 3 == 0) {
                HouseReservationSlideDTO houseReservationSlideDTO = new HouseReservationSlideDTO(reservationDTOS);
                houseReservationSlideDTOS.add(houseReservationSlideDTO);
                reservationDTOS = new ArrayList<>();
            }
            i = i + 1;
        }

        if (reservationDTOS.size() != 0) {
            HouseReservationSlideDTO houseReservationSlideDTO = new HouseReservationSlideDTO(reservationDTOS);
            houseReservationSlideDTOS.add(houseReservationSlideDTO);
        }

        return new ResponseEntity<>(houseReservationSlideDTOS, HttpStatus.OK);
    }

    @GetMapping("/getAllByHouseIdPlane/{id}")  // TODO: iskoristiti za prikaz svih rezervacija na kalendaru (nije potreban slajder)
    public ResponseEntity<List<HouseReservationDTO>> getAllByHouseIdPlane(@PathVariable("id") Long id) {
        List<HouseReservation> houseReservations = this.houseReservationService.getAllByHouse_Id(id);

        List<HouseReservationDTO> houseReservationDTOS = new ArrayList<>();

        for (HouseReservation a : houseReservations) {
            String startDate = (String.valueOf(a.getStartDate().getTime()));
            String endDate = (String.valueOf(a.getEndDate().getTime()));

            HouseReservationDTO houseReservationDTO = new HouseReservationDTO(a.getId(), startDate, endDate, a.getMaxGuests(), a.getPrice(), a.isAvailable());
            houseReservationDTO.setAvailabilityPeriod(a.isAvailabilityPeriod());
            houseReservationDTO.setAction(a.isAction());
            houseReservationDTO.setCancelled(a.getCancelled());
            if (a.getGuest() != null) {
                houseReservationDTO.setGuestId(a.getGuest().getId());
            }

            Set<AdditionalServicesDTO> additionalServicesDTOS = new HashSet<>();
            // dobavljamo set dodatnih usluga za onu konkretnu rezervaciju iz baze i pretvaramo u DTO (a mozemo samo i pristupiti setu dodatnih usluga direktno preko rezervacije (a.getAdditionalServices()))
            for(AdditionalServices add : this.additionalServicesService.getAllByHouseReservationId(a.getId())){  // a.getAdditionalServices()
                AdditionalServicesDTO newAddSer = new AdditionalServicesDTO(add.getId(), add.getName(), add.getPrice());
                additionalServicesDTOS.add(newAddSer);
            }

            houseReservationDTO.setAdditionalServices(additionalServicesDTOS);
            houseReservationDTOS.add(houseReservationDTO);  // lista svih HouseReservationDTO - treba nam zbog slidera (3 po 3 cemo slati)
        }

        return new ResponseEntity<>(houseReservationDTOS, HttpStatus.OK);
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_HOUSE_OWNER')")
    @Transactional
    public ResponseEntity<HouseReservation> save(@RequestBody HouseReservationDTO dto) throws MessagingException {
       boolean response = this.houseReservationLogicService.save(dto);
       if (response)
       {
           return new ResponseEntity<>(HttpStatus.CREATED);
       }
       else
       {
           return new ResponseEntity<>(HttpStatus.CONFLICT);
       }
    }

    // kod brisanja:
    // mora biti Transactional metoda + EnableTransactionManagement klasa
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_HOUSE_OWNER')")
    @Transactional
    public ResponseEntity<Boolean> delete(@PathVariable("id") Long id) {
        HouseReservation houseReservation = this.houseReservationService.getHouseReservationById(id);   // dobavimo rezervaciju iz baze

        Set<AdditionalServices> additionalServices =  houseReservation.getAdditionalServices();     // ne moramo direktno iz baze dobavljati jer ova lista u sebi ima objekte sa svojim pravim id-jevima
        for(AdditionalServices a: additionalServices){
            a.getHouseReservationsServices().remove(houseReservation);  // iz niza rezervacija dodatnih usluga izbacimo ovu rezervaciju koju brisemo - raskinuta u tabeli additional_services_house_reservation (sa vodece strane, jer je kod AdditionalService JoinTable)
            this.additionalServicesService.save(a);
        }

        houseReservation.setGuest(null);
        houseReservation.setHouse(null);  // raskinuta veza u tabeli house_reservation_table (sa strane vodece veze u ManyToMany vezi)
        houseReservation = this.houseReservationService.save(houseReservation);
        this.houseReservationService.delete(houseReservation.getId());  // brisanje rezervacije iz house_reservation tabele

        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @GetMapping("/getHouseReservationById/{id}")
    public ResponseEntity<HouseReservationDTO> getHouseReservationById(@PathVariable("id") Long id) {
        HouseReservation houseReservation = this.houseReservationService.getHouseReservationById(id);

        // TODO: izmeniti oblik za datum !!
        HouseReservationDTO dto = new HouseReservationDTO(houseReservation.getId(), houseReservation.getStartDate().toString(), houseReservation.getEndDate().toString(),
                houseReservation.getMaxGuests(), houseReservation.getPrice(), houseReservation.isAvailable());
        dto.setAction(houseReservation.isAction());
        dto.setAvailabilityPeriod(houseReservation.isAvailabilityPeriod());
        dto.setCancelled(houseReservation.getCancelled());

        if (houseReservation.getGuest() != null) {
            dto.setGuestId(houseReservation.getGuest().getId());
        }

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("/getHouseReservationByUserId/{id}")
    public ResponseEntity<List<HouseReservationDTO>> getHouseReservationByUserId(@PathVariable("id") Long id) {
        List<HouseReservation> houseReservations = this.houseReservationService.getHouseReservationByUserId(id);
        List<HouseReservationDTO> houseReservationDTOS = new ArrayList<>();

        for(HouseReservation houseReservation: houseReservations){
            HouseReservationDTO dto = new HouseReservationDTO(houseReservation.getId(), houseReservation.getStartDate().toString(), houseReservation.getEndDate().toString(),
                    houseReservation.getMaxGuests(), houseReservation.getPrice(), houseReservation.isAvailable());
            dto.setCancelled(houseReservation.getCancelled());
            houseReservationDTOS.add(dto);
        }

        return new ResponseEntity<>(houseReservationDTOS, HttpStatus.OK);
    }

    @GetMapping("/getHouseReservationsByGuestId/{id}")
    public ResponseEntity<List<HouseReservationDTO>> getHouseReservationsByGuestId(@PathVariable("id") Long id) {
        List<HouseReservation> houseReservations = this.houseReservationService.getHouseReservationsByGuestId(id);
        List<HouseReservationDTO> houseReservationDTOS = new ArrayList<>();

        for (HouseReservation h: houseReservations) {
            String startDate = (String.valueOf(h.getStartDate().getTime()));
            String endDate = (String.valueOf(h.getEndDate().getTime()));

            Long startDateMilis = h.getStartDate().getTime();
            Long endDateMilis = h.getEndDate().getTime();

            HouseReservationDTO dto = new HouseReservationDTO(h.getHouse().getId(), h.getId(), startDate, endDate, h.getMaxGuests(),
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

            dto.setTotalPrice(this.houseReservationService.findTotalPriceForHouseReservation(h));
            dto.setEntityName(h.getHouse().getName());
            this.houseReservationService.canBeCancelled(dto,h);
            dto.setCancelled(h.getCancelled());

            Set<AdditionalServicesDTO> additionalServicesDTOS = new HashSet<>();
            for(AdditionalServices add : this.additionalServicesService.getAllByHouseReservationId(h.getId())) {
                AdditionalServicesDTO newAddSer = new AdditionalServicesDTO(add.getId(), add.getName(), add.getPrice());
                additionalServicesDTOS.add(newAddSer);
            }
            dto.setAdditionalServices(additionalServicesDTOS);

            houseReservationDTOS.add(dto);
        }
        return new ResponseEntity<>(houseReservationDTOS, HttpStatus.OK);
    }


    @PutMapping("/editReservation/{id}")
    public ResponseEntity<HouseReservationDTO> editHouseReservation(@PathVariable("id") Long id, @RequestBody HouseReservationDTO houseReservationDTO) {
        HouseReservationDTO houseReservationEdit = this.houseReservationService.editHouseReservation(houseReservationDTO, id);
        return new ResponseEntity<>(houseReservationEdit, HttpStatus.OK);
    }

    @GetMapping("/getCompanyProfit/{startDate}/{endDate}")
    public ResponseEntity<Double> getCompanyInfo(@PathVariable("startDate") String startDate, @PathVariable("endDate") String endDate){
    	List<HouseReservation> houseReservations = this.houseReservationService.findAll();
		CompanyDTO company = this.companyService.getCompanyInfo((long) 1);
		double profit = 0;
        for (HouseReservation a : houseReservations) { 
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
	            double ownerBenefit = a.getHouse().getOwner().getCategory().getDiscountPercentage() * companyProfit * 0.01;
	            profit += companyProfit - clientBenefit - ownerBenefit;
            }
        }
        
        return new ResponseEntity<>(profit, HttpStatus.OK);
    }



}