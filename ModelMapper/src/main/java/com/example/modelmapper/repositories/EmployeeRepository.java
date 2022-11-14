package com.example.modelmapper.repositories;

import com.example.modelmapper.entities.Employee;
import com.example.modelmapper.entities.dtos.EmployeeNamesDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query("SELECT new com.example.modelmapper.entities.dtos.EmployeeNamesDto(e.firstName, e.lastName) FROM Employee AS e WHERE e.id = :id")
    EmployeeNamesDto findNamesById(long id);
}
