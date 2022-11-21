package jsonexercise.domain.dtos.products;

import jsonexercise.domain.dtos.categories.CategoryDto;
import jsonexercise.domain.dtos.users.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    private String name;

    private BigDecimal price;

    private UserDto buyer;

    private UserDto seller;

    private Set<CategoryDto> categories;

    public ProductInRangeWithNoBuyerDto toProductInRangeWithNoBuyerDto() {
        return new ProductInRangeWithNoBuyerDto(name, price, seller.getFullName());
    }

    public static ProductsSoldWithCountDto toProductsSoldWithCountDto(Set<ProductDto> sellingProducts) {
        return new ProductsSoldWithCountDto(sellingProducts.stream()
                .map(ProductDto::toProductsBasicInfo)
                .collect(Collectors.toList()));
    }

    public static ProductBasicInfoDto toProductsBasicInfo(ProductDto productDto) {
        return new ProductBasicInfoDto(productDto.getName(), productDto.getPrice());
    }
}
