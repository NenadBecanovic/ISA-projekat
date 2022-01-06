package com.application.bekend.service;

import com.application.bekend.DTO.AddressDTO;
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

    public Address getAddressById(Long id) { return this.addressRepository.getAddressById(id); }

    public Address updateAddress(AddressDTO addressDTO){
        Address address = this.addressRepository.getAddressById(addressDTO.getId());
        address.setCity(addressDTO.getCity());
        address.setState(addressDTO.getState());
        address.setStreet(addressDTO.getStreet());
        address.setLatitude(addressDTO.getLatitude());
        address.setLongitude(addressDTO.getLongitude());
        address.setPostalCode(addressDTO.getPostalCode());
        this.addressRepository.save(address);
        return address;
    }
}
