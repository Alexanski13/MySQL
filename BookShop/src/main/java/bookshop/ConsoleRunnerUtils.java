package bookshop;

import bookshop.domain.entities.Author;
import bookshop.domain.entities.Book;
import bookshop.domain.enums.AgeRestriction;
import bookshop.domain.enums.EditionType;
import bookshop.services.author.AuthorService;
import bookshop.services.book.BookService;
import bookshop.services.seed.SeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Component
public class ConsoleRunnerUtils {

    private final LocalDate BOOK_YEAR_AFTER = LocalDate.of(2000, 1, 1);
    private final LocalDate BOOK_YEAR_BEFORE = LocalDate.of(1990, 1, 1);

    private final SeedService seedService;
    private final BookService bookService;
    private final AuthorService authorService;

    @Autowired
    public ConsoleRunnerUtils(SeedService seedService, BookService bookService, AuthorService authorService) {
        this.seedService = seedService;
        this.bookService = bookService;
        this.authorService = authorService;
    }

    private void getAllBooksAfterAGivenYear() {
        this.bookService.findAllByReleaseDateAfter(BOOK_YEAR_AFTER)
                .forEach(book -> System.out.println(book.getTitle()));
    }

    private void getAllAuthorsWithBooksReleaseDateBefore() {
        this.authorService.findDistinctByBooksReleaseDateBefore(BOOK_YEAR_BEFORE)
                .forEach(author -> System.out.println(author.getFirstName() + " " + author.getLastName()));
    }

    private void getAllOrderByBooks() {
        this.authorService.findAllOrderByBooks()
                .forEach(author -> System.out.println(author.toStringWithCount()));
    }

    private void findAllByAuthorFirstNameAndAuthorLastNameOrderByReleaseDateDescTitleAsc() {
        this.bookService
                .findAllByAuthorFirstNameAndAuthorLastNameOrderByReleaseDateDescTitleAsc("George", "Powell")
                .forEach(book -> System.out.println(book.getTitle() + " "
                        + book.getReleaseDate() + " "
                        + book.getCopies()));
    }

    public void bookTitlesByAgeRestriction(String ageRestrictionType) {
        final AgeRestriction ageRestriction = AgeRestriction.valueOf(ageRestrictionType.toUpperCase());

        final List<Book> allByAgeRestriction = this.bookService.findAllByAgeRestriction(ageRestriction);

        allByAgeRestriction.stream().map(Book::getTitle).forEach(System.out::println);
    }

    public void goldenBookWithLessThan5000Copies() {
        this.bookService.findAllByEditionTypeAndCopiesLessThan(EditionType.GOLD, 5000)
                .stream().map(Book::getTitle).forEach(System.out::println);
    }

    public void findByPriceLessOrGreaterThan() {
        this.bookService.findAllByPriceLessThanOrPriceGreaterThan(BigDecimal.valueOf(5L), BigDecimal.valueOf(40L))
                .stream().map(Book::getBookAndTitleFormat).forEach(System.out::println);
    }

    public void findByReleaseDateNot() {
        this.bookService.findAllByReleaseDateStartingWith("2000")
                .stream().map(Book::getTitle)
                .forEach(System.out::println);
    }

    public void findByDateBefore() {
        this.bookService.findAllByReleaseDateBefore(LocalDate.of(1992, 4, 12))
                .stream().map(Book::getBookTitleEditionTypeAndPriceFormat).forEach(System.out::println);
    }

    public void authorsSearch(String arg) {
        this.authorService.findAllByFirstNameEndingWith(arg)
                .stream().map(Author::getFullName)
                .forEach(System.out::println);
    }

    public void bookSearchByStringContaining(String contains) {
        this.bookService.findAllByTitleContaining(contains)
                .stream().map(Book::getTitle)
                .forEach(System.out::println);
    }

    public void findByLastNameSuffix (String suffix) {
        this.bookService.findAllByAuthorLastNameStartingWith(suffix)
                .stream().map(Book::getBookTitleAndAuthorFullNameFormat)
                .forEach(System.out::println);
    }

    public void numberOfBookTitlesLongerThan(Integer length) {
        System.out.println(this.bookService.numberOfBooksTitleLongerThan(length));
    }

    public void findByTitle (String contains) {
        this.bookService.findFirstByTitle(contains);
    }
}
