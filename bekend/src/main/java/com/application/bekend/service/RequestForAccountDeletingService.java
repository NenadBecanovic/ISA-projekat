package com.application.bekend.service;

import com.application.bekend.model.RequestForAccountDeleting;
import com.application.bekend.repository.RequestForAccountDeletingRepository;

import java.util.List;

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
    
    public List<RequestForAccountDeleting> getAllRequests(){
    	return this.requestForAccountDeletingRepository.getAllRequests();
    }
    
    public RequestForAccountDeleting findById(Long id) {
    	return this.requestForAccountDeletingRepository.findRequestForAccountDeletingById(id);
    }
}
