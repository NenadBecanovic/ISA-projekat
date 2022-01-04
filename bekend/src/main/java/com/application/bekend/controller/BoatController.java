package com.application.bekend.controller;

import com.application.bekend.DTO.AddressDTO;
import com.application.bekend.DTO.BoatDTO;
import com.application.bekend.DTO.HouseDTO;
import com.application.bekend.model.Boat;
import com.application.bekend.model.House;
import com.application.bekend.service.BoatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/boat")
public class BoatController {

    private final BoatService boatService;

    @Autowired
    public BoatController(BoatService boatService) {
        this.boatService = boatService;
    }

    @GetMapping("/getBoatById/{id}")
    public ResponseEntity<BoatDTO> getBoatById(@PathVariable("id") Long id){
        Boat boat = this.boatService.getBoatById(id);
        AddressDTO addressDTO = new AddressDTO(boat.getAddress().getId(), boat.getAddress().getStreet(), boat.getAddress().getCity(),
                boat.getAddress().getState(), boat.getAddress().getLongitude(), boat.getAddress().getLatitude(), boat.getAddress().getPostalCode());

        BoatDTO dto = new BoatDTO(boat.getId(), boat.getName(), boat.getType(), boat.getLength(), boat.getEngineNumber(), boat.getEnginePower(), boat.getMaxSpeed(),
                boat.getPromoDescription(), boat.getCapacity(), boat.getBehaviourRules(), boat.getFishingEquipment(), boat.getPricePerDay(), boat.isCancalletionFree(), boat.getCancalletionFee(), addressDTO);

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<BoatDTO>> findAll(){
        List<Boat> boats = this.boatService.findAll();
        List<BoatDTO> boatDTOS = new ArrayList<>();
        for(Boat boat: boats){
            AddressDTO addressDTO = new AddressDTO(boat.getAddress().getId(), boat.getAddress().getStreet(), boat.getAddress().getCity(),
                    boat.getAddress().getState(), boat.getAddress().getLongitude(), boat.getAddress().getLatitude(), boat.getAddress().getPostalCode());

            BoatDTO dto = new BoatDTO(boat.getId(), boat.getName(), boat.getType(), boat.getLength(), boat.getEngineNumber(), boat.getEnginePower(), boat.getMaxSpeed(),
                    boat.getPromoDescription(), boat.getCapacity(), boat.getBehaviourRules(), boat.getFishingEquipment(), boat.getPricePerDay(), boat.isCancalletionFree(), boat.getCancalletionFee(), addressDTO);
            boatDTOS.add(dto);
        }
        return new ResponseEntity<>(boatDTOS, HttpStatus.OK);
    }

}
