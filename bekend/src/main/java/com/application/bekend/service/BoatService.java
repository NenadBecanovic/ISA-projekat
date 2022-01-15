package com.application.bekend.service;

import com.application.bekend.DTO.ReservationCheckDTO;
import com.application.bekend.model.Boat;
import com.application.bekend.repository.BoatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BoatService {

    private final BoatRepository boatRepository;
    private final BoatReservationService boatReservationService;

    @Autowired
    public BoatService(BoatRepository boatRepository, BoatReservationService boatReservationService) {
        this.boatRepository = boatRepository;
        this.boatReservationService = boatReservationService;
    }

    public Boat getBoatById(Long id){ return boatRepository.getBoatById(id); }

    public List<Boat> findAllOrderByGrade(){
        return this.boatRepository.findAll(Sort.by(Sort.Direction.DESC, "grade"));
    }

    public List<Boat> findAll(){ return boatRepository.findAll(); }

    public Boat save(Boat boat) {
        return this.boatRepository.save(boat);
    }

    public void delete(Long id){
        boatRepository.deleteById(id);
    }

    public List<Boat> getAllAvailableHouses(ReservationCheckDTO reservationCheckDTO) {
        List<Boat> boats = this.findAll();
        List<Boat> availableBoats = new ArrayList<>();
        for (Boat b: boats){

            if(this.boatReservationService.findBoatAvailability(reservationCheckDTO,b.getId()) &&
                b.getCapacity() >= reservationCheckDTO.getMaxGuest()){
                availableBoats.add(b);
            }
        }
        return availableBoats;
    }
}
