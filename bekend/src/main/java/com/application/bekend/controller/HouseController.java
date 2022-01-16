package com.application.bekend.controller;

import com.application.bekend.DTO.*;
import org.modelmapper.ModelMapper;
import com.application.bekend.model.*;
import com.application.bekend.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.*;
import javax.transaction.Transactional;
import java.util.*;

@RestController
@RequestMapping("api/house")
@EnableTransactionManagement
public class HouseController {

    private final HouseService houseService;
    @Autowired
    private ModelMapper modelMapper;
    private final RoomService roomService;
    private final AdditionalServicesService additionalServicesService;
    private  final HouseReservationService houseReservationService;
    private final ImageService imageService;
    private final AddresService addresService;
    private final MyUserService myUserService;
    private final ReportService reportService;
  
    @Autowired
    public HouseController(HouseService houseService, RoomService roomService, AdditionalServicesService additionalServicesService, HouseReservationService houseReservationService, ImageService imageService, AddresService addresService, MyUserService myUserService, ReportService reportService) {
        this.houseService = houseService;
        this.roomService = roomService;
        this.additionalServicesService = additionalServicesService;
        this.houseReservationService = houseReservationService;
        this.imageService = imageService;
        this.addresService = addresService;
        this.myUserService = myUserService;
        this.reportService = reportService;
    }

