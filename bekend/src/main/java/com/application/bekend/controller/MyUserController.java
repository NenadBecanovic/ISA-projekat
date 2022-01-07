package com.application.bekend.controller;


import com.application.bekend.DTO.*;
import com.application.bekend.model.RequestForAccountDeleting;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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
    private final ModelMapper modelMapper;
    private final HouseService houseService;

    @Autowired
    public MyUserController(MyUserService myUserService, ModelMapper modelMapper,  HouseService houseService) {
        this.myUserService = myUserService;
        this.modelMapper = modelMapper;
        this.houseService = houseService;
    }

    @GetMapping("/findUserByEmail/{email}")
    public ResponseEntity<MyUserDTO> findUserByEmail(@PathVariable("email") String email){
        MyUser myUser = this.myUserService.findUserByEmail(email);
        MyUserDTO dto = modelMapper.map(myUser, MyUserDTO.class);
        AddressDTO addressDTO = modelMapper.map(myUser.getAddress(), AddressDTO.class);

        dto.setAuthority(myUser.getAuthorities().get(0).getName());
        dto.setAddressDTO(addressDTO);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PutMapping("/updateUser")
    public ResponseEntity<MyUserDTO> updateUser(@RequestBody MyUserDTO myUserDTO){
        MyUser myUser = this.myUserService.updateUser(myUserDTO);
        MyUserDTO dto = modelMapper.map(myUser, MyUserDTO.class);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PostMapping("/saveDeleteRequest")
    public ResponseEntity<RequestForAccountDeletingDTO> updateUser(@RequestBody RequestForAccountDeletingDTO dto){
        MyUser user = this.myUserService.findUserByEmail(dto.getEmail());
        RequestForAccountDeleting requestForAccountDeleting = new RequestForAccountDeleting();
        requestForAccountDeleting.setUser(user);
        requestForAccountDeleting.setDescription(dto.getDescription());
        this.myUserService.saveDeleteRequest(requestForAccountDeleting);
        return new ResponseEntity<>(dto, HttpStatus.OK);
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

    @GetMapping("/findUserByHouseReservationId/{id}")
    public ResponseEntity<MyUserDTO> findUserByHouseReservationId(@PathVariable("id") Long id) {
        MyUser myUser = this.myUserService.findUserByHouseReservationId(id);

        AddressDTO addressDTO = new AddressDTO(myUser.getAddress().getId(), myUser.getAddress().getStreet(), myUser.getAddress().getCity(), myUser.getAddress().getState(),
                myUser.getAddress().getLongitude(), myUser.getAddress().getLatitude(), myUser.getAddress().getPostalCode());
        MyUserDTO dto = new MyUserDTO(myUser.getId(), myUser.getFirstName(), myUser.getLastName(), myUser.getEmail(), myUser.getPassword(), myUser.getUsername(),
                addressDTO, myUser.getPhoneNumber());

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
}
