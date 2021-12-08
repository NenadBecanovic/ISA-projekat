package com.application.bekend.service;

import com.application.bekend.DTO.AuthUserDTO;
import com.application.bekend.model.MyUser;
import com.application.bekend.repository.MyUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MyUserService {

    @Autowired
    private MyUserRepository myUserRepository;

    public MyUser findUserById(Long id){
        return myUserRepository.findMyUserById(id);
    }

    public void activateUser(Long id){
        MyUser user = findUserById(id);
        user.setActivated(true);
        this.myUserRepository.save(user);
    }
}
