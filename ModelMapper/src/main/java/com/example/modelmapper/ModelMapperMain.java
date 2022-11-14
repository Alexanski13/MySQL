package com.example.modelmapper;

import com.example.modelmapper.entities.Address;
import com.example.modelmapper.entities.Employee;
import com.example.modelmapper.entities.dtos.EmployeeDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.boot.CommandLineRunner;

import java.math.BigDecimal;

//@Component
public class ModelMapperMain implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {

        ModelMapper mapper = new ModelMapper();

//        PropertyMap<Employee, EmployeeDto> propertyMap = new PropertyMap<>() {
//            @Override
//            protected void configure() {
//                map().setAddressCity(source.getAddress().getCity());
//            }
//        };
//
//        mapper.addMappings(propertyMap);

        TypeMap<Employee, EmployeeDto> typeMap = mapper.createTypeMap(Employee.class, EmployeeDto.class);
        typeMap.addMappings(mapping -> mapping.map(source -> source.getAddress().getCity(),
                EmployeeDto::setAddressCity));
        typeMap.validate();

        Address address = new Address("Bulgaria", "Sofia");
        Employee employee = new Employee("First", BigDecimal.TEN, address);

        EmployeeDto employeeDto = typeMap.map(employee);

        System.out.println(employeeDto);
    }
}
