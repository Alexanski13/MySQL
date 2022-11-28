package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.CountryImportDto;
import softuni.exam.models.entity.Country;
import softuni.exam.repository.CountryRepository;
import softuni.exam.service.CountryService;
import softuni.exam.util.ValidationUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

import static softuni.exam.constants.Messages.INVALID_COUNTRY;
import static softuni.exam.constants.Messages.VALID_COUNTRY_FORMAT;
import static softuni.exam.constants.Paths.COUNTRIES_PATH;

@Service
public class CountryServiceImpl implements CountryService {

    private final CountryRepository countryRepository;

    private final Gson gson;

    private final ValidationUtils validationUtils;

    private final ModelMapper modelMapper;

    @Autowired
    public CountryServiceImpl(CountryRepository countryRepository,
                              Gson gson,
                              ValidationUtils validationUtils,
                              ModelMapper modelMapper) {
        this.countryRepository = countryRepository;
        this.gson = gson;
        this.validationUtils = validationUtils;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean areImported() {
        return this.countryRepository.count() > 0;
    }

    @Override
    public String readCountriesFromFile() throws IOException {
        return Files.readString(COUNTRIES_PATH);
    }

    @Override
    public String importCountries() throws IOException {
        final StringBuilder sb = new StringBuilder();

        final List<Country> countries = Arrays.stream(gson.fromJson(readCountriesFromFile(), CountryImportDto[].class))
                .filter(countryImportDto -> {
                    boolean isValid = this.validationUtils.isValid(countryImportDto);

                    if (this.countryRepository.findFirstByCountryName(countryImportDto.getCountryName()).isPresent()) {
                        isValid = false;
                    }

                    if (isValid) {
                        sb.append(String.format(VALID_COUNTRY_FORMAT,
                                countryImportDto.getCountryName(),
                                countryImportDto.getCurrency()));
                        this.countryRepository.saveAndFlush(this.modelMapper.map(countryImportDto, Country.class));

                    } else {
                        sb.append(INVALID_COUNTRY);
                    }

                    return isValid;
                })
                .map(countryImportDto -> this.modelMapper.map(countryImportDto, Country.class))
                .toList();

        return sb.toString();
    }
}
