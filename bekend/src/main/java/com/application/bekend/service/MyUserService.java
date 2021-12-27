package com.application.bekend.service;

import com.application.bekend.DTO.AuthUserDTO;
import com.application.bekend.model.MyUser;
import com.application.bekend.repository.MyUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserService implements UserDetailsService {

    @Autowired
    private MyUserRepository myUserRepository;

    public MyUser registerNewUser(MyUser myUser) {
        return myUserRepository.save(myUser);
    }

    public MyUser loginUser(String username , String password){
        return myUserRepository.findMyUserByEmailAndPassword(username, password);
    }

    public MyUser findUserById(Long id){
        return myUserRepository.findMyUserById(id);
    }

    public MyUser findUserByEmail(String email, String username){
        return myUserRepository.findMyUserByEmailOrUsername(email, username);
    }

    public void activateUser(Long id){
        MyUser user = findUserById(id);
        user.setIsActivated(true);
        this.myUserRepository.save(user);

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MyUser myUser = this.myUserRepository.findMyUserByUsername(username);
        if (myUser == null){
            throw new UsernameNotFoundException("Username or email not found");
        }
        return myUser;
    }
}
