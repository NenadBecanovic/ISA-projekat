package com.application.bekend.service;

import com.application.bekend.model.Address;
import com.application.bekend.repository.AddressRepository;
import org.springframework.stereotype.Service;

@Service
public class AddresService {

    private final AddressRepository addressRepository;

    public AddresService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public Address save(Address address){
        return this.addressRepository.save(address);
    }
}
