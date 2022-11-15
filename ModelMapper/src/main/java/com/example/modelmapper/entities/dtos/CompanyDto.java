package com.example.modelmapper.entities.dtos;

import com.google.gson.annotations.Expose;

import java.util.List;

public class CompanyDto {

    @Expose
    private String name;

    @Expose
    private List<CreateEmployeeDto> employees;

    public CompanyDto(String name, List<CreateEmployeeDto> employees) {
        this.name = name;
        this.employees = employees;
    }
}
