package com.application.bekend.service;

import com.application.bekend.model.House;
import com.application.bekend.repository.HouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HouseService {

    private final HouseRepository houseRepository;

    @Autowired
    public HouseService(HouseRepository houseRepository) {
        this.houseRepository = houseRepository;
    }

    public House getHouseById(Long id){
        return this.houseRepository.getHouseById(id);
    }

    public House save(House house) {
        return this.houseRepository.save(house);
    }

    public List<House> findAll() {return this.houseRepository.findAll();}

}
