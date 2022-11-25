package workshop.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import workshop.models.projects.FinalizedProjectDTO;
import workshop.models.projects.ImportProjectsDTO;
import workshop.models.projects.Project;
import workshop.repositories.CompanyRepository;
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
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final CompanyRepository companyRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository, ModelMapper modelMapper, ValidationUtil validationUtil, CompanyRepository companyRepository) {
        this.projectRepository = projectRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.companyRepository = companyRepository;
    }

    public String getXMLContent() throws IOException {
        Path path = Path.of("src", "main", "resources", "files", "xmls", "projects.xml");
        List<String> lines = Files.readAllLines(path);

        return String.join("\n", lines);
    }

    public void importProjects() throws IOException, JAXBException {
        String xmlContent = this.getXMLContent();
        ByteArrayInputStream stream = new ByteArrayInputStream(xmlContent.getBytes());

        JAXBContext context = JAXBContext.newInstance(ImportProjectsDTO.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        ImportProjectsDTO projects = (ImportProjectsDTO) unmarshaller.unmarshal(stream);

        List<Project> entities = projects.getProjects().stream()
                .filter(this.validationUtil::isValid)
                .map(dto -> this.modelMapper.map(dto, Project.class))
                .filter(project -> this.companyRepository.findFirstByName(project.getCompany().getName()).isPresent())
                .map(project -> new Project(project.getName(), project.getDescription(), project.isFinished(), project.getPayment(),
                        project.getStartDate(), this.companyRepository.findFirstByName(project.getCompany().getName()).get()))
                .collect(Collectors.toList());

        this.projectRepository.saveAll(entities);
    }

    public boolean areImported() {
        return this.projectRepository.count() > 0;
    }

    public String getFinalizedProjects() {
        return this.projectRepository.findAllByIsFinished(true).orElseThrow(NoSuchElementException::new)
                .stream().map(project -> this.modelMapper.map(project, FinalizedProjectDTO.class))
                .map(FinalizedProjectDTO::toString)
                .collect(Collectors.joining(System.lineSeparator()));
    }
}
