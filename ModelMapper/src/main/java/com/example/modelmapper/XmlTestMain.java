package com.example.modelmapper;

import com.example.modelmapper.entities.dtos.addresses.AddressXMLDto;
import com.example.modelmapper.entities.dtos.addresses.CountryXMLDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

@Component
public class XmlTestMain implements CommandLineRunner {


    private final JAXBContext addressJAXBContext;

    private final JAXBContext countryContext;


    public XmlTestMain(@Qualifier("addressContext") JAXBContext addressJAXBContext,
                       @Qualifier("countryContext") JAXBContext countryContext) {
        this.addressJAXBContext = addressJAXBContext;
        this.countryContext = countryContext;
    }

    @Override
    public void run(String... args) throws Exception {
        CountryXMLDto countryXMLDto = new CountryXMLDto("Bulgaria");
        AddressXMLDto addressXMLDto = new AddressXMLDto(5, "Bulgaria", "Stara Zagora");

        JAXBContext context = JAXBContext.newInstance(AddressXMLDto.class);

        Marshaller countryMarshall = countryContext.createMarshaller();
        countryMarshall.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        countryMarshall.marshal(countryXMLDto, System.out);

        Marshaller addressMarshall = addressJAXBContext.createMarshaller();
        addressMarshall.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        addressMarshall.marshal(addressXMLDto, System.out);

//        Unmarshaller unmarshaller = context.createUnmarshaller();
//        AddressXMLDto unmarshal = (AddressXMLDto) unmarshaller.unmarshal(System.in);
//
//        System.out.println(unmarshal);
    }
}
