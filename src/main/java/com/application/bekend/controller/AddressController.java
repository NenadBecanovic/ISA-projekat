package com.application.bekend.controller;

import com.application.bekend.DTO.AddressDTO;
import com.application.bekend.model.Address;
import com.application.bekend.service.AddresService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/address")
public class AddressController {

    private final AddresService addresService;

    public AddressController(AddresService addresService) {
        this.addresService = addresService;
    }

    @GetMapping("/getAddressById/{id}")
    public ResponseEntity<AddressDTO> getAddressByHouseId(@PathVariable("id") Long id){
        Address address = this.addresService.getAddressById(id);
        AddressDTO dto = new AddressDTO(address.getId(), address.getStreet(), address.getCity(), address.getState(), address.getLongitude(), address.getLatitude(), address.getPostalCode());
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
}
