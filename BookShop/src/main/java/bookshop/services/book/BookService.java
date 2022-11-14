package bookshop.services.book;

import bookshop.domain.dto.BookInformation;
import bookshop.domain.entities.Book;
import bookshop.domain.enums.AgeRestriction;
import bookshop.domain.enums.EditionType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BookService {

    void seedBooks(List<Book> books);

    List<Book> findAllByReleaseDateAfter(LocalDate localDate);

    List<Book> findAllByAuthorFirstNameAndAuthorLastNameOrderByReleaseDateDescTitleAsc(String firstName, String lastName);

    List<Book> findAllByAgeRestriction(AgeRestriction ageRestriction);

    List<Book> findAllByEditionTypeAndCopiesLessThan(EditionType type, Integer copiesCount);

    List<Book> findAllByPriceLessThanOrPriceGreaterThan(BigDecimal low, BigDecimal high);

    List<Book> findAllByReleaseDateStartingWith(String date);

    List<Book> findAllByReleaseDateBefore(LocalDate date);

    List<Book> findAllByTitleContaining(String contains);

    List<Book> findAllByAuthorLastNameStartingWith(String prefix);

    Integer numberOfBooksTitleLongerThan(Integer length);

    BookInformation findFirstByTitle(String title);

    int increaseBookCopies (LocalDate date, int copies);

    int deleteAllByCopiesLessThan(Integer copies);
}
