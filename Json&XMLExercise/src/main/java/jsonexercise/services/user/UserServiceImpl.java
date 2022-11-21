package jsonexercise.services.user;

import jsonexercise.domain.dtos.users.UserDto;
import jsonexercise.domain.dtos.users.UserWithProductsDto;
import jsonexercise.domain.dtos.users.wrappers.UsersWithProductWrapperDto;
import jsonexercise.domain.dtos.users.UsersWithSoldProductsDto;
import jsonexercise.domain.dtos.users.wrappers.UsersWithSoldProductsWrapperDto;
import jsonexercise.repositories.UserRepository;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static jsonexercise.constant.Paths.*;
import static jsonexercise.constant.Utils.*;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UsersWithSoldProductsDto> findAllBySellingProductsBuyerIsNotNullOrderBySellingProductsBuyerFirstName() throws IOException, JAXBException {
        final List<UsersWithSoldProductsDto> usersWithSoldProductsDtoList = this.userRepository
                .findAllBySellingProductsBuyerIsNotNullOrderBySellingProductsBuyerFirstName()
                .orElseThrow(NoSuchElementException::new)
                .stream().map(user -> MODEL_MAPPER.map(user, UsersWithSoldProductsDto.class))
                .collect(Collectors.toList());

        final UsersWithSoldProductsWrapperDto usersWithSoldProductsWrapperDto =
                new UsersWithSoldProductsWrapperDto().ofListOfUsersWithSoldProductsDto(usersWithSoldProductsDtoList);


        writeJsonIntoFile(usersWithSoldProductsDtoList, USERS_SOLD_PRODUCTS_JSON_PATH);
        writeXmlIntoFile(usersWithSoldProductsWrapperDto, USERS_SOLD_PRODUCTS_XML_PATH);


        return usersWithSoldProductsDtoList;
    }

    @Override
    public UsersWithProductWrapperDto usersAndProducts() throws IOException, JAXBException {

        final List<UserWithProductsDto> usersAndProducts = this.userRepository
                .findAllBySellingProductsBuyerIsNotNullOrderBySellingProductsBuyerFirstName()
                .orElseThrow(NoSuchElementException::new)
                .stream()
                .map(user -> MODEL_MAPPER.map(user, UserDto.class))
                .map(UserDto::toUserWithProductsDto)
                .collect(Collectors.toList());

        final UsersWithProductWrapperDto usersWithProductWrapperDto = new UsersWithProductWrapperDto(usersAndProducts);

        writeJsonIntoFile(usersWithProductWrapperDto, USERS_AND_PRODUCTS_JSON_PATH);
        writeXmlIntoFile(usersWithProductWrapperDto, USERS_AND_PRODUCTS_XML_PATH);


        return usersWithProductWrapperDto;
    }
}
