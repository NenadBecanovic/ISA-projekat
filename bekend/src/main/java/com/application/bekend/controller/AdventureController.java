package com.application.bekend.controller;

import com.application.bekend.DTO.AddressDTO;
import com.application.bekend.DTO.BoatDTO;
import com.application.bekend.DTO.FishingAdventureDTO;
import com.application.bekend.model.Boat;
import com.application.bekend.model.FishingAdventure;
import com.application.bekend.service.FishingAdventureService;
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

    public AdventureController(FishingAdventureService fishingAdventureService) {
        this.fishingAdventureService = fishingAdventureService;
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<FishingAdventureDTO>> findAll(){
        List<FishingAdventure> adventures = this.fishingAdventureService.findAll();
        List<FishingAdventureDTO> adventureDTOS = new ArrayList<>();
        for(FishingAdventure adventure: adventures){
            AddressDTO addressDTO = new AddressDTO(adventure.getAddress().getId(), adventure.getAddress().getStreet(), adventure.getAddress().getCity(),
                    adventure.getAddress().getState(), adventure.getAddress().getLongitude(), adventure.getAddress().getLatitude(), adventure.getAddress().getPostalCode());

            FishingAdventureDTO dto = new FishingAdventureDTO(adventure.getId(), adventure.getName(),addressDTO,adventure.getPromoDescription(), adventure.getCapacity(), adventure.getFishingEquipment(), adventure.getBehaviourRules(), adventure.getPricePerDay(), adventure.isCancalletionFree(), adventure.getCancalletionFee());
            adventureDTOS.add(dto);
        }
        return new ResponseEntity<>(adventureDTOS, HttpStatus.OK);
    }
}
