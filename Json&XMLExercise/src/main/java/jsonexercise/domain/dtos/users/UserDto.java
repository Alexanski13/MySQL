package jsonexercise.domain.dtos.users;


import jsonexercise.domain.dtos.products.ProductDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

import static jsonexercise.domain.dtos.products.ProductDto.toProductsSoldWithCountDto;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private String firstName;

    private String lastName;

    private Integer age;

    private Set<ProductDto> sellingProducts;

    private Set<ProductDto> boughtProducts;

    private Set<UserDto> friends;

    public String getFullName() {
        return getFirstName() + " " + getLastName();
    }



    public UserWithProductsDto toUserWithProductsDto() {
        return new UserWithProductsDto(firstName, lastName, age, toProductsSoldWithCountDto(sellingProducts));
    }


}
