package domain.dtos.users;

import domain.dtos.products.ProductSoldDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsersWithSoldProductsDto {

    private String firstName;

    private String lastName;

    private List<ProductSoldDto> boughtProducts;
}
