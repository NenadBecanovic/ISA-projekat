package com.application.bekend.controller;

import com.application.bekend.DTO.ActionDTO;
import com.application.bekend.DTO.AdventureReservationDTO;
import com.application.bekend.DTO.BoatReservationDTO;
import com.application.bekend.DTO.HouseReservationDTO;
import com.application.bekend.service.ClientReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.transaction.Transactional;

@RestController
@RequestMapping("api/user/reservation")
@EnableTransactionManagement
public class ClientReservationController {

    private final ClientReservationService clientReservationService;

    @Autowired
    public ClientReservationController(ClientReservationService clientReservationService) {
        this.clientReservationService = clientReservationService;
    }

    @PreAuthorize("hasRole('ROLE_HOUSE_OWNER')")
    @PostMapping("/house")
    @Transactional
    public ResponseEntity<Boolean> addHouseReservationClient(@RequestBody HouseReservationDTO dto) {

        Boolean success = this.clientReservationService.addHouseReservationClient(dto);
        return new ResponseEntity<>(success,HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_HOUSE_OWNER')")
    @PostMapping("/boat")
    @Transactional
    public ResponseEntity<Boolean> addBoatReservationClient(@RequestBody BoatReservationDTO dto){

        Boolean success = this.clientReservationService.addBoatReservationClient(dto);
        return new ResponseEntity<>(success, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_HOUSE_OWNER')")
    @PostMapping("/adventure")
    public ResponseEntity<Boolean> addAdventureReservationClient(@RequestBody AdventureReservationDTO dto){

        Boolean success = this.clientReservationService.addAdventureReservationClient(dto);
        return new ResponseEntity<>(success, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_HOUSE_OWNER')")
    @PostMapping("/action/house")
    @Transactional
    public ResponseEntity<Boolean> addHouseActionClient(@RequestBody ActionDTO dto) {

        Boolean success = this.clientReservationService.addHouseActionClient(dto);
        return new ResponseEntity<>(success,HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_HOUSE_OWNER')")
    @PostMapping("/action/boat")
    @Transactional
    public ResponseEntity<Boolean> addBoatActionClient(@RequestBody ActionDTO dto){

        Boolean success = this.clientReservationService.addBoatActionClient(dto);
        return new ResponseEntity<>(success, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_HOUSE_OWNER')")
    @PostMapping("action/adventure")
    @Transactional
    public ResponseEntity<Boolean> addAdventureActionClient(@RequestBody ActionDTO dto){

        Boolean success = this.clientReservationService.addAdventureActionClient(dto);
        return new ResponseEntity<>(success, HttpStatus.OK);
    }


}
