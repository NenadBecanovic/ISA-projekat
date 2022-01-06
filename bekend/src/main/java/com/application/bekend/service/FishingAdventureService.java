package com.application.bekend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.application.bekend.model.FishingAdventure;
import com.application.bekend.repository.FishingAdventureRepository;
import com.application.bekend.model.House;
import org.springframework.data.domain.Sort;

@Service
public class FishingAdventureService {

    private final FishingAdventureRepository fishingAdventureRepository;

    @Autowired
    public FishingAdventureService(FishingAdventureRepository fishingAdventureRepository) {
        this.fishingAdventureRepository = fishingAdventureRepository;
    }

    public FishingAdventure getFishingAdventureById(Long id){ return fishingAdventureRepository.getFishingAdventureById(id); }
    
    public List<FishingAdventure> getFishingAdventuresByInstructor(Long id){ return fishingAdventureRepository.getFishingAdventuresByInstructor(id); }


    public List<FishingAdventure> findAll(){return this.fishingAdventureRepository.findAll();}
}
