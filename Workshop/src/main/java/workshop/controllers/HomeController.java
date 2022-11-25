package workshop.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import workshop.services.CompanyService;
import workshop.services.EmployeeService;
import workshop.services.ProjectService;

@Controller
public class HomeController {

    private final CompanyService companyService;
    private final ProjectService projectService;
    private final EmployeeService employeeService;

    public HomeController(CompanyService companyService, ProjectService projectService, EmployeeService employeeService) {
        this.companyService = companyService;
        this.projectService = projectService;
        this.employeeService = employeeService;
    }

    @GetMapping("/")
    public String index() {

        return "index";
    }

    @GetMapping("home")
    public String home(Model model) {
        boolean areImported = this.companyService.areImported() && this.employeeService.areImported() &&
                this.projectService.areImported();
        model.addAttribute("areImported", areImported);
        return "home";
    }
}
