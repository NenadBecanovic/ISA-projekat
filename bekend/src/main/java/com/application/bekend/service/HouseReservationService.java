package com.application.bekend.service;

import com.application.bekend.model.HouseReservation;
import com.application.bekend.repository.HouseReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class HouseReservationService {

    private final HouseReservationRepository houseReservationsRepository;

    @Autowired
    public HouseReservationService(HouseReservationRepository houseReservationsRepository) {
        this.houseReservationsRepository = houseReservationsRepository;
    }

    public List<HouseReservation> getAllByHouse_Id(Long id) {
        return houseReservationsRepository.getAllByHouse_Id(id);
    }

    public HouseReservation save(HouseReservation houseReservation) {
        return this.houseReservationsRepository.save(houseReservation);
    }

    public HouseReservation getHouseReservationById(Long id) {
        return houseReservationsRepository.getHouseReservationById(id);
    }

    public void delete(Long id){
        houseReservationsRepository.deleteById(id);
    }

    public List<HouseReservation> getHouseReservationByUserId(Long id) {
        return houseReservationsRepository.findHouseReservationByGuestId(id);
    }

    public  List<HouseReservation> getHouseReservationsByGuestId(Long id) { return this.houseReservationsRepository.getHouseReservationsByGuestId(id); }

}
