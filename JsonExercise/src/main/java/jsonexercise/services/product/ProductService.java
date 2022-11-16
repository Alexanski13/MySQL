package jsonexercise.services.product;

import domain.dtos.products.ProductInRangeWithNoBuyerDto;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public interface ProductService {
    List<ProductInRangeWithNoBuyerDto> findAllByPriceBetweenAndBuyerIsNullOrderByPrice(BigDecimal low, BigDecimal high) throws IOException;
}
