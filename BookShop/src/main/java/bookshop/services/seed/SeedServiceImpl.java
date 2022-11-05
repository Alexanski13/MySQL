package bookshop.services.seed;

import bookshop.domain.entities.Author;
import bookshop.domain.entities.Book;
import bookshop.domain.entities.Category;
import bookshop.domain.enums.AgeRestriction;
import bookshop.domain.enums.EditionType;
import bookshop.services.author.AuthorService;
import bookshop.services.book.BookService;
import bookshop.services.category.CategoryService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static bookshop.Constants.FilePaths.*;

@Service
public class SeedServiceImpl implements SeedService {

    private final BookService bookService;
    private final AuthorService authorService;
    private final CategoryService categoryService;

    public SeedServiceImpl(BookService bookService, AuthorService authorService, CategoryService categoryService) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.categoryService = categoryService;
    }


    @Override
    public void seedAuthors() throws IOException {
        if (!this.authorService.isDataSeeded()) {
            this.authorService.seedAuthors(Files.readAllLines(Path.of(RESOURCE_URL + AUTHOR_FILE_NAME))
                    .stream()
                    .filter(s -> !s.isBlank())
                    .map(s -> Author.builder()
                            .firstName(s.split(" ")[0])
                            .lastName(s.split(" ")[1])
                            .build())
                    .collect(Collectors.toList()));
        }
    }

    @Override
    public void seedBooks() throws IOException {
        final List<Book> books = Files.readAllLines(Path.of(RESOURCE_URL + BOOK_FILE_NAME))
                .stream()
                .filter(s -> !s.isBlank())
                .map(row -> {
                    String[] data = row.split("\\s+");
                    String title = Arrays.stream(data)
                            .skip(5)
                            .collect(Collectors.joining(" "));

                    return Book.builder()
                            .title(title)
                            .editionType(EditionType.values()[Integer.parseInt(data[0])])
                            .price(new BigDecimal(data[3]))
                            .releaseDate(LocalDate.parse(data[1], DateTimeFormatter.ofPattern("d/M/yyyy")))
                            .ageRestriction(AgeRestriction.values()[Integer.parseInt(data[4])])
                            .author(this.authorService.getRandomAuthor())
                            .categories(this.categoryService.getRandomCategories())
                            .copies(Integer.parseInt(data[2]))
                            .build();

                })
                .collect(Collectors.toList());

        this.bookService.seedBooks(books);
    }

    @Override
    public void seedCategory() throws IOException {
        if (!categoryService.isDataSeeded()) {
            this.categoryService.seedCategories(Files.readAllLines(Path.of(RESOURCE_URL + CATEGORY_FILE_NAME))
                    .stream()
                    .filter(s -> !s.isBlank())
                    .map(name -> Category.builder().name(name).build())
                    .collect(Collectors.toList()));
        }
    }
}
