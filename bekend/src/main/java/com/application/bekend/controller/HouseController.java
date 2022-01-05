package com.application.bekend.controller;

import com.application.bekend.DTO.AdditionalServicesDTO;
import com.application.bekend.DTO.AddressDTO;
import com.application.bekend.DTO.HouseDTO;
import com.application.bekend.DTO.RoomDTO;
import com.application.bekend.model.*;
import com.application.bekend.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("api/house")
@EnableTransactionManagement
public class HouseController {

    private final HouseService houseService;
    private final RoomService roomService;
    private final AdditionalServicesService additionalServicesService;
    private  final HouseReservationService houseReservationService;
    private final ImageService imageService;

    @Autowired
    public HouseController(HouseService houseService, RoomService roomService, AdditionalServicesService additionalServicesService, HouseReservationService houseReservationService, ImageService imageService) {
        this.houseService = houseService;
        this.roomService = roomService;
        this.additionalServicesService = additionalServicesService;
        this.houseReservationService = houseReservationService;
        this.imageService = imageService;
    }

    @GetMapping("/getHouseById/{id}")
    public ResponseEntity<HouseDTO> getHouseById(@PathVariable("id") Long id) {
        House house = this.houseService.getHouseById(id);
        AddressDTO addressDTO = new AddressDTO(house.getAddress().getId(), house.getAddress().getStreet(), house.getAddress().getCity(), house.getAddress().getState(),
                house.getAddress().getLongitude(), house.getAddress().getLatitude(), house.getAddress().getPostalCode());

        HouseDTO dto = new HouseDTO(house.getId(), house.getName(), addressDTO, house.getPromoDescription(), house.getBehaviourRules(),
                house.getPricePerDay(), house.isCancalletionFree(), house.getCancalletionFee());
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<HouseDTO> save(@RequestBody HouseDTO dto) {
        House house = this.houseService.getHouseById(dto.getId());
        Address address = house.getAddress();
        Set<Room> rooms = house.getRooms();
        Set<AdditionalServices> additionalServices = house.getServices();

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

        address.setStreet(dto.getAddress().getStreet());
        address.setCity(dto.getAddress().getCity());
        address.setState(dto.getAddress().getState());
        address.setLongitude(dto.getAddress().getLongitude());
        address.setLatitude(dto.getAddress().getLatitude());
        address.setPostalCode(dto.getAddress().getPostalCode());

        house.setAddress(address);
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

            for (Room r: h.getRooms())
            {
                RoomDTO roomDTO = new RoomDTO(r.getId(), r.getNumberOfBeds());
                roomDTOS.add(roomDTO);
            }

            for (AdditionalServices a: h.getServices())
            {
                AdditionalServicesDTO additionalServicesDTO = new AdditionalServicesDTO(a.getId(), a.getName(), a.getPrice());
                additionalServicesDTOS.add(additionalServicesDTO);
            }

            HouseDTO houseDTO = new HouseDTO(h.getId(), h.getName(), addressDTO, h.getPromoDescription(), h.getBehaviourRules(), h.getPricePerDay(),
                    h.isCancalletionFree(), h.getCancalletionFee(), roomDTOS, additionalServicesDTOS);
            housesDtos.add(houseDTO);
        }

        return new ResponseEntity<>(housesDtos, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    @Transactional
    public ResponseEntity<Boolean> delete(@PathVariable("id") Long id) {
        House house = this.houseService.getHouseById(id);
        house.setAddress(null);

        for (Room r: house.getRooms()) {
            Room room = this.roomService.getRoomById(r.getId());
            room.setHouse(null);
//            this.roomService.save(room); TODO: delete umesto save
            this.roomService.deleteById(room.getId());
        }

        house.setRooms(null);
        // proci kroz sobe i staviti house_id = null (+ izbrisati iz tabele)

        house.setOwner(null);

        for (Image i: house.getImages()) {
            Image image = this.imageService.getImageById(i.getId());
            image.setHouse(null);
            this.imageService.delete(image.getId());
        }

        house.setImages(null);
        // proci kroz slike i staviti house_id = null (+ izbrisati i sliku)

        // rezervacije
        for (HouseReservation h: house.getCourses()) {
            HouseReservation houseReservation = this.houseReservationService.getHouseReservationById(h.getId());

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
}