package com.application.bekend.controller;

import com.application.bekend.DTO.AddressDTO;
import com.application.bekend.DTO.MyUserDTO;
import com.application.bekend.model.House;
import com.application.bekend.model.MyUser;
import com.application.bekend.service.HouseService;
import com.application.bekend.service.MyUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("api/user")
public class MyUserController {

    private final MyUserService myUserService;
    private final HouseService houseService;

    public MyUserController(MyUserService myUserService, HouseService houseService) {
        this.myUserService = myUserService;
        this.houseService = houseService;
    }

    @GetMapping("/getAllByHouseId/{id}")
    public ResponseEntity<Set<MyUserDTO>> getAllByHouseId(@PathVariable("id") Long id) {
        Set<MyUserDTO> myUserDTOS = new HashSet<>();

        for (MyUser user: this.myUserService.getAllByHouseId(id)) {
            AddressDTO addressDTO = new AddressDTO(user.getAddress().getId(), user.getAddress().getStreet(), user.getAddress().getCity(), user.getAddress().getState(),
                    user.getAddress().getLongitude(), user.getAddress().getLatitude(), user.getAddress().getPostalCode());

            MyUserDTO dto = new MyUserDTO(user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword(), user.getUsername(), addressDTO, user.getPhoneNumber());
            dto.setId(user.getId());
            myUserDTOS.add(dto);
        }

        return new ResponseEntity<>(myUserDTOS, HttpStatus.OK);
    }
}
