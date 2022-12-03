package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.PartImportDTO;
import softuni.exam.models.entity.Part;
import softuni.exam.repository.PartRepository;
import softuni.exam.service.PartService;
import softuni.exam.util.ValidationUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import static softuni.exam.constants.Messages.*;

@Service
public class PartServiceImpl implements PartService {

    private final PartRepository partRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;

    private final ValidationUtils validationUtils;

    public PartServiceImpl(PartRepository partRepository, ModelMapper modelMapper, Gson gson, ValidationUtils validationUtils) {
        this.partRepository = partRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtils = validationUtils;
    }

    @Override
    public boolean areImported() {
        return this.partRepository.count() > 0;
    }

    @Override
    public String readPartsFileContent() throws IOException {
        return Files.readString(Path.of("src/main/resources/files/json/parts.json"));
    }

    @Override
    public String importParts() throws IOException {
        final StringBuilder stringBuilder = new StringBuilder();

        final List<Part> parts = Arrays.stream(gson.fromJson(readPartsFileContent(), PartImportDTO[].class))
                .filter(partImportDTO -> {
                    boolean isValid = this.validationUtils.isValid(partImportDTO);

                    if (this.partRepository.findFirstByPartName(partImportDTO.getPartName()).isPresent()) {
                        isValid = false;
                    }

                    if (isValid) {
                        stringBuilder.append(String.format(VALID_PART_FORMAT,
                                partImportDTO.getPartName(),
                                partImportDTO.getPrice()));
                        this.partRepository.saveAndFlush(this.modelMapper.map(partImportDTO, Part.class));

                    } else {
                        stringBuilder.append(INVALID_PART);
                    }

                    return isValid;
                })
                .map(partImportDTO -> this.modelMapper.map(partImportDTO, Part.class))
                .toList();

        return stringBuilder.toString();
    }
}
