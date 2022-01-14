package com.application.bekend.repository;

import com.application.bekend.model.FishingAdventure;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FishingAdventureRepository extends JpaRepository<FishingAdventure, Long> {

    FishingAdventure getFishingAdventureById(Long id);
    
    List<FishingAdventure> getFishingAdventuresByInstructor_Id(Long id);
}
