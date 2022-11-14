package com.example.modelmapper.services;

import com.example.modelmapper.entities.Address;
import com.example.modelmapper.entities.dtos.AddressDto;

public interface AddressService {
    Address create(AddressDto data);
}
