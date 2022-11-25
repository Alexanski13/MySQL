package workshop.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import workshop.models.employees.Employee;
import workshop.models.employees.EmployeeAboveCertainAge;
import workshop.models.employees.ImportEmployeesDTO;
import workshop.models.projects.FinalizedProjectDTO;
import workshop.models.projects.ImportProjectsDTO;
import workshop.models.projects.Project;
import workshop.repositories.EmployeeRepository;
import workshop.repositories.ProjectRepository;
import workshop.utils.ValidationUtil;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final ProjectRepository projectRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;


    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository, ProjectRepository projectRepository, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.employeeRepository = employeeRepository;
        this.projectRepository = projectRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    public String getXMLContent() throws IOException {
        Path path = Path.of("src", "main", "resources", "files", "xmls", "employees.xml");
        List<String> lines = Files.readAllLines(path);

        return String.join("\n", lines);
    }

    public void importEmployees() throws IOException, JAXBException {
        String xmlContent = this.getXMLContent();
        ByteArrayInputStream stream = new ByteArrayInputStream(xmlContent.getBytes());

        JAXBContext context = JAXBContext.newInstance(ImportEmployeesDTO.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        ImportEmployeesDTO employeesDTO = (ImportEmployeesDTO) unmarshaller.unmarshal(stream);

        List<Employee> employees = employeesDTO.getEmployeeDTOs().stream()
                .filter(importEmployeeDTO -> this.validationUtil.isValid(importEmployeeDTO) &&
                        this.projectRepository.findFirstByName(importEmployeeDTO.getProject().getName()).isPresent())
                .map(dto -> this.modelMapper.map(dto, Employee.class))
                .map(employee -> employee.builderWithProject(this.projectRepository
                        .findFirstByName(employee.getProject().getName()).get()))
                .collect(Collectors.toList());

        this.employeeRepository.saveAll(employees);
    }

    public boolean areImported() {
        return this.employeeRepository.count() > 0;
    }

    public String getEmployeesAboveAge() {
        return this.employeeRepository.findAllByAgeGreaterThan(25).orElseThrow(NoSuchElementException::new)
                .stream().map(employee -> this.modelMapper.map(employee, EmployeeAboveCertainAge.class))
                .map(EmployeeAboveCertainAge::toString)
                .collect(Collectors.joining(System.lineSeparator()));
    }

}
