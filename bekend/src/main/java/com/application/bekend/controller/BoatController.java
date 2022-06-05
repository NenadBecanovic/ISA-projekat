package com.application.bekend.controller;

import com.application.bekend.DTO.*;
import com.application.bekend.model.Boat;
import com.application.bekend.model.Image;
import com.application.bekend.service.*;
import org.modelmapper.ModelMapper;
import com.application.bekend.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.*;

@RestController
@RequestMapping("api/boat")
@EnableTransactionManagement
public class BoatController {

    private final BoatService boatService;
    private final AdditionalServicesService additionalServicesService;
    private final NavigationEquipmentService navigationEquipmentService;
    private final AddresService addresService;
    private final ImageService imageService;
    private final BoatReservationService boatReservationService;
    private final MyUserService myUserService;
    private final ReportService reportService;

    @Autowired
    public BoatController(BoatService boatService, AdditionalServicesService additionalServicesService, NavigationEquipmentService navigationEquipmentService, AddresService addresService, ImageService imageService, BoatReservationService boatReservationService, MyUserService myUserService, ReportService reportService) {
        this.boatService = boatService;
        this.additionalServicesService = additionalServicesService;
        this.navigationEquipmentService = navigationEquipmentService;
        this.addresService = addresService;
        this.imageService = imageService;
        this.boatReservationService = boatReservationService;
        this.myUserService = myUserService;
        this.reportService = reportService;
    }
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/getBoatById/{id}")
    public ResponseEntity<BoatDTO> getBoatById(@PathVariable("id") Long id){
        Boat boat = this.boatService.getBoatById(id);

        AddressDTO addressDTO = new AddressDTO(boat.getAddress().getId(), boat.getAddress().getStreet(), boat.getAddress().getCity(),
                boat.getAddress().getState(), boat.getAddress().getLongitude(), boat.getAddress().getLatitude(), boat.getAddress().getPostalCode());

        NavigationEquipment navigationEquipment = this.navigationEquipmentService.getNavigationEquipmentByBoatId(id);
        NavigationEquipmentDTO navigationEquipmentDTO = new NavigationEquipmentDTO();


        if(navigationEquipment != null){
            navigationEquipmentDTO = new NavigationEquipmentDTO(navigationEquipment.getId(), navigationEquipment.isFishFinder(),
                    navigationEquipment.isRadar(), navigationEquipment.isVhfradio(), navigationEquipment.isGps());
        }

        BoatDTO dto = new BoatDTO(boat.getId(), boat.getName(), boat.getType(), boat.getLength(), boat.getEngineNumber(), boat.getEnginePower(), boat.getMaxSpeed(),
                boat.getPromoDescription(), boat.getCapacity(), boat.getBehaviourRules(), boat.getFishingEquipment(), boat.getPricePerDay(), boat.isCancalletionFree(),
                boat.getCancalletionFee(), addressDTO, navigationEquipmentDTO);

        if(boat.getNumberOfReviews() == 0){
            dto.setAvarageGrade(0);
        }else{
            dto.setAvarageGrade(boat.getGrade() / boat.getNumberOfReviews());
        }

        dto.setOwnerId(boat.getOwner().getId());
        dto.setGrade(boat.getGrade());
        dto.setNumberOfReviews(boat.getNumberOfReviews());

        Set<ImageDTO> dtoSet = new HashSet<>();
        for(Image i: boat.getImages()){
            ImageDTO postDto = modelMapper.map(i, ImageDTO.class);
            dtoSet.add(postDto);
        }
        dto.setImages(dtoSet);

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PutMapping("/edit/{id}")
    @PreAuthorize("hasRole('ROLE_BOAT_OWNER')")
    public ResponseEntity<BoatDTO> edit(@RequestBody BoatDTO dto) {
        Boat boat = this.boatService.getBoatById(dto.getId());

        // ako postoji rezervacija u brodu, ona se ne moze izmeniti
        for (BoatReservation h: boat.getCourses()) {
            Long endDate = h.getEndDate().getTime();
            Calendar date = Calendar.getInstance();
            long millisecondsDate = date.getTimeInMillis();

            if (h.isAvailable() == false && h.isAvailabilityPeriod() == false && endDate >= millisecondsDate) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }

        Address address = boat.getAddress();
        List<AdditionalServices> additionalServices = this.additionalServicesService.getAllByBoatId(dto.getId());

        for (AdditionalServices a: additionalServices)
        {
            for (AdditionalServicesDTO additionalServicesDTO: dto.getServices())
            {
                if(a.getId() == additionalServicesDTO.getId())
                {
                    a.setName(additionalServicesDTO.getName());
                    a.setPrice(additionalServicesDTO.getPrice());
                    this.additionalServicesService.save(a);
                }
            }
        }

        Address address1 = this.addresService.getAddressById(dto.getAddress().getId());
        address1.setStreet(dto.getAddress().getStreet());
        address1.setCity(dto.getAddress().getCity());
        address1.setState(dto.getAddress().getState());
        address1.setLongitude(dto.getAddress().getLongitude());
        address1.setLatitude(dto.getAddress().getLatitude());
        address1.setPostalCode(dto.getAddress().getPostalCode());
        this.addresService.save(address1);

        boat.setAddress(address1);
        boat.setName(dto.getName());
        boat.setPromoDescription(dto.getPromoDescription());
        boat.setCapacity(dto.getCapacity());
        boat.setBehaviourRules(dto.getBehaviourRules());
        boat.setPricePerDay(dto.getPricePerDay());
        boat.setCancalletionFee(dto.getCancalletionFee());
        boat.setCancalletionFree(dto.isCancalletionFree());

        boat.setType(dto.getType());
        boat.setLength(dto.getLength());
        boat.setEngineNumber(dto.getEngineNumber());
        boat.setEnginePower(dto.getEnginePower());
        boat.setMaxSpeed(dto.getMaxSpeed());
        boat.setFishingEquipment(dto.getFishingEquipment());

        NavigationEquipment navigationEquipment = new NavigationEquipment(dto.getNavigationEquipmentDTO().getId(),
                dto.getNavigationEquipmentDTO().isGps(), dto.getNavigationEquipmentDTO().isRadar(), dto.getNavigationEquipmentDTO().isVhfradio(),
                dto.getNavigationEquipmentDTO().isFishFinder());
        boat.setNavigationEquipment(navigationEquipment);
        navigationEquipment.setBoat(boat);

        this.navigationEquipmentService.save(navigationEquipment);
        this.boatService.save(boat);
      
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<BoatDTO>> findAll(){
        List<Boat> boats = this.boatService.findAll();
        List<BoatDTO> boatDTOS = new ArrayList<>();

        for(Boat boat: boats){
            BoatDTO dto = modelMapper.map(boat, BoatDTO.class);
            if(boat.getNumberOfReviews() == 0){
                dto.setAvarageGrade(0);
            }else{
                dto.setAvarageGrade(boat.getGrade() / boat.getNumberOfReviews());
            }
            boatDTOS.add(dto);
        }
        return new ResponseEntity<>(boatDTOS, HttpStatus.OK);
    }

    @GetMapping("/findAllOrderByGrade")
    public ResponseEntity<List<BoatDTO>> findAllOrderByGrade(){
        List<Boat> boats = this.boatService.findAllOrderByGrade();
        List<BoatDTO> boatDTOS = new ArrayList<>();
        for(Boat boat: boats){
            addBoatDto(boatDTOS, boat);
        }
        return new ResponseEntity<>(boatDTOS, HttpStatus.OK);
    }

    private void addBoatDto(List<BoatDTO> boatDTOS, Boat boat) {
        BoatDTO dto = modelMapper.map(boat, BoatDTO.class);
        if(boat.getNumberOfReviews() == 0){
            dto.setAvarageGrade(0);
        }else{
            dto.setAvarageGrade(boat.getGrade() / boat.getNumberOfReviews());
        }
        boatDTOS.add(dto);
    }

    @GetMapping("/findBoatsForHomePage")
    public ResponseEntity<List<HomeBoatSlideDTO>> findBoatsForHomePage(){
        List<Boat> boats = this.boatService.findAllOrderByGrade();
        List<BoatDTO> boatDTOS = new ArrayList<>();
        for(Boat boat: boats){
            addBoatDto(boatDTOS, boat);
        }
        List<HomeBoatSlideDTO> homeBoatSlideDTOS = getHomeBoatSlideDTOS(boatDTOS);
        return new ResponseEntity<>(homeBoatSlideDTOS, HttpStatus.OK);
    }

    private List<HomeBoatSlideDTO> getHomeBoatSlideDTOS(List<BoatDTO> boatDTOS) {
        List<HomeBoatSlideDTO> homeBoatSlideDTOS = new ArrayList<>();
        List<BoatDTO> boatDTOS1 = new ArrayList<>();
        int i = 1;
        for (BoatDTO dto : boatDTOS) {
            boatDTOS1.add(dto);
            if (i % 4 == 0) {
                HomeBoatSlideDTO homeBoatSlideDTO = new HomeBoatSlideDTO(boatDTOS1);
                homeBoatSlideDTOS.add(homeBoatSlideDTO);
                boatDTOS1 = new ArrayList<>();
            }
            i = i + 1;
        }

        if (boatDTOS1.size() != 0) {
            HomeBoatSlideDTO homeBoatSlideDTO = new HomeBoatSlideDTO(boatDTOS1);
            homeBoatSlideDTOS.add(homeBoatSlideDTO);
        }
        return homeBoatSlideDTOS;
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_BOAT_OWNER')")
    @Transactional
    public ResponseEntity<Boat> add(@RequestBody BoatDTO dto) {
        Address address = new Address(dto.getAddress().getId(), dto.getAddress().getStreet(), dto.getAddress().getCity(), dto.getAddress().getState(),
                dto.getAddress().getLongitude(), dto.getAddress().getLatitude(), dto.getAddress().getPostalCode());
        address = this.addresService.save(address);

        Set<Image> dtoSet = new HashSet<>();
        for (ImageDTO i : dto.getImages()) {
            Image image = modelMapper.map(i, Image.class);
            dtoSet.add(image);
        }

        NavigationEquipment navigationEquipment = new NavigationEquipment(dto.getNavigationEquipmentDTO().getId(), dto.getNavigationEquipmentDTO().isGps(),
                dto.getNavigationEquipmentDTO().isRadar(), dto.getNavigationEquipmentDTO().isVhfradio(), dto.getNavigationEquipmentDTO().isFishFinder());
        navigationEquipment = this.navigationEquipmentService.save(navigationEquipment);

        Boat boat = new Boat(dto.getId(), dto.getName(), dto.getType(), dto.getLength(), dto.getEngineNumber(), dto.getEnginePower(), dto.getMaxSpeed(),
                navigationEquipment, address, dto.getPromoDescription(), dto.getCapacity(), dto.getGrade(), new HashSet<>(), dto.getBehaviourRules(),
                dto.getFishingEquipment(), dto.getPricePerDay(), new HashSet<>(), dto.isCancalletionFree(), dto.getCancalletionFee(), dtoSet);

        MyUser owner = this.myUserService.findUserById(dto.getOwnerId());
        boat.setOwner(owner);
        boat = this.boatService.save(boat);  // sacuvani brod iz baze (sa odgovarajucim id-jem)

        // dodatna usluga kapetana
        AdditionalServices captainService = new AdditionalServices("prisustvo kapetana", dto.getPricePerDay()/2, new HashSet<>());
        Set<Boat> additionalServicesBoats = captainService.getBoats();
        additionalServicesBoats.add(boat);
        captainService.setBoats(additionalServicesBoats);
        captainService = this.additionalServicesService.save(captainService);

        Set<AdditionalServices> boatServices = boat.getServices();
        boatServices.add(captainService);
        boat.setServices(boatServices);
        this.boatService.save(boat);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    @Transactional
    public ResponseEntity<Boolean> delete(@PathVariable("id") Long id) {
        Boat boat = this.boatService.getBoatById(id);

        // ako postoji rezervacija u brodu, ona se ne moze izmeniti
        for (BoatReservation h: boat.getCourses()) {
            Long endDate = h.getEndDate().getTime();
            Calendar date = Calendar.getInstance();
            long millisecondsDate = date.getTimeInMillis();

            if (h.isAvailable() == false && h.isAvailabilityPeriod() == false && endDate >= millisecondsDate) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }

        boat.setAddress(null);
        boat.setOwner(null);
        this.boatService.save(boat);
        for (Image i: boat.getImages()) {
            Image image = this.imageService.getImageById(i.getId());
            image.setBoat(null);
            this.imageService.delete(image.getId());
        }
        boat.setImages(null);
        this.boatService.save(boat);

        // rezervacije
        for (BoatReservation h: boat.getCourses()) {
            BoatReservation boatReservation = this.boatReservationService.getBoatReservationById(h.getId());

            Report report = this.reportService.getReportByBoatReservationId(h.getId());
            if(report != null) {
                this.reportService.delete(report.getId());
            }

            Set<AdditionalServices> additionalServices =  boatReservation.getAdditionalServices();
            for(AdditionalServices a: additionalServices){
                a.getBoatReservationsServices().remove(boatReservation);
                this.additionalServicesService.save(a);
            }

            boatReservation.setGuest(null);
            boatReservation.setBoat(null);
            boatReservation = this.boatReservationService.save(boatReservation);

            this.boatReservationService.delete(boatReservation.getId());
        }

        boat.setCourses(null);
        this.boatService.save(boat);

        // dodatne usluge
        for (AdditionalServices a: boat.getServices()) {
            AdditionalServices additionalServices = this.additionalServicesService.getAdditionalServicesById(a.getId());
            additionalServices.setHouses(null);
            additionalServices.setHouseReservationsServices(null);
            additionalServices.setBoats(null);
            additionalServices.setBoatReservationsServices(null);

            this.additionalServicesService.deleteById(a.getId());
        }
        boat.setServices(null);
        boat = this.boatService.save(boat);

        NavigationEquipment navigationEquipment = this.navigationEquipmentService.getNavigationEquipmentByBoatId(id);
        navigationEquipment.setBoat(null);
        this.navigationEquipmentService.delete(navigationEquipment.getId());

        this.boatService.delete(boat.getId());

        return new ResponseEntity<>(true, HttpStatus.OK);
    }


    @PostMapping("/findAllAvailableBoats")
    public ResponseEntity<List<BoatDTO>> findAllAvailableBoats(@RequestBody ReservationCheckDTO reservationCheckDTO){
        List<Boat> boats = this.boatService.getAllAvailableHouses(reservationCheckDTO);
        List<BoatDTO> boatDTOS = new ArrayList<>();
        for(Boat boat: boats){
            makeBoatDTOList(boat,boatDTOS);
        }
        return new ResponseEntity<>(boatDTOS, HttpStatus.OK);
    }

    private void makeBoatDTOList(Boat boat, List<BoatDTO> boatDTOS) {
        AddressDTO addressDTO = new AddressDTO(boat.getAddress().getId(), boat.getAddress().getStreet(), boat.getAddress().getCity(), boat.getAddress().getState(),
                boat.getAddress().getLongitude(), boat.getAddress().getLatitude(), boat.getAddress().getPostalCode());

        BoatDTO dto = modelMapper.map(boat, BoatDTO.class);
        dto.setAddress(addressDTO);
        dto.setGrade(boat.getGrade());
        Set<ImageDTO> dtoSet = new HashSet<>();
        for(Image i: boat.getImages()){
            ImageDTO imageDTO = modelMapper.map(i, ImageDTO.class);
            dtoSet.add(imageDTO);
        }
        if(boat.getNumberOfReviews() == 0){
            dto.setAvarageGrade(0);
        }else{
            dto.setAvarageGrade(boat.getGrade() / boat.getNumberOfReviews());
        }

        dto.setImages(dtoSet);
        boatDTOS.add(dto);
    }

    @DeleteMapping("/deleteAllBoatsByOwner/{id}")
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    @Transactional
    public ResponseEntity<Boolean> deleteAllBoatsByOwner(@PathVariable("id") Long id) {
        List<Boat> allBoats = this.boatService.getAllByOwnerId(id);

        for(Boat boat : allBoats) {

            // ako postoji rezervacija u brodu, ona se ne moze izmeniti
            for (BoatReservation h: boat.getCourses()) {
                Long endDate = h.getEndDate().getTime();
                Calendar date = Calendar.getInstance();
                long millisecondsDate = date.getTimeInMillis();

                if (h.isAvailable() == false && h.isAvailabilityPeriod() == false && endDate >= millisecondsDate) {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
            }

            boat.setAddress(null);
            boat.setOwner(null);
            this.boatService.save(boat);
            for (Image i: boat.getImages()) {
                Image image = this.imageService.getImageById(i.getId());
                image.setBoat(null);
                this.imageService.delete(image.getId());
            }
            boat.setImages(null);
            this.boatService.save(boat);

            // rezervacije
            for (BoatReservation h: boat.getCourses()) {
                BoatReservation boatReservation = this.boatReservationService.getBoatReservationById(h.getId());

                Report report = this.reportService.getReportByBoatReservationId(h.getId());
                if(report != null) {
                    this.reportService.delete(report.getId());
                }

                Set<AdditionalServices> additionalServices =  boatReservation.getAdditionalServices();
                for(AdditionalServices a: additionalServices){
                    a.getBoatReservationsServices().remove(boatReservation);
                    this.additionalServicesService.save(a);
                }

                boatReservation.setGuest(null);
                boatReservation.setBoat(null);
                boatReservation = this.boatReservationService.save(boatReservation);

                this.boatReservationService.delete(boatReservation.getId());
            }

            boat.setCourses(null);
            this.boatService.save(boat);

            // dodatne usluge
            for (AdditionalServices a: boat.getServices()) {
                AdditionalServices additionalServices = this.additionalServicesService.getAdditionalServicesById(a.getId());
                additionalServices.setHouses(null);
                additionalServices.setHouseReservationsServices(null);
                additionalServices.setBoats(null);
                additionalServices.setBoatReservationsServices(null);

                this.additionalServicesService.deleteById(a.getId());
            }
            boat.setServices(null);
            boat = this.boatService.save(boat);

            NavigationEquipment navigationEquipment = this.navigationEquipmentService.getNavigationEquipmentByBoatId(id);
            if(navigationEquipment != null) {
            	navigationEquipment.setBoat(null);
                this.navigationEquipmentService.delete(navigationEquipment.getId());
            }
           
            this.boatService.delete(boat.getId());
        }
        
        return new ResponseEntity<>(true, HttpStatus.OK);
    }
}
