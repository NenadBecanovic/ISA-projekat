package com.application.bekend.service;

import com.application.bekend.model.Boat;
import com.application.bekend.model.House;
import com.application.bekend.repository.BoatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoatService {

    private final BoatRepository boatRepository;

    @Autowired
    public BoatService(BoatRepository boatRepository) {
        this.boatRepository = boatRepository;
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

}
