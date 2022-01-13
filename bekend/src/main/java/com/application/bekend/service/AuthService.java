package com.application.bekend.service;

import com.application.bekend.DTO.MyUserDTO;
import com.application.bekend.model.Address;
import com.application.bekend.model.Authority;
import com.application.bekend.model.MyUser;
import com.application.bekend.model.VerificationRequest;
import com.application.bekend.repository.MyUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    public AuthService(VerificationRequestService verificationRequestService) {
        this.verificationRequestService = verificationRequestService;
    }

    private PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }       // za enkodovanje lozinke

    private final VerificationRequestService verificationRequestService;

    public MyUser sendVerificationEmail(MyUser myUser) {    // preko mejla saljemo verifikacioni token
        Optional<MyUser> saved = Optional.of(this.save(myUser));

        saved.ifPresent(u -> {      // ako je user sacuvan (nije null)
                    try {
                        String token = UUID.randomUUID().toString();
                          // kreiranje verifikacionog tokena

                        this.emailService.sendHTMLMail(saved.get());
                        verificationTokenService.save(saved.get(), token);
                        MyUser user = this.save(myUser);// send verification email
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
        );
        return saved.get();     // vracamo sacuvanog user-a (moze biti i void metoda)
    }

    public MyUser register(MyUserDTO myUserDTO) {
        MyUser myUser = new MyUser();
        myUser.setFirstName(myUserDTO.getFirstName());
        myUser.setLastName(myUserDTO.getLastName());
        myUser.setEmail(myUserDTO.getEmail());
        myUser.setUsername(myUserDTO.getUsername());
        myUser.setPassword(this.passwordEncoder().encode(myUserDTO.getPassword()));
        Address address = new Address();
        address.setStreet(myUserDTO.getAddressDTO().getStreet());
        address.setCity(myUserDTO.getAddressDTO().getCity());
        address.setState(myUserDTO.getAddressDTO().getState());
        address.setLongitude(myUserDTO.getAddressDTO().getLongitude());
        address.setLatitude(myUserDTO.getAddressDTO().getLatitude());
        address.setPostalCode(myUserDTO.getAddressDTO().getPostalCode());
        addresService.save(address);
        myUser.setAddress(address);
        Authority authority = this.authorityService.findAuthorityByName(myUserDTO.getAuthority());
        myUser.addAuthority(authority);
        myUser.setDeleted(false);
        myUser.setPenalties(0);
        myUser.setGrade(0);
        myUser.setActivated(false);
        myUser.setPhoneNumber(myUserDTO.getPhoneNumber());
        myUser.setPersonalDescription(myUserDTO.getPersonalDescription());

        // poslati mejl za obicnog usera
        if(myUserDTO.getAuthority().equals("ROLE_USER")){
            this.sendVerificationEmail(myUser);
        }else{
            // poslati zahtev za verifikaciju za ostale korisnike
            VerificationRequest verificationRequest = new VerificationRequest(myUser, false, myUserDTO.getReasonForRegistration());
            this.verificationRequestService.save(verificationRequest);
        }

        return myUser;
    }

    public MyUser save(MyUser user) {
        return this.myUserRepository.save(user);
    }

    public MyUser findMyUserByEmailOrUsername(String email, String username) {
        return this.myUserRepository.findMyUserByEmailOrUsername(email, username);
    }
}
