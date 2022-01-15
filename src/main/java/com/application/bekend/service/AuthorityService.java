package com.application.bekend.service;

import com.application.bekend.model.Authority;
import com.application.bekend.repository.AuthorityRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthorityService {

    private final AuthorityRepository authorityRepository;

    public AuthorityService(AuthorityRepository authorityRepository) {
        this.authorityRepository = authorityRepository;
    }

    public Authority findAuthorityByName(String name){
        return this.authorityRepository.findAuthorityByName(name);
    }

}
