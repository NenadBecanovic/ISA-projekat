package com.application.bekend.service;

import com.application.bekend.model.FishingAdventure;
import com.application.bekend.repository.FishingAdventureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FishingAdventureService {

    private final FishingAdventureRepository fishingAdventureRepository;

    @Autowired
    public FishingAdventureService(FishingAdventureRepository fishingAdventureRepository) {
        this.fishingAdventureRepository = fishingAdventureRepository;
    }

    public List<FishingAdventure> findAll(){return this.fishingAdventureRepository.findAll();}
}
