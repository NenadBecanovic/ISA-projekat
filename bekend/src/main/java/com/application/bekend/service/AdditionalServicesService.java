package com.application.bekend.service;

import com.application.bekend.model.AdditionalServices;
import com.application.bekend.repository.AdditionalServicesRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdditionalServicesService {

    private final AdditionalServicesRepository additionalServicesRepository;

    public AdditionalServicesService(AdditionalServicesRepository additionalServicesRepository) {
        this.additionalServicesRepository = additionalServicesRepository;
    }

    public List<AdditionalServices> getAllByHouseId(Long id) { return additionalServicesRepository.getAllByHouseId(id); };
}
