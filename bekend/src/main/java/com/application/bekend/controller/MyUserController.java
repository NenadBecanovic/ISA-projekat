package com.application.bekend.controller;

import com.application.bekend.DTO.AuthUserDTO;
import com.application.bekend.model.MyUser;
import com.application.bekend.service.MyUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/user")
public class MyUserController {
    private final MyUserService myUserService;

    @Autowired
    public MyUserController(MyUserService myUserService) {
        this.myUserService = myUserService;
    }

    @PostMapping("/register")
    public MyUser registerNewUser(@RequestBody MyUser myUser){
        return myUserService.registerNewUser(myUser);
    }

    @PostMapping("/login")
    public MyUser loginUser(@RequestBody AuthUserDTO authUserDTO)
    {
        System.out.println(authUserDTO.toString());
        MyUser user = myUserService.loginUser(authUserDTO.getEmail(), authUserDTO.getPassword());
        if(user == null){
            throw new UsernameNotFoundException("User Not found");
        }
        return user;
    }

}
