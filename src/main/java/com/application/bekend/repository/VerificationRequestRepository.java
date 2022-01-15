package com.application.bekend.repository;

import com.application.bekend.model.VerificationRequest;
import com.application.bekend.model.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationRequestRepository extends JpaRepository<VerificationRequest, Long> {

}
