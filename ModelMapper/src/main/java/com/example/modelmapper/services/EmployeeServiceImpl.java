package com.example.modelmapper.services;

import com.example.modelmapper.entities.Address;
import com.example.modelmapper.entities.Employee;
import com.example.modelmapper.entities.dtos.CreateEmployeeDto;
import com.example.modelmapper.entities.dtos.EmployeeDto;
import com.example.modelmapper.entities.dtos.EmployeeNamesDto;
import com.example.modelmapper.repositories.AddressRepository;
import com.example.modelmapper.repositories.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final AddressRepository addressRepository;
    private final EmployeeRepository employeeRepository;

    private final ModelMapper mapper;

    public EmployeeServiceImpl(AddressRepository addressRepository, EmployeeRepository employeeRepository, ModelMapper mapper) {
        this.addressRepository = addressRepository;
        this.employeeRepository = employeeRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public Employee create(CreateEmployeeDto employeeDto) {

        Employee employee = mapper.map(employeeDto, Employee.class);

        Optional<Address> address = this.addressRepository.findByCountryAndCity(employeeDto.getAddress().getCountry(),
                employeeDto.getAddress().getCity());

        address.ifPresent(employee::setAddress);

        return this.employeeRepository.save(employee);
    }

    @Override
    public List<EmployeeDto> findAll() {
      return this.employeeRepository.findAll()
                .stream().map(employee -> mapper.map(employee, EmployeeDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeNamesDto findNamesById(long id) {
        return this.employeeRepository.findNamesById(id);
    }
}
