package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.MechanicImportDTO;
import softuni.exam.models.dto.PartImportDTO;
import softuni.exam.models.entity.Mechanic;
import softuni.exam.models.entity.Part;
import softuni.exam.repository.MechanicRepository;
import softuni.exam.service.MechanicService;
import softuni.exam.util.ValidationUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import static softuni.exam.constants.Messages.*;

@Service
public class MechanicServiceImpl implements MechanicService {

    private final MechanicRepository mechanicRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidationUtils validationUtils;

    public MechanicServiceImpl(MechanicRepository mechanicRepository, ModelMapper modelMapper, Gson gson, ValidationUtils validationUtils) {
        this.mechanicRepository = mechanicRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtils = validationUtils;
    }

    @Override
    public boolean areImported() {
        return this.mechanicRepository.count() > 0;
    }

    @Override
    public String readMechanicsFromFile() throws IOException {
        return Files.readString(Path.of("src/main/resources/files/json/mechanics.json"));
    }

    @Override
    public String importMechanics() throws IOException {
        final StringBuilder stringBuilder = new StringBuilder();

        final List<Mechanic> mechanics = Arrays.stream(gson.fromJson(readMechanicsFromFile(), MechanicImportDTO[].class))
                .filter(mechanicImportDTO -> {
                    boolean isValid = this.validationUtils.isValid(mechanicImportDTO);

                    if (this.mechanicRepository.findFirstByEmail(mechanicImportDTO.getEmail()).isPresent()) {
                        isValid = false;
                    }

                    if (isValid) {
                        stringBuilder.append(String.format(VALID_MECHANIC_FORMAT,
                                mechanicImportDTO.getFirstName(),
                                mechanicImportDTO.getLastName()));
                        this.mechanicRepository.saveAndFlush(this.modelMapper.map(mechanicImportDTO, Mechanic.class));

                    } else {
                        stringBuilder.append(INVALID_MECHANIC);
                    }

                    return isValid;
                })
                .map(mechanicImportDTO -> this.modelMapper.map(mechanicImportDTO, Mechanic.class))
                .toList();

        return stringBuilder.toString();
    }
}
