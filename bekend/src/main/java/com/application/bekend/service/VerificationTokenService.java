package com.application.bekend.service;


import com.application.bekend.model.MyUser;
import com.application.bekend.model.VerificationToken;
import com.application.bekend.repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Calendar;

@Service
public class VerificationTokenService {

    private final VerificationTokenRepository verificationTokenRepository;

    @Autowired
    public VerificationTokenService(VerificationTokenRepository verificationTokenRepository) {
        this.verificationTokenRepository = verificationTokenRepository;
    }

    public VerificationToken findByToken(String token){
        return this.verificationTokenRepository.findByToken(token);
    }

    public VerificationToken findByUser(MyUser user){
        return verificationTokenRepository.findByUser(user);
    }

    public void save(MyUser user, String token){
        VerificationToken verificationToken = new VerificationToken(token, user);
        verificationToken.setExpiryDate(this.calculateExpiryDate(60*24));
        this.verificationTokenRepository.save(verificationToken);
    }

    private Timestamp calculateExpiryDate(int expiryTimeInMinutes){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Timestamp(cal.getTime().getTime());
    }


}
