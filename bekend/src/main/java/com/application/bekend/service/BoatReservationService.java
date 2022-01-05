package com.application.bekend.service;

import com.application.bekend.model.BoatReservation;
import com.application.bekend.model.HouseReservation;
import com.application.bekend.repository.BoatRepository;
import com.application.bekend.repository.BoatReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoatReservationService {

    private final BoatReservationRepository boatReservationRepository;

    @Autowired
    public BoatReservationService(BoatReservationRepository boatReservationRepository) {
        this.boatReservationRepository = boatReservationRepository;
    }

    public List<BoatReservation> getAllByBoat_Id(Long id) {return boatReservationRepository.getAllByBoat_Id(id); }

    public BoatReservation save(BoatReservation boatReservation) {
        return this.boatReservationRepository.save(boatReservation);
    }

    public BoatReservation getBoatReservationById(Long id) {
        return boatReservationRepository.getBoatReservationById(id);
    }

    public void delete(Long id){
        boatReservationRepository.deleteById(id);
    }
}
