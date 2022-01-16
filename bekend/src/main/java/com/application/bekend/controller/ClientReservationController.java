package com.application.bekend.controller;

import com.application.bekend.DTO.AdventureReservationDTO;
import com.application.bekend.DTO.BoatReservationDTO;
import com.application.bekend.DTO.HouseReservationDTO;
import com.application.bekend.service.ClientReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.transaction.Transactional;

@RestController
@RequestMapping("api/user/reservation")
public class ClientReservationController {

    private final ClientReservationService clientReservationService;

    @Autowired
    public ClientReservationController(ClientReservationService clientReservationService) {
        this.clientReservationService = clientReservationService;
    }


    @PostMapping("/house")
    public ResponseEntity<Boolean> addHouseReservationClient(@RequestBody HouseReservationDTO dto) throws MessagingException {

        Boolean success = this.clientReservationService.addHouseReservationClient(dto);
        return new ResponseEntity<>(success,HttpStatus.OK);
    }

    @PostMapping("/boat")
    public ResponseEntity addBoatReservationClient(@RequestBody BoatReservationDTO dto){

        Boolean success = this.clientReservationService.addBoatReservationClient(dto);
        return new ResponseEntity<>(success, HttpStatus.OK);
    }


    @PostMapping("/adventure")
    public ResponseEntity addAdventureReservationClient(@RequestBody AdventureReservationDTO dto){

        return new ResponseEntity<>(HttpStatus.OK);
    }


}
