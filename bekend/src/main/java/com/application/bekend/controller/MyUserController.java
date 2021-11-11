package com.application.bekend.controller;

import com.application.bekend.model.MyUser;
import com.application.bekend.service.MyUserService;
import org.springframework.beans.factory.annotation.Autowired;
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
}
