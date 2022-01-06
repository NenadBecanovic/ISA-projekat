package com.application.bekend.controller;

import com.application.bekend.DTO.*;
import com.application.bekend.model.FishingAdventure;
import com.application.bekend.service.FishingAdventureService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/adventure")
public class AdventureController {

    private final FishingAdventureService fishingAdventureService;

    @Autowired
    private ModelMapper modelMapper;

    public AdventureController(FishingAdventureService fishingAdventureService) {
        this.fishingAdventureService = fishingAdventureService;
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<FishingAdventureDTO>> findAll(){
        List<FishingAdventure> adventures = this.fishingAdventureService.findAll();
        List<FishingAdventureDTO> adventureDTOS = new ArrayList<>();
        for(FishingAdventure adventure: adventures){
            FishingAdventureDTO dto = modelMapper.map(adventure, FishingAdventureDTO.class);
            adventureDTOS.add(dto);
        }
        return new ResponseEntity<>(adventureDTOS, HttpStatus.OK);
    }

    @GetMapping("/findAdventuresForHomePage")
    public ResponseEntity<List<HomeAdventureSlideDTO>> findAdventuresForHomePage(){
        List<FishingAdventure> houses = this.fishingAdventureService.findAll();
        List<FishingAdventureDTO> adventureDTOS = new ArrayList<>();
        for(FishingAdventure house: houses){
            addHouseDto(adventureDTOS, house);
        }

        List<HomeAdventureSlideDTO> homeAdventureSlideDTOS = getHomeAdventureSlideDTOS(adventureDTOS);

        return new ResponseEntity<>(homeAdventureSlideDTOS, HttpStatus.OK);
    }

    private void addHouseDto(List<FishingAdventureDTO> adventureDTOS, FishingAdventure adventure) {
        FishingAdventureDTO dto = modelMapper.map(adventure, FishingAdventureDTO.class);
        adventureDTOS.add(dto);
    }

    private List<HomeAdventureSlideDTO> getHomeAdventureSlideDTOS(List<FishingAdventureDTO> adventureDTOS) {
        List<HomeAdventureSlideDTO> homeAdventureSlideDTOS = new ArrayList<>();
        List<FishingAdventureDTO> adventureDTOs1 = new ArrayList<>();
        int i = 1;
        for (FishingAdventureDTO dto : adventureDTOS) {
            adventureDTOs1.add(dto);
            if (i % 4 == 0) {
                HomeAdventureSlideDTO homeAdventureSlideDTO = new HomeAdventureSlideDTO(adventureDTOs1);
                homeAdventureSlideDTOS.add(homeAdventureSlideDTO);
                adventureDTOs1 = new ArrayList<>();
            }
            i = i + 1;
        }

        if (adventureDTOs1.size() != 0) {
            HomeAdventureSlideDTO homeAdventureSlideDTO = new HomeAdventureSlideDTO(adventureDTOs1);
            homeAdventureSlideDTOS.add(homeAdventureSlideDTO);
        }
        return homeAdventureSlideDTOS;
    }
}
