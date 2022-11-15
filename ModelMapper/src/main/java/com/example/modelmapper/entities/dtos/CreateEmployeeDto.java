package com.example.modelmapper.entities.dtos;

import com.example.modelmapper.entities.dtos.addresses.CreateAddressDto;
import com.google.gson.annotations.Expose;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CreateEmployeeDto {

    @Expose
    private String firstName;

    @Expose
    private String lastName;

    @Expose
    private BigDecimal salary;

//    @Expose
    private LocalDate birthday;

    @Expose
    private CreateAddressDto address;

    public CreateEmployeeDto(String firstName, String lastName, BigDecimal salary, LocalDate birthday, CreateAddressDto address) {
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

    public CreateAddressDto getAddress() {
        return address;
    }
}
