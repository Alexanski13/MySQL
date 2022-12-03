package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.TaskImportDTO;
import softuni.exam.models.dto.TaskImportWrapperDTO;
import softuni.exam.models.entity.Car;
import softuni.exam.models.entity.Mechanic;
import softuni.exam.models.entity.Part;
import softuni.exam.models.entity.Task;
import softuni.exam.models.entity.enums.CarType;
import softuni.exam.repository.CarRepository;
import softuni.exam.repository.MechanicRepository;
import softuni.exam.repository.PartRepository;
import softuni.exam.repository.TaskRepository;
import softuni.exam.service.TaskService;
import softuni.exam.util.ValidationUtils;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.NoSuchElementException;
import java.util.Set;

import static softuni.exam.constants.Messages.INVALID_TASK;
import static softuni.exam.constants.Messages.VALID_TASK_FORMAT;

@Service
public class TaskServiceImpl implements TaskService {

    public static final Path TASKS_PATH = Path.of("src/main/resources/files/xml/tasks.xml");

    private final CarType carType = CarType.coupe;

    private final String EXPORT_FORMAT = "Car %s %s with %dkm\n"
            + "-Mechanic: %s %s - task №%d:\n"
            + "--Engine: %.1f\n"
            + "---Price: %.2f$";

    private final TaskRepository taskRepository;

    private final MechanicRepository mechanicRepository;

    private final PartRepository partRepository;

    private final CarRepository carRepository;

    private final ModelMapper modelMapper;

    private final XmlParser xmlParser;

    private final ValidationUtils validationUtils;

    public TaskServiceImpl(TaskRepository taskRepository, MechanicRepository mechanicRepository,
                           PartRepository partRepository, CarRepository carRepository,
                           ModelMapper modelMapper, XmlParser xmlParser, ValidationUtils validationUtils) {
        this.taskRepository = taskRepository;
        this.mechanicRepository = mechanicRepository;
        this.partRepository = partRepository;
        this.carRepository = carRepository;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
        this.validationUtils = validationUtils;
    }

    @Override
    public boolean areImported() {
        return this.taskRepository.count() > 0;
    }

    @Override
    public String readTasksFileContent() throws IOException {
        return Files.readString(TASKS_PATH);
    }

    @Override
    public String importTasks() throws IOException, JAXBException {
        final StringBuilder stringBuilder = new StringBuilder();

        final File file = TASKS_PATH.toFile();

        final TaskImportWrapperDTO taskImportWrapperDTO =
                xmlParser.fromFile(file, TaskImportWrapperDTO.class);


        for (TaskImportDTO task : taskImportWrapperDTO.getTasks()) {
            boolean isValid = this.validationUtils.isValid(task);

            if (isValid) {
                if (this.mechanicRepository.findByFirstName(task.getMechanic().getFirstName()).isPresent() &&
                        this.carRepository.findById(task.getCar().getId()).isPresent() &&
                        this.partRepository.findById(task.getPart().getId()).isPresent()) {

                    Mechanic mechanic = this.mechanicRepository.findByFirstName(task.getMechanic().getFirstName()).get();
                    Car car = this.carRepository.findById(task.getCar().getId()).get();
                    Part part = this.partRepository.findById(task.getPart().getId()).get();

                    Task taskToSave = this.modelMapper.map(task, Task.class);

                    taskToSave.setMechanic(mechanic);
                    taskToSave.setCar(car);
                    taskToSave.setPart(part);

                    taskToSave.setDate(LocalDateTime.parse(task.getDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                    this.taskRepository.saveAndFlush(taskToSave);

                    stringBuilder.append(String.format(VALID_TASK_FORMAT, taskToSave.getPrice()));
                } else {
                    stringBuilder.append(INVALID_TASK);
                }
            } else {
                stringBuilder.append(INVALID_TASK);
            }
            stringBuilder.append(System.lineSeparator());
        }
        return stringBuilder.toString();
    }

    @Override
    public String getCoupeCarTasksOrderByPrice() {
        Set<Task> tasks = this.taskRepository
                .findAllByCar_CarTypeOrderByPriceDesc(carType)
                .orElseThrow(NoSuchElementException::new);

        StringBuilder stringBuilder = new StringBuilder();

        tasks.forEach(task -> {
            stringBuilder.append(String.format(EXPORT_FORMAT,
                            task.getCar().getCarMake(),
                            task.getCar().getCarModel(),
                            task.getCar().getKilometers(),
                            task.getMechanic().getFirstName(),
                            task.getMechanic().getLastName(),
                            task.getId(),
                            task.getCar().getEngine(),
                            task.getPrice()))
                    .append(System.lineSeparator());
        });

        return stringBuilder.toString();
    }
}
