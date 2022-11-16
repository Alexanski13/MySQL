package jsonexercise.services.user;

import domain.dtos.users.UsersWithProductWrapperDto;
import domain.dtos.users.UsersWithSoldProductsDto;

import java.io.IOException;
import java.util.List;

public interface UserService {

    List<UsersWithSoldProductsDto> findAllBySellingProductsBuyerIsNotNullOrderBySellingProductsBuyerFirstName() throws IOException;

    UsersWithProductWrapperDto usersAndProducts() throws IOException;
}
