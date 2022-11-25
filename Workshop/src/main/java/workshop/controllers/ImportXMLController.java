package workshop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import workshop.services.CompanyService;
import workshop.services.EmployeeService;
import workshop.services.ProjectService;

import javax.xml.bind.JAXBException;
import java.io.IOException;

@Controller
public class ImportXMLController {

    private final CompanyService companyService;
    private final ProjectService projectService;
    private final EmployeeService employeeService;

    @Autowired
    public ImportXMLController(CompanyService companyService, ProjectService projectService, EmployeeService employeeService) {
        this.companyService = companyService;
        this.projectService = projectService;
        this.employeeService = employeeService;
    }

    @GetMapping("/import/xml")
    public String index(Model model){
        boolean companiesImported = this.companyService.areImported();
        boolean projectsImported = this.projectService.areImported();
        boolean employeesImported = this.employeeService.areImported();

        boolean[] importStatuses = {companiesImported, projectsImported, employeesImported};

        model.addAttribute("areImported", importStatuses);

        return "xml/import-xml";
    }

    @GetMapping("/import/companies")
    public String viewImportCompanies(Model model) throws IOException {
        String companiesXML = this.companyService.getXMLContent();
        model.addAttribute("companies", companiesXML);

        return "xml/import-companies";
    }

    @PostMapping("/import/companies")
    public String importCompanies() throws JAXBException, IOException {
        this.companyService.importCompanies();

        return "redirect:/import/xml";
    }

    @GetMapping("/import/projects")
    public String viewImportProjects(Model model) throws IOException {
        String projectsXML = this.projectService.getXMLContent();

        model.addAttribute("projects", projectsXML);

        return "xml/import-projects";
    }

    @PostMapping("/import/projects")
    public String importProjects() throws IOException, JAXBException {
        this.projectService.importProjects();

        return "redirect:/import/xml";
    }

    @GetMapping("/import/employees")
    public String viewImportEmployees(Model model) throws IOException {
        String employeeXML = this.employeeService.getXMLContent();

        model.addAttribute("employees", employeeXML);

        return "xml/import-employees";
    }

    @PostMapping("/import/employees")
    public String importEmployees() throws IOException, JAXBException {
        this.employeeService.importEmployees();

        return "redirect:/import/xml";
    }
}
