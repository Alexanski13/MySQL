package com.example.modelmapper.entities.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CreateEmployeeDto {

    private String firstName;

    private String lastName;

    private BigDecimal salary;

    private LocalDate birthday;

    private AddressDto address;

    public CreateEmployeeDto(String firstName, String lastName, BigDecimal salary, LocalDate birthday, AddressDto address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
        this.birthday = birthday;
        this.address = address;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public AddressDto getAddress() {
        return address;
    }
}
