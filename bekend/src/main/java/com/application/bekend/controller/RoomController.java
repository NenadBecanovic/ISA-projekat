package com.application.bekend.controller;

import com.application.bekend.DTO.RoomDTO;
import com.application.bekend.model.AdditionalServices;
import com.application.bekend.model.House;
import com.application.bekend.model.Room;
import com.application.bekend.service.HouseService;
import com.application.bekend.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("api/room")
@EnableTransactionManagement
public class RoomController {

    private final RoomService roomService;
    private final HouseService houseService;

    @Autowired
    public RoomController(RoomService roomService, HouseService houseService) {
        this.roomService = roomService;
        this.houseService = houseService;
    }

    @GetMapping("/getAllByHouseId/{id}")
    public ResponseEntity<List<RoomDTO>> getAllByHouseId(@PathVariable("id") Long id){
        List<Room> rooms = this.roomService.getAllByHouse_Id(id);
        List<RoomDTO> roomsDTO = new ArrayList<>();

        for (Room r:rooms) {
            RoomDTO roomDTO = new RoomDTO(r.getId(), r.getNumberOfBeds());
            roomsDTO.add(roomDTO);
        }

        return new ResponseEntity<>(roomsDTO, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Room> save(@RequestBody RoomDTO dto) {
        Room room = new Room(dto.getNumberOfBeds());
        House house = this.houseService.getHouseById(dto.getHouseId());

        room.setHouse(house);
        this.roomService.save(room);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    @Transactional
    public ResponseEntity<Boolean> delete(@PathVariable("id") Long id) {
        Room room = this.roomService.getRoomById(id);
        room.setHouse(null);
        this.roomService.deleteById(id);

        return new ResponseEntity<>(true, HttpStatus.OK);
    }
}
