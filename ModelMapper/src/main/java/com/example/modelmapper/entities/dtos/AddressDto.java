package com.example.modelmapper.entities.dtos;

public class AddressDto {

    private String country;

    private String city;

    public AddressDto(String country, String city) {
        this.country = country;
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

}
