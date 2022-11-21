package com.example.modelmapper.entities.dtos.addresses;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "country")
@XmlAccessorType(XmlAccessType.FIELD)
public class CountryXMLDto {

    @XmlAttribute
    private String value;

    public CountryXMLDto() {
    }

    public CountryXMLDto(String value) {
        this.value = value;
    }
}
