package com.example.modelmapper;

import com.example.modelmapper.entities.dtos.addresses.CreateAddressDto;
import com.example.modelmapper.entities.dtos.CompanyDto;
import com.example.modelmapper.entities.dtos.CreateEmployeeDto;
import com.google.gson.*;
import org.springframework.boot.CommandLineRunner;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

//@Component
public class JsonTestMain implements CommandLineRunner {

    private final Scanner scanner;
    private final Gson gson;

    public JsonTestMain(Scanner scanner, Gson gson) {
        this.scanner = scanner;
        this.gson = new GsonBuilder()
//                .excludeFieldsWithoutExposeAnnotation()
                .setDateFormat("YYYY-MM-DD")
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .setPrettyPrinting()
                .create();
    }

    static class LocalDateAdapter implements JsonSerializer<LocalDate> {
        public JsonElement serialize(LocalDate date, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(date.format(DateTimeFormatter.ISO_LOCAL_DATE));
        }
    }

    @Override
    public void run(String... args) throws Exception {
        CreateAddressDto address1 = new CreateAddressDto("Bulgaria", "Burgas");
        CreateEmployeeDto employee1 = new CreateEmployeeDto("George", "Ignatov", BigDecimal.ONE,
                LocalDate.now(), address1);

        CreateAddressDto address2 = new CreateAddressDto("Bulgaria", "Kaspichan");
        CreateEmployeeDto employee2 = new CreateEmployeeDto("John", "Voltra", BigDecimal.TEN,
                LocalDate.now(), address2);

        CreateAddressDto address3 = new CreateAddressDto("Bulgaria", "Sungurlare");
        CreateEmployeeDto employee3 = new CreateEmployeeDto("Rumen", "Rumenov", BigDecimal.TEN,
                LocalDate.now(), address3);

        CompanyDto sega = new CompanyDto("Sega", List.of(employee1, employee2, employee3));

        System.out.println(this.gson.toJson(sega));

    }

    private void test1() {
        CreateAddressDto address1 = new CreateAddressDto("Bulgaria", "Burgas");
        CreateAddressDto address2 = new CreateAddressDto("Bulgaria", "Kaspichan");
        CreateAddressDto address3 = new CreateAddressDto("Bulgaria", "Sungurlare");

        CreateEmployeeDto createEmployeeDto = new CreateEmployeeDto("George", "Ignatov", BigDecimal.ONE,
                LocalDate.now(), address1);

        String json = gson.toJson(createEmployeeDto);

        List<CreateAddressDto> addressList = List.of(address1, address2, address3);

        System.out.println(gson.toJson(addressList));

        System.out.println(json);

        String input = this.scanner.nextLine();

//        CreateEmployeeDto parsedDto = gson.fromJson(input, CreateEmployeeDto.class);

        CreateAddressDto[] list = gson.fromJson(input, CreateAddressDto[].class);

        System.out.println();
    }
}
