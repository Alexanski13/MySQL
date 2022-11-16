package jsonexercise.services.product;

import domain.dtos.products.ProductDto;
import domain.dtos.products.ProductInRangeWithNoBuyerDto;
import jsonexercise.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static jsonexercise.constant.Paths.PRODUCTS_WITH_NO_BUYERS_IN_RANGE_JSON_PATH;
import static jsonexercise.constant.Utils.MODEL_MAPPER;
import static jsonexercise.constant.Utils.writeJsonIntoFile;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    @Transactional
    public List<ProductInRangeWithNoBuyerDto> findAllByPriceBetweenAndBuyerIsNullOrderByPrice(BigDecimal low, BigDecimal high) throws IOException {
        final List<ProductInRangeWithNoBuyerDto> products = this.productRepository.findAllByPriceBetweenAndBuyerIsNullOrderByPrice(low, high)
                .orElseThrow(NoSuchElementException::new)
                .stream().map(product -> MODEL_MAPPER.map(product, ProductDto.class))
                .map(ProductDto::toProductInRangeWithNoBuyerDto)
                .collect(Collectors.toList());

        writeJsonIntoFile(products, PRODUCTS_WITH_NO_BUYERS_IN_RANGE_JSON_PATH);

        return products;
    }
}
