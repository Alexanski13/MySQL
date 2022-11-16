package jsonexercise.services.category;

import domain.dtos.categories.CategoryProductSummaryDto;
import jsonexercise.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

import static jsonexercise.constant.Paths.CATEGORIES_BY_PRODUCTS_JSON_PATH;
import static jsonexercise.constant.Utils.writeJsonIntoFile;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<CategoryProductSummaryDto> getCategorySummary() throws IOException {
        List<CategoryProductSummaryDto> categoryProductSummaryDtoList =
                this.categoryRepository.getCategorySummary().orElseThrow(NoSuchElementException::new);

        writeJsonIntoFile(categoryProductSummaryDtoList, CATEGORIES_BY_PRODUCTS_JSON_PATH);

        return categoryProductSummaryDtoList;
    }
}
