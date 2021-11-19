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


}
