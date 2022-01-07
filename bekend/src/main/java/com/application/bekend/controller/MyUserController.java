package com.application.bekend.controller;

import com.application.bekend.DTO.*;
import com.application.bekend.model.MyUser;
import com.application.bekend.model.RequestForAccountDeleting;
import com.application.bekend.service.MyUserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/user")
public class MyUserController {

    private final MyUserService myUserService;
    private final ModelMapper modelMapper;

    @Autowired
    public MyUserController(MyUserService myUserService, ModelMapper modelMapper) {
        this.myUserService = myUserService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/findUserByEmail/{email}")
    public ResponseEntity<MyUserDTO> findUserByEmail(@PathVariable("email") String email){
        MyUser myUser = this.myUserService.findUserByEmail(email);
        MyUserDTO dto = modelMapper.map(myUser, MyUserDTO.class);
        AddressDTO addressDTO = modelMapper.map(myUser.getAddress(), AddressDTO.class);

        dto.setAuthority(myUser.getAuthoritiesList().get(0).getName());
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

}
