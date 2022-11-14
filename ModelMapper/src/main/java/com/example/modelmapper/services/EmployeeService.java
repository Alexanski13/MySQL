package com.example.modelmapper.services;

import com.example.modelmapper.entities.Employee;
import com.example.modelmapper.entities.dtos.CreateEmployeeDto;
import com.example.modelmapper.entities.dtos.EmployeeDto;
import com.example.modelmapper.entities.dtos.EmployeeNamesDto;

import java.util.List;

public interface EmployeeService {
    Employee create(CreateEmployeeDto employeeDto);

    List<EmployeeDto> findAll();

    EmployeeNamesDto findNamesById(long id);


}
