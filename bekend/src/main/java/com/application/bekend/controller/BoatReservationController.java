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

    @Autowired
    public BoatReservationController(BoatReservationService boatReservationService, AdditionalServicesService additionalServicesService, BoatService boatService, MyUserService myUserService, HouseReservationService houseReservationService, UserCategoryService userCategoryService, CompanyService companyService) {
        this.boatReservationService = boatReservationService;
        this.additionalServicesService = additionalServicesService;
        this.boatService = boatService;
        this.myUserService = myUserService;
        this.houseReservationService = houseReservationService;
        this.userCategoryService = userCategoryService;
        this.companyService = companyService;
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
        Boat boat = this.boatService.getBoatById(dto.getBoatId());
        MyUser owner = this.myUserService.findUserByBoatId(dto.getBoatId());
        List<BoatReservation> boatReservations = this.boatReservationService.getAllByBoat_Id(boat.getId());
        for (BoatReservation h: boatReservations) {
            Long start =  h.getStartDate().getTime();
            Long end = h.getEndDate().getTime();

            if (Long.parseLong(dto.getStartDate()) >= start && Long.parseLong(dto.getEndDate()) <=  end ||
                    Long.parseLong(dto.getStartDate()) <= start && Long.parseLong(dto.getEndDate()) >= start  ||
                    Long.parseLong(dto.getStartDate()) >= start && Long.parseLong(dto.getStartDate()) <= end  )
            {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        }

        // provera da li vec postoji termin za klijenta u izabranom periodu
        List<HouseReservation> houseReservationsClient = this.houseReservationService.getHouseReservationsByGuestId(dto.getGuestId());
        List<BoatReservation> boatReservationsClient = this.boatReservationService.getBoatReservationsByGuestId(dto.getGuestId());

        for (HouseReservation h: houseReservationsClient) {
            Long start =  h.getStartDate().getTime();
            Long end = h.getEndDate().getTime();

            if (Long.parseLong(dto.getStartDate()) >= start && Long.parseLong(dto.getEndDate()) <=  end ||
                    Long.parseLong(dto.getStartDate()) <= start && Long.parseLong(dto.getEndDate()) >= start  ||
                    Long.parseLong(dto.getStartDate()) >= start && Long.parseLong(dto.getStartDate()) <= end  )
            {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        }

        for (BoatReservation h: boatReservationsClient) {
            Long start =  h.getStartDate().getTime();
            Long end = h.getEndDate().getTime();

            if (Long.parseLong(dto.getStartDate()) >= start && Long.parseLong(dto.getEndDate()) <=  end ||
                    Long.parseLong(dto.getStartDate()) <= start && Long.parseLong(dto.getEndDate()) >= start  ||
                    Long.parseLong(dto.getStartDate()) >= start && Long.parseLong(dto.getStartDate()) <= end  )
            {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        }
        // kraj provere za klijenta

        Date startDate = new Date(Long.parseLong(dto.getStartDate()));
        Date endDate = new Date(Long.parseLong(dto.getEndDate()));
        BoatReservation boatReservation = new BoatReservation(dto.getId(), startDate, endDate, dto.getMaxGuests(), dto.getPrice(), dto.isAvailable(), boat);
        boatReservation.setAvailabilityPeriod(dto.isAvailabilityPeriod());
        boatReservation.setAction(dto.isAction());

        boatReservation = this.boatReservationService.save(boatReservation); // sacuvali smo rezervaciju i povratna vrednost metode je tacno ta rezervacija iz baze (sa ispravno generisanim id-em ...)
        // ovaj korak je obavezan jer se rezervacija koju dodajemo ovde (***) mora nalaziti u bazi

        owner.setPoints(owner.getPoints() + this.companyService.getCompanyInfo((long) 1).getPointsPerReservationOwner());
        this.checkUserCategory(owner);
        
        Set<AdditionalServices> additionalServicesSet = new HashSet<>();
        for(AdditionalServicesDTO add : dto.getAdditionalServices()){

            // iz baze dobavljamo (original) dodatnu uslugu i u njen set rezervacija, dodajemo ovu konkretnu rezervaciju (houseReservation)
            AdditionalServices additionalServices = this.additionalServicesService.getAdditionalServicesById(add.getId());

            additionalServices.addBoatReservation(boatReservation); // (***)
            // da je bio slucaj da smo dodali samo inicijalno kreiran houseReservation (nastao iz podataka od DTO), bio bi error: javax.persistence.EntityNotFoundException
            // jer u tabeli koja spaja AdditionalServices (id_a) i HouseReservation (id_h), id_h bi bio null i to vraca gresku, jer se u tabeli mora nalaziti neki vec postojeci id_h (radimo spajanje podataka dve postojece table, nema novih podataka)

            additionalServicesSet.add(additionalServices);   // u set koji cemo kasnije dodeliti rezervaciji dodajemo dodatnu uslugu

            // azuriramo (sacuvamo) izmenjenu dodatnu uslugu u bazi (additional service)
            this.additionalServicesService.save(additionalServices);
        }

        // dodajem rezervaciju vikendice u samu vikendicu
        boat.addBoatReservation(boatReservation);
        this.boatService.save(boat);

        if (dto.getGuestId() != null && dto.getGuestId() != 0 && dto.isAvailable() == false) {
            MyUser guest = this.myUserService.findUserById(dto.getGuestId());
            boatReservation.setGuest(guest);
            this.boatService.save(boat);

            Set<BoatReservation> boatReservations1 = guest.getBoatReservations();
            boatReservations1.add(boatReservation);
            guest.setBoatReservations(boatReservations1);
            guest.setPoints(guest.getPoints() + this.companyService.getCompanyInfo((long) 1).getPointsPerReservationClient());
            this.checkUserCategory(guest);
            this.myUserService.save(guest);

            // TODO: ako je vlasnik zakazao za klijenta, poslati mejl klijentu
            this.myUserService.sendMailToClient(null, dto, null, "", boat.getName(), "");
        }

        // TODO: ako je akcije, poslati mejl svim pretplacenim klijentima
        if (dto.isAction() == true && dto.isAvailable() == true){
            this.myUserService.sendSubscribedUsersEmail(null, dto, null, "", boat.getName(), "");
        }

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    @Transactional
    public ResponseEntity<Boolean> delete(@PathVariable("id") Long id) {
        BoatReservation boatReservation = this.boatReservationService.getBoatReservationById(id);   // dobavimo rezervaciju iz baze

        Set<AdditionalServices> additionalServices =  boatReservation.getAdditionalServices();     // ne moramo direktno iz baze dobavljati jer ova lista u sebi ima objekte sa svojim pravim id-jevima
        for(AdditionalServices a: additionalServices){
            a.getBoatReservationsServices().remove(boatReservation);  // iz niza rezervacija dodatnih usluga izbacimo ovu rezervaciju koju brisemo - raskinuta u tabeli additional_services_house_reservation (sa vodece strane, jer je kod AdditionalService JoinTable)
            this.additionalServicesService.save(a);
        }

        boatReservation.setGuest(null); // TODO: proveriit
        boatReservation.setBoat(null);  // raskinuta veza u tabeli house_reservation_table (sa strane vodece veze u ManyToMany vezi)
        boatReservation = this.boatReservationService.save(boatReservation);

        this.boatReservationService.delete(boatReservation.getId());  // brisanje rezervacije iz house_reservation tabele

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
            if (a.getGuest() != null) {
                boatReservationDTO.setGuestId(a.getGuest().getId());
            }

            Set<AdditionalServicesDTO> additionalServicesDTOS = new HashSet<>();
            // dobavljamo set dodatnih usluga za onu konkretnu rezervaciju iz baze i pretvaramo u DTO (a mozemo samo i pristupiti setu dodatnih usluga direktno preko rezervacije (a.getAdditionalServices()))
            for(AdditionalServices add : this.additionalServicesService.getAllByHouseReservationId(a.getId())){  // a.getAdditionalServices()
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
    
    private void checkUserCategory(MyUser user) {
    	List<UserCategory> allCategories = this.userCategoryService.findAll();
    	int min = 0;
    	UserCategory cat = new UserCategory();
    	for(UserCategory category: allCategories) {
    		if(category.getPoints() > min && user.getPoints() > category.getPoints()) {
    			min = category.getPoints();
    			cat = category;
    		}
    	}
    	user.setCategory(cat);
    }


}
