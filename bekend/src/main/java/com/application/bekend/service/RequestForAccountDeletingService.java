package com.application.bekend.service;

import com.application.bekend.model.RequestForAccountDeleting;
import com.application.bekend.repository.RequestForAccountDeletingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RequestForAccountDeletingService {

    private final RequestForAccountDeletingRepository requestForAccountDeletingRepository;
    @Autowired
    public RequestForAccountDeletingService(RequestForAccountDeletingRepository requestForAccountDeletingRepository) {
        this.requestForAccountDeletingRepository = requestForAccountDeletingRepository;
    }

    public RequestForAccountDeleting save(RequestForAccountDeleting requestForAccountDeleting){
        return this.requestForAccountDeletingRepository.save(requestForAccountDeleting);
    }
}
