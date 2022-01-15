package com.application.bekend.service;

import com.application.bekend.DTO.ReservationCheckDTO;
import com.application.bekend.model.House;
import com.application.bekend.repository.HouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HouseService {

    private final HouseRepository houseRepository;
    private final HouseReservationService houseReservationService;

    @Autowired
    public HouseService(HouseRepository houseRepository, HouseReservationService houseReservationService) {
        this.houseRepository = houseRepository;
        this.houseReservationService = houseReservationService;
    }

    public House getHouseById(Long id){
        return this.houseRepository.getHouseById(id);
    }

    public List<House> findAllOrderByGrade(){
        return this.houseRepository.findAll(Sort.by(Sort.Direction.DESC, "grade"));
    }

    public House save(House house) {
        return this.houseRepository.save(house);
    }

    public List<House> findAll() {return this.houseRepository.findAll();}

    public List<House> getAll() { return  this.houseRepository.findAll(); }

    public void delete(Long id){
        houseRepository.deleteById(id);
    }

    public List<House> getAllAvailableHouses(ReservationCheckDTO checkDTO){
        List<House> houses = this.findAll();
        List<House> availableHouses = new ArrayList<>();
        for (House h: houses){

            if(this.houseReservationService.findHouseAvailability(checkDTO,h.getId()) &&
                    this.houseReservationService.checkMaxGuest(checkDTO.getMaxGuest(), h) ){
                availableHouses.add(h);
            }
        }
        return availableHouses;

    }
}
