package jsonexercise.domain.dtos.users;

import jsonexercise.domain.dtos.products.ProductSoldDto;
import jsonexercise.domain.dtos.products.wrappers.ProductSoldWrapperDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsersWithSoldProductsDto {

    private String firstName;

    private String lastName;

    private List<ProductSoldDto> boughtProducts;

    public static List<UserWithSoldProductsXmlDto> toUsersWithSoldProductsDto(List<UsersWithSoldProductsDto> input) {
        return input.stream()
                .map(user -> new UserWithSoldProductsXmlDto(user.getFirstName(),
                        user.getLastName(),
                        new ProductSoldWrapperDto(user.getBoughtProducts())))
                .toList();
    }
}


