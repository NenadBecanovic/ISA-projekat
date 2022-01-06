package com.application.bekend.controller;

import com.application.bekend.DTO.HomeHouseSlideDTO;
import com.application.bekend.DTO.HouseDTO;
import com.application.bekend.DTO.MyUserDTO;
import com.application.bekend.DTO.RequestForAccountDeletingDTO;
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
        RequestForAccountDeleting requestForAccountDeleting = modelMapper.map(dto, RequestForAccountDeleting.class);
        this.myUserService.saveDeleteRequest(requestForAccountDeleting);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

}
