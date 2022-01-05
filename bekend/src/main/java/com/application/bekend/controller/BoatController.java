package com.application.bekend.controller;

import com.application.bekend.DTO.*;
import com.application.bekend.model.Boat;
import com.application.bekend.model.House;
import com.application.bekend.model.Image;
import com.application.bekend.service.BoatService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("api/boat")
public class BoatController {

    private final BoatService boatService;

    @Autowired
    public BoatController(BoatService boatService) {
        this.boatService = boatService;
    }
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/getBoatById/{id}")
    public ResponseEntity<BoatDTO> getBoatById(@PathVariable("id") Long id){
        Boat boat = this.boatService.getBoatById(id);
        AddressDTO addressDTO = new AddressDTO(boat.getAddress().getId(), boat.getAddress().getStreet(), boat.getAddress().getCity(),
                boat.getAddress().getState(), boat.getAddress().getLongitude(), boat.getAddress().getLatitude(), boat.getAddress().getPostalCode());

        BoatDTO dto = new BoatDTO(boat.getId(), boat.getName(), boat.getType(), boat.getLength(), boat.getEngineNumber(), boat.getEnginePower(), boat.getMaxSpeed(),
                boat.getPromoDescription(), boat.getCapacity(), boat.getBehaviourRules(), boat.getFishingEquipment(), boat.getPricePerDay(), boat.isCancalletionFree(), boat.getCancalletionFee(), addressDTO);
        dto.setGrade(boat.getGrade());
        Set<ImageDTO> dtoSet = new HashSet<>();
        for(Image i: boat.getImages()){
            ImageDTO postDto = modelMapper.map(i, ImageDTO.class);
            dtoSet.add(postDto);
        }
        dto.setImages(dtoSet);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<BoatDTO>> findAll(){
        List<Boat> boats = this.boatService.findAll();
        List<BoatDTO> boatDTOS = new ArrayList<>();
        for(Boat boat: boats){
            AddressDTO addressDTO = new AddressDTO(boat.getAddress().getId(), boat.getAddress().getStreet(), boat.getAddress().getCity(),
                    boat.getAddress().getState(), boat.getAddress().getLongitude(), boat.getAddress().getLatitude(), boat.getAddress().getPostalCode());

            BoatDTO dto = new BoatDTO(boat.getId(), boat.getName(), boat.getType(), boat.getLength(), boat.getEngineNumber(), boat.getEnginePower(), boat.getMaxSpeed(),
                    boat.getPromoDescription(), boat.getCapacity(), boat.getBehaviourRules(), boat.getFishingEquipment(), boat.getPricePerDay(), boat.isCancalletionFree(), boat.getCancalletionFee(), addressDTO);
            dto.setGrade(boat.getGrade());
            boatDTOS.add(dto);
            Set<ImageDTO> dtoSet = new HashSet<>();
            for(Image i: boat.getImages()){
                ImageDTO postDto = modelMapper.map(i, ImageDTO.class);
                dtoSet.add(postDto);
            }
            dto.setImages(dtoSet);
        }


        return new ResponseEntity<>(boatDTOS, HttpStatus.OK);
    }


    @GetMapping("/findAllOrderByGrade")
    public ResponseEntity<List<BoatDTO>> findAllOrderByGrade(){
        List<Boat> boats = this.boatService.findAllOrderByGrade();
        List<BoatDTO> boatDTOS = new ArrayList<>();
        for(Boat boat: boats){
            addBoatDto(boatDTOS, boat);
        }
        return new ResponseEntity<>(boatDTOS, HttpStatus.OK);
    }

    private void addBoatDto(List<BoatDTO> boatDTOS, Boat boat) {
        BoatDTO dto = modelMapper.map(boat, BoatDTO.class);
        boatDTOS.add(dto);
    }


    @GetMapping("/findBoatsForHomePage")
    public ResponseEntity<List<HomeBoatSlideDTO>> findBoatsForHomePage(){
        List<Boat> boats = this.boatService.findAllOrderByGrade();
        List<BoatDTO> boatDTOS = new ArrayList<>();
        for(Boat boat: boats){
            addBoatDto(boatDTOS, boat);
        }

        List<HomeBoatSlideDTO> homeBoatSlideDTOS = getHomeBoatSlideDTOS(boatDTOS);

        return new ResponseEntity<>(homeBoatSlideDTOS, HttpStatus.OK);
    }

    private List<HomeBoatSlideDTO> getHomeBoatSlideDTOS(List<BoatDTO> boatDTOS) {
        List<HomeBoatSlideDTO> homeBoatSlideDTOS = new ArrayList<>();
        List<BoatDTO> boatDTOS1 = new ArrayList<>();
        int i = 1;
        for (BoatDTO dto : boatDTOS) {
            boatDTOS1.add(dto);
            if (i % 4 == 0) {
                HomeBoatSlideDTO homeBoatSlideDTO = new HomeBoatSlideDTO(boatDTOS1);
                homeBoatSlideDTOS.add(homeBoatSlideDTO);
                boatDTOS1 = new ArrayList<>();
            }
            i = i + 1;
        }

        if (boatDTOS1.size() != 0) {
            HomeBoatSlideDTO homeBoatSlideDTO = new HomeBoatSlideDTO(boatDTOS1);
            homeBoatSlideDTOS.add(homeBoatSlideDTO);
        }
        return homeBoatSlideDTOS;
    }

}
