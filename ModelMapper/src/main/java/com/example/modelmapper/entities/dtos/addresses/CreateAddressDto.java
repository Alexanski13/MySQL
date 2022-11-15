package com.example.modelmapper.entities.dtos.addresses;

import com.google.gson.annotations.Expose;

public class CreateAddressDto {

    @Expose
    private String country;

    @Expose
    private String city;

    public CreateAddressDto() {
    }

    public CreateAddressDto(String country, String city) {
        this.country = country;
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
