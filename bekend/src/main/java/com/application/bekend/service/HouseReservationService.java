package com.application.bekend.service;

import com.application.bekend.model.HouseReservation;
import com.application.bekend.repository.HouseReservationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HouseReservationService {

    private final HouseReservationsRepository houseReservationsRepository;

    @Autowired
    public HouseReservationService(HouseReservationsRepository houseReservationsRepository) {
        this.houseReservationsRepository = houseReservationsRepository;
    }

    public List<HouseReservation> getAllByHouse_Id(Long id) { return houseReservationsRepository.getAllByHouse_Id(id);}
}
