package com.example.modelmapper.services;

import com.example.modelmapper.entities.dtos.addresses.AddressDto;
import com.example.modelmapper.entities.dtos.addresses.CreateAddressDto;

public interface AddressService {
    AddressDto create(CreateAddressDto data);
}
