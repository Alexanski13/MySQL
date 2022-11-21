package com.example.modelmapper.config;

import com.example.modelmapper.entities.dtos.addresses.AddressXMLDto;
import com.example.modelmapper.entities.dtos.addresses.CountryXMLDto;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.util.Scanner;

@Configuration
public class Config {

    @Bean
    public ModelMapper createModelMapper() {
        return new ModelMapper();
    }

    @Bean
    public Scanner createScanner() {
        return new Scanner(System.in);
    }

    @Bean
    public Gson createGson() {
        return new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();
    }

    @Bean("addressContext")
    @Primary
    public JAXBContext createAddressContext() throws JAXBException {
        return JAXBContext.newInstance(AddressXMLDto.class);
    }

    @Bean("countryContext")
    public JAXBContext createCountryContext() throws JAXBException {
        return JAXBContext.newInstance(CountryXMLDto.class);
    }
}
