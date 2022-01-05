package com.application.bekend.service;

import com.application.bekend.model.Boat;
import com.application.bekend.model.NavigationEquipment;
import com.application.bekend.repository.NavigationEquipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NavigationEquipmentService {

    private final NavigationEquipmentRepository navigationEquipmentRepository;

    @Autowired
    public NavigationEquipmentService(NavigationEquipmentRepository navigationEquipmentRepository) {
        this.navigationEquipmentRepository = navigationEquipmentRepository;
    }

    public NavigationEquipment save(NavigationEquipment navigationEquipment) {
        return this.navigationEquipmentRepository.save(navigationEquipment);
    }
}
