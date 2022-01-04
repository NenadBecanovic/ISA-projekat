package com.application.bekend.controller;

import com.application.bekend.DTO.AddressDTO;
import com.application.bekend.DTO.HouseDTO;
import com.application.bekend.model.House;
import com.application.bekend.service.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/house")
public class HouseController {

    private final HouseService houseService;

    @Autowired
    public HouseController(HouseService houseService) {
        this.houseService = houseService;
    }

    @GetMapping("/getHouseById/{id}")
    public ResponseEntity<HouseDTO> getHouseById(@PathVariable("id") Long id){
        House house = this.houseService.getHouseById(id);
        AddressDTO addressDTO = new AddressDTO(house.getAddress().getId(), house.getAddress().getStreet(), house.getAddress().getCity(), house.getAddress().getState(),
                house.getAddress().getLongitude(), house.getAddress().getLatitude(), house.getAddress().getPostalCode());

        HouseDTO dto = new HouseDTO(house.getId(), house.getName(), addressDTO, house.getPromoDescription(), house.getBehaviourRules(),
                house.getPricePerDay(), house.isCancalletionFree(), house.getCancalletionFee());
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }


    @GetMapping("/findAll")
    public ResponseEntity<List<HouseDTO>> findAll(){
        List<House> houses = this.houseService.findAll();
        List<HouseDTO> houseDTOS = new ArrayList<>();
        for(House house: houses){
            AddressDTO addressDTO = new AddressDTO(house.getAddress().getId(), house.getAddress().getStreet(), house.getAddress().getCity(), house.getAddress().getState(),
                    house.getAddress().getLongitude(), house.getAddress().getLatitude(), house.getAddress().getPostalCode());

            HouseDTO dto = new HouseDTO(house.getId(), house.getName(), addressDTO, house.getPromoDescription(), house.getBehaviourRules(),
                    house.getPricePerDay(), house.isCancalletionFree(), house.getCancalletionFee());
            houseDTOS.add(dto);
        }
        return new ResponseEntity<>(houseDTOS, HttpStatus.OK);
    }
}
