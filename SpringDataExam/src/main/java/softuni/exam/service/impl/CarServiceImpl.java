package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.CarImportDTO;
import softuni.exam.models.dto.CarImportWrapperDTO;
import softuni.exam.models.entity.Car;
import softuni.exam.repository.CarRepository;
import softuni.exam.service.CarService;
import softuni.exam.util.ValidationUtils;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static softuni.exam.constants.Messages.*;

@Service
public class CarServiceImpl implements CarService {

    public static final Path CARS_PATH = Path.of("src/main/resources/files/xml/cars.xml");

    private final CarRepository carRepository;

    private final ModelMapper modelMapper;

    private final XmlParser xmlParser;

    private final ValidationUtils validationUtils;

    public CarServiceImpl(CarRepository carRepository, ModelMapper modelMapper, XmlParser xmlParser, ValidationUtils validationUtils) {
        this.carRepository = carRepository;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
        this.validationUtils = validationUtils;
    }

    @Override
    public boolean areImported() {
        return this.carRepository.count() > 0;
    }

    @Override
    public String readCarsFromFile() throws IOException {
        return Files.readString(CARS_PATH);
    }

    @Override
    public String importCars() throws IOException, JAXBException {
        final StringBuilder stringBuilder = new StringBuilder();

        final File file = CARS_PATH.toFile();

        final CarImportWrapperDTO carImportWrapperDTO =
                xmlParser.fromFile(file, CarImportWrapperDTO.class);

        final List<CarImportDTO> cars = carImportWrapperDTO.getCars();

        for (CarImportDTO car : cars) {
            boolean isValid = validationUtils.isValid(car);

            if (this.carRepository.findFirstByPlateNumber(car.getPlateNumber()).isPresent()) {
                isValid = false;
            }

            if (isValid) {
                stringBuilder.append(String.format(VALID_CAR_FORMAT,
                        car.getCarMake(),
                        car.getCarModel()));
                this.carRepository.saveAndFlush(this.modelMapper.map(car, Car.class));
            } else {
                stringBuilder.append(INVALID_CAR).append(System.lineSeparator());
            }
        }

        return stringBuilder.toString();
    }
}
