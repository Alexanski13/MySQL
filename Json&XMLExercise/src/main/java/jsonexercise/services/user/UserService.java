package jsonexercise.services.user;

import jsonexercise.domain.dtos.users.UserWithProductsDto;
import jsonexercise.domain.dtos.users.wrappers.UsersWithProductWrapperDto;
import jsonexercise.domain.dtos.users.UsersWithSoldProductsDto;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.List;

public interface UserService {

    List<UsersWithSoldProductsDto> findAllBySellingProductsBuyerIsNotNullOrderBySellingProductsBuyerFirstName() throws IOException, JAXBException;

    UsersWithProductWrapperDto usersAndProducts() throws IOException, JAXBException;
}
