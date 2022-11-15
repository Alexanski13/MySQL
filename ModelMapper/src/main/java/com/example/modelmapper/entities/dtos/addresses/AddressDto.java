package com.example.modelmapper.entities.dtos.addresses;

import com.google.gson.annotations.Expose;

public class AddressDto extends CreateAddressDto{

    @Expose
    private long id;

    public AddressDto() {
    }

    public AddressDto(String country, String city) {
        super(country, city);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
