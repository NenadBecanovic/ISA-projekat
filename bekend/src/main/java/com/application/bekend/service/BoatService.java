package com.application.bekend.service;

import com.application.bekend.model.Boat;
import com.application.bekend.repository.BoatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoatService {

    private final BoatRepository boatRepository;

    @Autowired
    public BoatService(BoatRepository boatRepository) {
        this.boatRepository = boatRepository;
    }

    public Boat getBoatById(Long id){ return boatRepository.getBoatById(id); }
}
