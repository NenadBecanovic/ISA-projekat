package com.application.bekend.service;

import com.application.bekend.DTO.MyUserDTO;
import com.application.bekend.model.Address;
import com.application.bekend.model.MyUser;
import com.application.bekend.model.RequestForAccountDeleting;
import com.application.bekend.repository.MyUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MyUserService implements UserDetailsService {

    @Autowired
    private MyUserRepository myUserRepository;

    private final AddresService addresService;
    private final RequestForAccountDeletingService requestForAccountDeletingService;

    private PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }       // za enkodovanje lozinke

    @Autowired
    public MyUserService(AddresService addresService, RequestForAccountDeletingService requestForAccountDeletingService) {
        this.addresService = addresService;
        this.requestForAccountDeletingService = requestForAccountDeletingService;
    }

    public MyUser findUserById(Long id){
        return myUserRepository.findMyUserById(id);
    }

    public MyUser findUserByEmailorUsername(String email, String username){
        return myUserRepository.findMyUserByEmailOrUsername(email, username);
    }

    public MyUser findUserByEmail(String email){
        return myUserRepository.findMyUserByEmail(email);
    }

    public void activateUser(Long id){
        MyUser user = findUserById(id);
        user.setActivated(true);
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

    public MyUser updateUser(MyUserDTO myUserDTO){
        MyUser myUser = findUserByEmail(myUserDTO.getEmail());
        Address address = this.addresService.updateAddress(myUserDTO.getAddressDTO());
        myUser.setFirstName(myUserDTO.getFirstName());
        myUser.setLastName(myUserDTO.getLastName());
        myUser.setEmail(myUserDTO.getEmail());
        myUser.setUsername(myUserDTO.getUsername());
        if (myUserDTO.isPasswordChange()){
            myUser.setPassword(this.passwordEncoder().encode(myUserDTO.getPassword()));
        }
        myUser.setAddress(address);
        myUser.setPhoneNumber(myUserDTO.getPhoneNumber());
        MyUser user = this.myUserRepository.save(myUser);
        return user;
    }

    public RequestForAccountDeleting saveDeleteRequest(RequestForAccountDeleting requestForAccountDeleting){
        return this.requestForAccountDeletingService.save(requestForAccountDeleting);
    }

}
