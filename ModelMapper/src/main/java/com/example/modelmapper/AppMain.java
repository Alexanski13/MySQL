package com.example.modelmapper;

import com.example.modelmapper.entities.Employee;
import com.example.modelmapper.services.EmployeeService;
import com.example.modelmapper.entities.dtos.addresses.CreateAddressDto;
import com.example.modelmapper.entities.dtos.CreateEmployeeDto;
import com.example.modelmapper.services.AddressService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Scanner;

//@Component
public class AppMain implements CommandLineRunner {

    private final AddressService addressService;
    private final EmployeeService employeeService;

    private final Gson gson;

    private final Scanner scanner;

    @Autowired
    public AppMain(AddressService addressService, EmployeeService employeeService, Gson gson, Scanner scanner) {
        this.addressService = addressService;
        this.employeeService = employeeService;
        this.gson = gson;
        this.scanner = scanner;
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);

//        createAddress(scanner);
//        createEmployee(scanner);
//        printAllEmployees();
//        printAllEmployeeNames();

        createAddress(scanner);
    }

    private void printAllEmployeeNames() {
        System.out.println(this.employeeService.findNamesById(1L));
    }

    private void printAllEmployees() {
        this.employeeService.findAll()
                .forEach(System.out::println);
    }

    private void createEmployee(Scanner scanner) {
        String firstName = scanner.nextLine();
        BigDecimal salary = new BigDecimal(scanner.nextLine());
        LocalDate birthday = LocalDate.parse(scanner.nextLine());
//        long addressId = Long.parseLong(scanner.nextLine());

        String country = scanner.nextLine();
        String city = scanner.nextLine();

        CreateAddressDto address = new CreateAddressDto(country, city);

        CreateEmployeeDto employeeDto = new CreateEmployeeDto(firstName, null, salary, birthday, address);

        Employee employee = this.employeeService.create(employeeDto);

        System.out.println(employee);

    }

    private void createAddress(Scanner scanner) {
        String input = this.scanner.nextLine();

        CreateAddressDto data = this.gson.fromJson(input, CreateAddressDto.class);

        CreateAddressDto created = addressService.create(data);

        System.out.println(this.gson.toJson(created));
    }
}
