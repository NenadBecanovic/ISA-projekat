package com.application.bekend.controller;

import com.application.bekend.DTO.*;
import com.application.bekend.model.*;
import com.application.bekend.service.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    public HouseReservationController(HouseReservationService houseReservationService, HouseService houseService, AdditionalServicesService additionalServicesService, MyUserService myUserService, ModelMapper modelMapper, BoatReservationService boatReservationService) {
        this.houseReservationService = houseReservationService;
        this.houseService = houseService;
        this.additionalServicesService = additionalServicesService;
        this.myUserService = myUserService;
        this.modelMapper = modelMapper;
        this.boatReservationService = boatReservationService;
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
    @Transactional
    public ResponseEntity<HouseReservation> save(@RequestBody HouseReservationDTO dto) {
        House house = this.houseService.getHouseById(dto.getHouseId());

        // TODO: svu logiku prebaciti u servis
        // provera da li vec postoji termin u vikednici u izabranom periodu
        List<HouseReservation> houseReservations = this.houseReservationService.getAllByHouse_Id(house.getId());
        for (HouseReservation h: houseReservations) {
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
        HouseReservation houseReservation = new HouseReservation(dto.getId(), startDate, endDate, dto.getMaxGuests(), dto.getPrice(), dto.isAvailable(), house);
        houseReservation.setAvailabilityPeriod(dto.isAvailabilityPeriod());
        houseReservation.setAction(dto.isAction());

        houseReservation = this.houseReservationService.save(houseReservation); // sacuvali smo rezervaciju i povratna vrednost metode je tacno ta rezervacija iz baze (sa ispravno generisanim id-em ...)
        // ovaj korak je obavezan jer se rezervacija koju dodajemo ovde (***) mora nalaziti u bazi

        Set<AdditionalServices> additionalServicesSet = new HashSet<>();
        for(AdditionalServicesDTO add : dto.getAdditionalServices()){

            // iz baze dobavljamo (original) dodatnu uslugu i u njen set rezervacija, dodajemo ovu konkretnu rezervaciju (houseReservation)
            AdditionalServices additionalServices = this.additionalServicesService.getAdditionalServicesById(add.getId());

            additionalServices.addHouseReservation(houseReservation); // (***)
            // da je bio slucaj da smo dodali samo inicijalno kreiran houseReservation (nastao iz podataka od DTO), bio bi error: javax.persistence.EntityNotFoundException
            // jer u tabeli koja spaja AdditionalServices (id_a) i HouseReservation (id_h), id_h bi bio null i to vraca gresku, jer se u tabeli mora nalaziti neki vec postojeci id_h (radimo spajanje podataka dve postojece table, nema novih podataka)

            additionalServicesSet.add(additionalServices);   // u set koji cemo kasnije dodeliti rezervaciji dodajemo dodatnu uslugu

            // azuriramo (sacuvamo) izmenjenu dodatnu uslugu u bazi (additional service)
            this.additionalServicesService.save(additionalServices);
        }

        // dodajem rezervaciju vikendice u samu vikendicu
        house.addHouseReservation(houseReservation);
        this.houseService.save(house);

        if (dto.getGuestId() != null && dto.getGuestId() != 0) {
            MyUser guest = this.myUserService.findUserById(dto.getGuestId());
            houseReservation.setGuest(guest);
            this.houseService.save(house);

            Set<HouseReservation> houseReservations1 = guest.getHouseReservations();
            //if (guest.)  // greska   // TODO : treba da bude transactional metoda ????
            houseReservations1.add(houseReservation);
            guest.setHouseReservations(houseReservations1);
            this.myUserService.save(guest);
        }

        // TODO: ako je vlasnik zakazao za klijenta, poslati mejl klijentu

        // TODO: ako je akcije, poslati mejl svim pretplacenim klijentima

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // kod brisanja:
    // mora biti Transactional metoda + EnableTransactionManagement klasa
    @DeleteMapping("/delete/{id}")
    @Transactional
    public ResponseEntity<Boolean> delete(@PathVariable("id") Long id) {
        HouseReservation houseReservation = this.houseReservationService.getHouseReservationById(id);   // dobavimo rezervaciju iz baze

        Set<AdditionalServices> additionalServices =  houseReservation.getAdditionalServices();     // ne moramo direktno iz baze dobavljati jer ova lista u sebi ima objekte sa svojim pravim id-jevima
        for(AdditionalServices a: additionalServices){
            a.getHouseReservationsServices().remove(houseReservation);  // iz niza rezervacija dodatnih usluga izbacimo ovu rezervaciju koju brisemo - raskinuta u tabeli additional_services_house_reservation (sa vodece strane, jer je kod AdditionalService JoinTable)
            this.additionalServicesService.save(a);
        }

        houseReservation.setGuest(null);    // TODO: proveriti kad se dodaju gosti sa rezervacijama
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







}