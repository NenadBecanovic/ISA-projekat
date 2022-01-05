package com.application.bekend.controller;

import com.application.bekend.DTO.*;
import com.application.bekend.model.Boat;
import com.application.bekend.model.House;
import com.application.bekend.model.Image;
import org.modelmapper.ModelMapper;
import com.application.bekend.model.*;
import com.application.bekend.service.AdditionalServicesService;
import com.application.bekend.service.BoatService;
import com.application.bekend.service.NavigationEquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@RestController
@RequestMapping("api/boat")
public class BoatController {

    private final BoatService boatService;
    private final AdditionalServicesService additionalServicesService;
    private final NavigationEquipmentService navigationEquipmentService;

    @Autowired
    public BoatController(BoatService boatService, AdditionalServicesService additionalServicesService, NavigationEquipmentService navigationEquipmentService) {
        this.boatService = boatService;
        this.additionalServicesService = additionalServicesService;
        this.navigationEquipmentService = navigationEquipmentService;
    }
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/getBoatById/{id}")
    public ResponseEntity<BoatDTO> getBoatById(@PathVariable("id") Long id){
        Boat boat = this.boatService.getBoatById(id);

        AddressDTO addressDTO = new AddressDTO(boat.getAddress().getId(), boat.getAddress().getStreet(), boat.getAddress().getCity(),
                boat.getAddress().getState(), boat.getAddress().getLongitude(), boat.getAddress().getLatitude(), boat.getAddress().getPostalCode());
        NavigationEquipmentDTO navigationEquipmentDTO = new NavigationEquipmentDTO(boat.getNavigationEquipment().getId(), boat.getNavigationEquipment().isFishFinder(),
                boat.getNavigationEquipment().isRadar(), boat.getNavigationEquipment().isVhfradio(), boat.getNavigationEquipment().isGps());
        BoatDTO dto = new BoatDTO(boat.getId(), boat.getName(), boat.getType(), boat.getLength(), boat.getEngineNumber(), boat.getEnginePower(), boat.getMaxSpeed(),
                boat.getPromoDescription(), boat.getCapacity(), boat.getBehaviourRules(), boat.getFishingEquipment(), boat.getPricePerDay(), boat.isCancalletionFree(),
                boat.getCancalletionFee(), addressDTO, navigationEquipmentDTO);

        dto.setGrade(boat.getGrade());

        Set<ImageDTO> dtoSet = new HashSet<>();
        for(Image i: boat.getImages()){
            ImageDTO postDto = modelMapper.map(i, ImageDTO.class);
            dtoSet.add(postDto);
        }
        dto.setImages(dtoSet);

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<BoatDTO> save(@RequestBody BoatDTO dto) {
        Boat boat = this.boatService.getBoatById(dto.getId());
        Address address = boat.getAddress();
        Set<AdditionalServices> additionalServices = boat.getServices();

        for (AdditionalServices a: additionalServices)
        {
            for (AdditionalServicesDTO additionalServicesDTO: dto.getServices())
            {
                if(a.getId() == additionalServicesDTO.getId())
                {
                    a.setName(additionalServicesDTO.getName());
                    a.setPrice(additionalServicesDTO.getPrice());
                    this.additionalServicesService.save(a);
                }
            }
        }

        address.setStreet(dto.getAddress().getStreet());
        address.setCity(dto.getAddress().getCity());
        address.setState(dto.getAddress().getState());
        address.setLongitude(dto.getAddress().getLongitude());
        address.setLatitude(dto.getAddress().getLatitude());
        address.setPostalCode(dto.getAddress().getPostalCode());

        boat.setAddress(address);
        boat.setName(dto.getName());
        boat.setPromoDescription(dto.getPromoDescription());
        boat.setCapacity(dto.getCapacity());
        boat.setBehaviourRules(dto.getBehaviourRules());
        boat.setPricePerDay(dto.getPricePerDay());
        boat.setCancalletionFee(dto.getCancalletionFee());
        boat.setCancalletionFree(dto.isCancalletionFree());

        boat.setType(dto.getType());
        boat.setLength(dto.getLength());
        boat.setEngineNumber(dto.getEngineNumber());
        boat.setEnginePower(dto.getEnginePower());
        boat.setMaxSpeed(dto.getMaxSpeed());
        boat.setFishingEquipment(dto.getFishingEquipment());

        NavigationEquipment navigationEquipment = new NavigationEquipment(dto.getNavigationEquipmentDTO().getId(),
                dto.getNavigationEquipmentDTO().isGps(), dto.getNavigationEquipmentDTO().isRadar(), dto.getNavigationEquipmentDTO().isVhfradio(),
                dto.getNavigationEquipmentDTO().isFishFinder());
        boat.setNavigationEquipment(navigationEquipment);
        navigationEquipment.setBoat(boat);

        this.navigationEquipmentService.save(navigationEquipment);
        this.boatService.save(boat);
      
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<BoatDTO>> findAll(){
        List<Boat> boats = this.boatService.findAll();
        List<BoatDTO> boatDTOS = new ArrayList<>();

        for(Boat boat: boats){
            BoatDTO dto = modelMapper.map(boat, BoatDTO.class);
            boatDTOS.add(dto);
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
