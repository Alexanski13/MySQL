package workshop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import workshop.services.EmployeeService;
import workshop.services.ProjectService;

@Controller
public class ExportController {

    private final ProjectService projectService;
    private final EmployeeService employeeService;

    @Autowired
    public ExportController(ProjectService projectService, EmployeeService employeeService) {
        this.projectService = projectService;
        this.employeeService = employeeService;
    }

    @GetMapping("export/project-if-finished")
    public String exportFinalizedProjects(Model model) {
        String finalizedProjects = this.projectService.getFinalizedProjects();

        model.addAttribute("projectsIfFinished", finalizedProjects);

        return "export/export-project-if-finished";
    }

    @GetMapping("export/employees-above")
    public String exportEmployeesAbove25(Model model) {
        String employeesAboveAge = this.employeeService.getEmployeesAboveAge();

        model.addAttribute("employeesAbove", employeesAboveAge);

        return "export/export-employees-with-age";
    }
}