    @GetMapping("/getHouseById/{id}")
    public ResponseEntity<HouseDTO> getHouseById(@PathVariable("id") Long id) {
        House house = this.houseService.getHouseById(id);
        AddressDTO addressDTO = new AddressDTO(house.getAddress().getId(), house.getAddress().getStreet(), house.getAddress().getCity(), house.getAddress().getState(),
                house.getAddress().getLongitude(), house.getAddress().getLatitude(), house.getAddress().getPostalCode());

        HouseDTO dto = new HouseDTO(house.getId(), house.getName(), addressDTO, house.getPromoDescription(), house.getBehaviourRules(),
                house.getPricePerDay(), house.isCancalletionFree(), house.getCancalletionFee());
        dto.setOwnerId(house.getOwner().getId());

        // TODO: ispraviti
//        Set<ImageDTO> dtoSet = new HashSet<>();
//        for(Image i: house.getImages()){
//            ImageDTO imageDTO = modelMapper.map(i, ImageDTO.class);
//            dtoSet.add(imageDTO);
//        }
//        dto.setImages(dtoSet);


        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<HouseDTO>> findAll(){
        List<House> houses = this.houseService.findAll();
        List<HouseDTO> houseDTOS = new ArrayList<>();
        for(House house: houses){
            makeHouseDTOList(house, houseDTOS);
        }
        return new ResponseEntity<>(houseDTOS, HttpStatus.OK);
    }

    @GetMapping("/findHouseForHomePage")
    public ResponseEntity<List<HomeHouseSlideDTO>> findHouseForHomePage(){
        List<House> houses = this.houseService.findAllOrderByGrade();
        List<HouseDTO> houseDTOS = new ArrayList<>();
        for(House house: houses){
            addHouseDto(houseDTOS, house);
        }

        List<HomeHouseSlideDTO> homeHouseSlideDTOS = getHomeHouseSlideDTOS(houseDTOS);

        return new ResponseEntity<>(homeHouseSlideDTOS, HttpStatus.OK);
    }

    private void addHouseDto(List<HouseDTO> houseDTOS, House house) {
        HouseDTO dto = modelMapper.map(house, HouseDTO.class);
        houseDTOS.add(dto);
    }

    private List<HomeHouseSlideDTO> getHomeHouseSlideDTOS(List<HouseDTO> houseDTOS) {
        List<HomeHouseSlideDTO> homeHouseSlideDTOS = new ArrayList<>();
        List<HouseDTO> houseDTOS1 = new ArrayList<>();
        int i = 1;
        for (HouseDTO dto : houseDTOS) {
            houseDTOS1.add(dto);
            if (i % 4 == 0) {
                HomeHouseSlideDTO homeBoatSlideDTO = new HomeHouseSlideDTO(houseDTOS1);
                homeHouseSlideDTOS.add(homeBoatSlideDTO);
                houseDTOS1 = new ArrayList<>();
            }
            i = i + 1;
        }

        if (houseDTOS1.size() != 0) {
            HomeHouseSlideDTO homeBoatSlideDTO = new HomeHouseSlideDTO(houseDTOS1);
            homeHouseSlideDTOS.add(homeBoatSlideDTO);
        }
        return homeHouseSlideDTOS;
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<HouseDTO> edit(@RequestBody HouseDTO dto) {
        House house = this.houseService.getHouseById(dto.getId());

        // ako postoji rezervacija u vikendici, ona se ne moze izmeniti
        for (HouseReservation h: house.getCourses()) {
            Long endDate = h.getEndDate().getTime();
            Calendar date = Calendar.getInstance();
            long millisecondsDate = date.getTimeInMillis();

            if (h.isAvailable() == false && h.isAvailabilityPeriod() == false && endDate >= millisecondsDate) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }

        Address address = house.getAddress();
        Set<Room> rooms = house.getRooms();
        List<AdditionalServices> additionalServices = this.additionalServicesService.getAllByHouseId(dto.getId());

        for (Room r : rooms) {
            for (RoomDTO roomDTO : dto.getRooms()) {
                if (r.getId() == roomDTO.getId()) {
                    r.setNumberOfBeds(roomDTO.getNumberOfBeds());
                    this.roomService.save(r);
                }
            }
        }

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
        address1.setId(dto.getAddress().getId());
        address1.setStreet(dto.getAddress().getStreet());
        address1.setCity(dto.getAddress().getCity());
        address1.setState(dto.getAddress().getState());
        address1.setLongitude(dto.getAddress().getLongitude());
        address1.setLatitude(dto.getAddress().getLatitude());
        address1.setPostalCode(dto.getAddress().getPostalCode());
        this.addresService.save(address1);

        house.setAddress(address1);
        house.setName(dto.getName());
        house.setPromoDescription(dto.getPromoDescription());
        house.setBehaviourRules(dto.getBehaviourRules());
        house.setPricePerDay(dto.getPricePerDay());
        house.setCancalletionFee(dto.getCancalletionFee());
        house.setCancalletionFree(dto.isCancalletionFree());

        this.houseService.save(house);

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<HouseDTO>> getAll() {
        List<House> houses = this.houseService.getAll();
        List<HouseDTO> housesDtos = new ArrayList<>();

        for (House h: houses) {
            AddressDTO addressDTO = new AddressDTO(h.getAddress().getId(), h.getAddress().getStreet(), h.getAddress().getCity(), h.getAddress().getState(),
                h.getAddress().getLongitude(), h.getAddress().getLatitude(), h.getAddress().getPostalCode());
            Set<RoomDTO> roomDTOS = new HashSet<>();
            Set<AdditionalServicesDTO> additionalServicesDTOS = new HashSet<>();

            for (Room r : h.getRooms()) {
                RoomDTO roomDTO = new RoomDTO(r.getId(), r.getNumberOfBeds());
                roomDTOS.add(roomDTO);
            }

            for (AdditionalServices a : this.additionalServicesService.getAllByHouseId(h.getId())) {
                AdditionalServicesDTO additionalServicesDTO = new AdditionalServicesDTO(a.getId(), a.getName(), a.getPrice());
                additionalServicesDTOS.add(additionalServicesDTO);
            }

            HouseDTO houseDTO = new HouseDTO(h.getId(), h.getName(), addressDTO, h.getPromoDescription(), h.getBehaviourRules(), h.getPricePerDay(),
                    h.isCancalletionFree(), h.getCancalletionFee(), roomDTOS, additionalServicesDTOS);
            houseDTO.setOwnerId(h.getOwner().getId());
            housesDtos.add(houseDTO);
        }

        return new ResponseEntity<>(housesDtos, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    @Transactional
    public ResponseEntity<Boolean> delete(@PathVariable("id") Long id) {
        House house = this.houseService.getHouseById(id);

        // ako postoji rezervacija u vikendici, ona se ne moze obrisati
        for (HouseReservation h: house.getCourses()) {
            Long endDate = h.getEndDate().getTime();
            Calendar date = Calendar.getInstance();
            long millisecondsDate = date.getTimeInMillis();

            if (h.isAvailable() == false && h.isAvailabilityPeriod() == false && endDate >= millisecondsDate) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }

        house.setAddress(null);
        for (Room r: house.getRooms()) {
            Room room = this.roomService.getRoomById(r.getId());
            room.setHouse(null);
            this.roomService.deleteById(room.getId());
        }

        house.setRooms(null);
        this.houseService.save(house);
        house.setOwner(null);
        this.houseService.save(house);

        for (Image i: house.getImages()) {
            Image image = this.imageService.getImageById(i.getId());
            image.setHouse(null);
            this.imageService.delete(image.getId());
        }

        house.setImages(null);
        this.houseService.save(house);

        // rezervacije
        for (HouseReservation h: house.getCourses()) {
            HouseReservation houseReservation = this.houseReservationService.getHouseReservationById(h.getId());

            Report report = this.reportService.getReportByHouseReservationId(h.getId());
            if(report != null) {
                this.reportService.delete(report.getId());
            }

            Set<AdditionalServices> additionalServices =  houseReservation.getAdditionalServices();
            for(AdditionalServices a: additionalServices){
                a.getHouseReservationsServices().remove(houseReservation);
                this.additionalServicesService.save(a);
            }

            houseReservation.setGuest(null);
            houseReservation.setHouse(null);
            houseReservation = this.houseReservationService.save(houseReservation);

            this.houseReservationService.delete(houseReservation.getId());
        }

        house.setCourses(null);
        this.houseService.save(house);

        // dodatne usluge
        for (AdditionalServices a: house.getServices()) {
            AdditionalServices additionalServices = this.additionalServicesService.getAdditionalServicesById(a.getId());
            additionalServices.setHouses(null);
            additionalServices.setHouseReservationsServices(null);
            additionalServices.setBoats(null);
            additionalServices.setBoatReservationsServices(null);
            this.additionalServicesService.deleteById(a.getId());
        }

        house.setServices(null);
        house = this.houseService.save(house);
        this.houseService.delete(house.getId());

        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<House> add(@RequestBody HouseDTO dto) {
        Address address = new Address(dto.getAddress().getId(), dto.getAddress().getStreet(), dto.getAddress().getCity(), dto.getAddress().getState(),
                dto.getAddress().getLongitude(), dto.getAddress().getLatitude(), dto.getAddress().getPostalCode());
        address = this.addresService.save(address);

        Set<Image> dtoSet = new HashSet<>();
        for(ImageDTO i: dto.getImages()){
            Image image = modelMapper.map(i, Image.class);
            dtoSet.add(image);
        }

        House house = new House(dto.getId(), dto.getName(), dto.getGrade(), new HashSet<>(), address, dto.getPromoDescription(), new HashSet<>(),
                dto.getBehaviourRules(), dto.getPricePerDay(), new HashSet<>(), dto.isCancalletionFree(), dto.getCancalletionFee(), dtoSet);

        MyUser owner = this.myUserService.findUserById(dto.getOwnerId());
        house.setOwner(owner);
        house = this.houseService.save(house);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @PostMapping("/findAllAvailableHouses")
    public ResponseEntity<List<HouseDTO>> findAllAvailableHouses(@RequestBody ReservationCheckDTO reservationCheckDTO){
        List<House> houses = this.houseService.getAllAvailableHouses(reservationCheckDTO);
        List<HouseDTO> houseDTOS = new ArrayList<>();
        for(House house: houses){
            makeHouseDTOList(house,houseDTOS);
        }
        return new ResponseEntity<>(houseDTOS, HttpStatus.OK);
    }

    private void makeHouseDTOList(House house, List<HouseDTO> houseDTOS) {
        AddressDTO addressDTO = new AddressDTO(house.getAddress().getId(), house.getAddress().getStreet(), house.getAddress().getCity(), house.getAddress().getState(),
                house.getAddress().getLongitude(), house.getAddress().getLatitude(), house.getAddress().getPostalCode());

        HouseDTO dto = new HouseDTO(house.getId(), house.getName(), addressDTO, house.getPromoDescription(), house.getBehaviourRules(),
                house.getPricePerDay(), house.isCancalletionFree(), house.getCancalletionFee());
        dto.setGrade(house.getGrade());
        Set<ImageDTO> dtoSet = new HashSet<>();
        for(Image i: house.getImages()){
            ImageDTO imageDTO = modelMapper.map(i, ImageDTO.class);
            dtoSet.add(imageDTO);
        }
        dto.setImages(dtoSet);
        houseDTOS.add(dto);
    }

}

