package com.application.bekend.controller;

import com.application.bekend.DTO.*;
import com.application.bekend.model.*;
import com.application.bekend.service.AdditionalServicesService;
import com.application.bekend.service.BoatService;
import com.application.bekend.service.NavigationEquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

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
}
