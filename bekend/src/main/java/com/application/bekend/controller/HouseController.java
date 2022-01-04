package com.application.bekend.controller;

import com.application.bekend.DTO.AddressDTO;
import com.application.bekend.DTO.HouseDTO;
import com.application.bekend.DTO.RoomDTO;
import com.application.bekend.model.Address;
import com.application.bekend.model.House;
import com.application.bekend.model.Room;
import com.application.bekend.service.HouseService;
import com.application.bekend.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("api/house")
public class HouseController {

    private final HouseService houseService;
    private final RoomService roomService;

    @Autowired
    public HouseController(HouseService houseService, RoomService roomService) {
        this.houseService = houseService;
        this.roomService = roomService;
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

        for (Room r: rooms) {
            for(RoomDTO roomDTO: dto.getRooms())
            {
                if(r.getId() == roomDTO.getId())
                {
                    r.setNumberOfBeds(roomDTO.getNumberOfBeds());
                    this.roomService.save(r);
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
}