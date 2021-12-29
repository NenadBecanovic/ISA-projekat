package com.application.bekend.service;

import com.application.bekend.model.MyUser;
import com.application.bekend.model.VerificationRequest;
import com.application.bekend.repository.VerificationRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VerificationRequestService {

    private final VerificationRequestRepository verificationRequestRepository;

    @Autowired
    public VerificationRequestService(VerificationRequestRepository verificationRequestRepository) {
        this.verificationRequestRepository = verificationRequestRepository;
    }

    public VerificationRequest save(VerificationRequest verificationRequest) {
        return this.verificationRequestRepository.save(verificationRequest);
    }
}
