package com.application.bekend.controller;

import com.application.bekend.DTO.AuthUserDTO;
import com.application.bekend.DTO.MyUserDTO;
import com.application.bekend.model.MyUser;
import com.application.bekend.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/indentity")
public class AuthentificationController {
    private final AuthService authService;

    @Autowired
    public AuthentificationController(AuthService authService) {
        this.authService = authService;
    }


    @PostMapping("/register")
    public ResponseEntity<MyUser> registerNewUser(@RequestBody MyUserDTO myUserDTO){

        MyUser user = this.authService.findMyUserByEmailOrUsername(myUserDTO.getEmail(), myUserDTO.getUsername());
        if(user != null){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        this.authService.registerNewUser(myUserDTO);

        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PostMapping("/login")
    public MyUser loginUser(@RequestBody AuthUserDTO authUserDTO)
    {
        System.out.println(authUserDTO.toString());
        MyUser user = authService.loginUser(authUserDTO.getEmail(), authUserDTO.getPassword());
        if(user == null){
            throw new UsernameNotFoundException("User Not found");
        }
        return user;
    }
}
