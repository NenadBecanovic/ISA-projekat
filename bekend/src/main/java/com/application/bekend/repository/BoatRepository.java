package com.application.bekend.repository;

import com.application.bekend.model.Boat;
import com.application.bekend.model.FishingAdventure;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoatRepository extends JpaRepository<Boat, Long> {

    Boat getBoatById(Long id);

    void deleteById(Long id);

    Boat getBoatByName(String name);
    
    List<Boat> getBoatByOwnerId(Long id);
}
