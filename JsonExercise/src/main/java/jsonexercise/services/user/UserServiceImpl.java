package jsonexercise.services.user;

import domain.dtos.users.UserDto;
import domain.dtos.users.UserWithProductsDto;
import domain.dtos.users.UsersWithProductWrapperDto;
import domain.dtos.users.UsersWithSoldProductsDto;
import jsonexercise.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static jsonexercise.constant.Paths.USERS_AND_PRODUCTS_JSON_PATH;
import static jsonexercise.constant.Paths.USERS_SOLD_PRODUCTS_JSON_PATH;
import static jsonexercise.constant.Utils.MODEL_MAPPER;
import static jsonexercise.constant.Utils.writeJsonIntoFile;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UsersWithSoldProductsDto> findAllBySellingProductsBuyerIsNotNullOrderBySellingProductsBuyerFirstName() throws IOException {
        final List<UsersWithSoldProductsDto> usersWithSoldProductsDtoList = this.userRepository.findAllBySellingProductsBuyerIsNotNullOrderBySellingProductsBuyerFirstName()
                .orElseThrow(NoSuchElementException::new)
                .stream().map(user -> MODEL_MAPPER.map(user, UsersWithSoldProductsDto.class))
                .collect(Collectors.toList());

        writeJsonIntoFile(usersWithSoldProductsDtoList, USERS_SOLD_PRODUCTS_JSON_PATH);


        return usersWithSoldProductsDtoList;
    }

    @Override
    public UsersWithProductWrapperDto usersAndProducts() throws IOException {

        final List<UserWithProductsDto> usersAndProducts = this.userRepository.findAllBySellingProductsBuyerIsNotNullOrderBySellingProductsBuyerFirstName()
                .orElseThrow(NoSuchElementException::new)
                .stream()
                .map(user -> MODEL_MAPPER.map(user, UserDto.class))
                .map(UserDto::toUserWithProductsDto)
                .collect(Collectors.toList());

        final UsersWithProductWrapperDto usersWithProductWrapperDto = new UsersWithProductWrapperDto(usersAndProducts);

        writeJsonIntoFile(usersWithProductWrapperDto, USERS_AND_PRODUCTS_JSON_PATH);


        return usersWithProductWrapperDto;
    }
}
