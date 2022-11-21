package jsonexercise.services.category;

import jsonexercise.domain.dtos.categories.CategoryProductSummaryDto;
import jsonexercise.domain.dtos.categories.wrappers.CategoriesProductSummaryWrapperDto;
import jsonexercise.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

import static jsonexercise.constant.Paths.CATEGORIES_BY_PRODUCTS_JSON_PATH;
import static jsonexercise.constant.Paths.CATEGORIES_BY_PRODUCTS_XML_PATH;
import static jsonexercise.constant.Utils.writeJsonIntoFile;
import static jsonexercise.constant.Utils.writeXmlIntoFile;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<CategoryProductSummaryDto> getCategorySummary() throws IOException, JAXBException {
        List<CategoryProductSummaryDto> categories =
                this.categoryRepository.getCategorySummary().orElseThrow(NoSuchElementException::new);

        final CategoriesProductSummaryWrapperDto categoriesWrapper =
                new CategoriesProductSummaryWrapperDto(categories);

        writeJsonIntoFile(categories, CATEGORIES_BY_PRODUCTS_JSON_PATH);
        writeXmlIntoFile(categoriesWrapper, CATEGORIES_BY_PRODUCTS_XML_PATH);

        return categories;
    }
}
