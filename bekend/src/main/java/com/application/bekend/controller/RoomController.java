package com.application.bekend.controller;

import com.application.bekend.DTO.AddressDTO;
import com.application.bekend.DTO.HouseDTO;
import com.application.bekend.DTO.RoomDTO;
import com.application.bekend.model.House;
import com.application.bekend.model.Room;
import com.application.bekend.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("api/room")
public class RoomController {

    private final RoomService roomService;

    @Autowired
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
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
}
