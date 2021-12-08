package com.application.bekend.controller;

import com.application.bekend.DTO.ActivationDTO;
import com.application.bekend.DTO.AuthUserDTO;
import com.application.bekend.DTO.MyUserDTO;
import com.application.bekend.model.MyUser;
import com.application.bekend.model.VerificationToken;
import com.application.bekend.service.AuthService;
import com.application.bekend.service.MyUserService;
import com.application.bekend.service.VerificationTokenService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.sql.Timestamp;


@RestController
@RequestMapping("api/indentity")
public class AuthentificationController {
    private final AuthService authService;
    private final MyUserService myUserService;
    private final VerificationTokenService verificationTokenService;

    @Autowired
    public AuthentificationController(AuthService authService, MyUserService myUserService, VerificationTokenService verificationTokenService) {
        this.authService = authService;
        this.myUserService = myUserService;
        this.verificationTokenService = verificationTokenService;
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
    public ResponseEntity<MyUser> loginUser(@RequestBody AuthUserDTO authUserDTO)
    {
        System.out.println(authUserDTO.toString());
        MyUser user = authService.loginUser(authUserDTO.getEmail(), authUserDTO.getPassword());
        if(user == null){
            throw new UsernameNotFoundException("User Not found");
        }

        return new ResponseEntity<>(user,HttpStatus.ACCEPTED);
    }

    @PostMapping("/email/verification")
    public ResponseEntity indentifyUser(@RequestBody ActivationDTO activationDTO) throws UserPrincipalNotFoundException {
        MyUser user = this.myUserService.findUserById(activationDTO.getUserId());
        if(user == null){
            throw new UserPrincipalNotFoundException("User not found");
        }

        VerificationToken verificationToken = this.verificationTokenService.findByToken(activationDTO.getToken());
        if(verificationToken.getUser() == user){
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            if(verificationToken.getExpiryDate().after(timestamp)){
                this.myUserService.activateUser(user.getId());
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }


        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
