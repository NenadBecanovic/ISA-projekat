package com.application.bekend.service;

import com.application.bekend.model.MyUser;
import com.application.bekend.repository.MyUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MyUserService {

    @Autowired
    private MyUserRepository myUserRepository;

    public MyUser registerNewUser(MyUser myUser) {
        return myUserRepository.save(myUser);
    }
}
