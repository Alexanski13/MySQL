package com.example.modelmapper.services;

import com.example.modelmapper.entities.Address;
import com.example.modelmapper.entities.dtos.addresses.AddressDto;
import com.example.modelmapper.entities.dtos.addresses.CreateAddressDto;
import com.example.modelmapper.repositories.AddressRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;

    private final ModelMapper mapper;

    @Autowired
    public AddressServiceImpl(AddressRepository addressRepository, ModelMapper mapper) {
        this.addressRepository = addressRepository;
        this.mapper = mapper;
    }

    @Override
    public AddressDto create(CreateAddressDto data) {

        Address address = mapper.map(data, Address.class);

        Address save = this.addressRepository.save(address);
        return this.mapper.map(save, AddressDto.class);
    }
}
