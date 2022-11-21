package com.example.modelmapper.entities.dtos.addresses;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class AddressXMLDto {

    @XmlElement
    private int id;

    @XmlElement
    private String country;

    @XmlElement
    private String city;

    public AddressXMLDto() {
    }

    public AddressXMLDto(int id, String country, String city) {
        this.id = id;
        this.country = country;
        this.city = city;
    }

    @Override
    public String toString() {
        return "AddressXMLDto{" +
                "id=" + id +
                ", country=" + country +
                ", city='" + city + '\'' +
                '}';
    }
}
