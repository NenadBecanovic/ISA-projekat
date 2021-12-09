package com.application.bekend.service;

import com.application.bekend.DTO.MyUserDTO;
import com.application.bekend.model.Address;
import com.application.bekend.model.Authority;
import com.application.bekend.model.MyUser;
import com.application.bekend.model.VerificationRequest;
import com.application.bekend.repository.MyUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService {
    @Autowired
    private MyUserRepository myUserRepository;
    @Autowired
    private AddresService addresService;
    @Autowired
    private AuthorityService authorityService;
    @Autowired
    private VerificationTokenService verificationTokenService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private VerificationRequestService verificationRequestService;

    public MyUser registerNewUser(MyUserDTO myUserDTO) {
        MyUser myUser = new MyUser();
        myUser.setFirstName(myUserDTO.getFirstName());
        myUser.setLastName(myUserDTO.getLastName());
        myUser.setEmail(myUserDTO.getEmail());
        myUser.setUsername(myUserDTO.getUsername());
        myUser.setPassword(myUserDTO.getPassword());
        Address address = new Address();
        address.setStreet(myUserDTO.getAddressDTO().getStreet());
        address.setCity(myUserDTO.getAddressDTO().getCity());
        address.setState(myUserDTO.getAddressDTO().getState());
        address.setLongitude(myUserDTO.getAddressDTO().getLongitude());
        address.setLatitude(myUserDTO.getAddressDTO().getLatitude());
        addresService.save(address);

        myUser.setAddress(address);
        Authority authority = this.authorityService.findAuthorityByName(myUserDTO.getAuthority());
        myUser.setAuthority(authority);
        myUser.setActivated(false);

        Optional<MyUser> saved = Optional.of(this.save(myUser));

        //creation of validation token
        saved.ifPresent(u -> {      // ako je user sacuvan (nije null)
                    try {
                        String token = UUID.randomUUID().toString();
                        verificationTokenService.save(saved.get(), token);
                        //send verification email
                        this.emailService.sendHTMLMail(saved.get());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
        );
        return saved.get();     // vracamo sacuvanog user-a
    }

    public MyUser register(MyUserDTO myUserDTO) {
        MyUser myUser = new MyUser();
        myUser.setFirstName(myUserDTO.getFirstName());
        myUser.setLastName(myUserDTO.getLastName());
        myUser.setEmail(myUserDTO.getEmail());
        myUser.setUsername(myUserDTO.getUsername());
        myUser.setPassword(myUserDTO.getPassword());
        Address address = new Address();
        address.setStreet(myUserDTO.getAddressDTO().getStreet());
        address.setCity(myUserDTO.getAddressDTO().getCity());
        address.setState(myUserDTO.getAddressDTO().getState());
        address.setLongitude(myUserDTO.getAddressDTO().getLongitude());
        address.setLatitude(myUserDTO.getAddressDTO().getLatitude());
        addresService.save(address);
        myUser.setAddress(address);
        Authority authority = this.authorityService.findAuthorityByName(myUserDTO.getAuthority());
        myUser.setAuthority(authority);
        myUser.setActivated(false);
        myUser.setPhoneNumber(myUserDTO.getPhoneNumber());
        this.save(myUser);

        // poslati zahtev za registraciju administratoru
        VerificationRequest verificationRequest = new VerificationRequest(myUser, false, myUserDTO.getReasonForRegistration());
        this.verificationRequestService.save(verificationRequest);

        return myUser;
    }

    public MyUser save(MyUser user) {
        return this.myUserRepository.save(user);
    }

    public MyUser loginUser(String username, String password) {
        return myUserRepository.findMyUserByEmailAndPassword(username, password);
    }

    public MyUser findMyUserByEmailOrUsername(String email, String username) {
        return this.myUserRepository.findMyUserByEmailOrUsername(email, username);
    }
}
