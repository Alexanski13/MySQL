package jsonexercise.services.category;

import domain.dtos.categories.CategoryProductSummaryDto;

import java.io.IOException;
import java.util.List;

public interface CategoryService {

    List<CategoryProductSummaryDto> getCategorySummary() throws IOException;

}
