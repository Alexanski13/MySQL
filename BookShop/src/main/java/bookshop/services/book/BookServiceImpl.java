package bookshop.services.book;

import bookshop.domain.dto.BookInformation;
import bookshop.domain.entities.Book;
import bookshop.domain.enums.AgeRestriction;
import bookshop.domain.enums.EditionType;
import bookshop.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void seedBooks(List<Book> books) {
        this.bookRepository.saveAll(books);
    }

    @Override
    public List<Book> findAllByReleaseDateAfter(LocalDate date) {
        return this.bookRepository.findAllByReleaseDateAfter(date).orElseThrow(NoSuchElementException::new);

    }

    @Override
    public List<Book> findAllByAuthorFirstNameAndAuthorLastNameOrderByReleaseDateDescTitleAsc(String firstName, String lastName) {
        return this.bookRepository.findAllByAuthorFirstNameAndAuthorLastNameOrderByReleaseDateDescTitleAsc(firstName, lastName)
                .orElseThrow(NoSuchElementException::new);
    }

    @Override
    public List<Book> findAllByAgeRestriction(AgeRestriction ageRestriction) {
        return this.bookRepository.findAllByAgeRestriction(ageRestriction).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public List<Book> findAllByEditionTypeAndCopiesLessThan(EditionType type, Integer copiesCount) {
        return this.bookRepository.findAllByEditionTypeAndCopiesLessThan(type, copiesCount).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public List<Book> findAllByPriceLessThanOrPriceGreaterThan(BigDecimal low, BigDecimal high) {
        return this.bookRepository.findAllByPriceLessThanOrPriceGreaterThan(low, high).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public List<Book> findAllByReleaseDateStartingWith(String date) {
        return this.bookRepository.findAllByReleaseDateStartingWith(date).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public List<Book> findAllByReleaseDateBefore(LocalDate date) {
        return this.bookRepository.findAllByReleaseDateBefore(date).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public List<Book> findAllByTitleContaining(String contains) {
        return this.bookRepository.findAllByTitleContaining(contains).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public List<Book> findAllByAuthorLastNameStartingWith(String suffix) {
        return this.bookRepository.findAllByAuthorLastNameStartingWith(suffix).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public Integer numberOfBooksTitleLongerThan(Integer length) {
        return this.bookRepository.numberOfBooksTitleLongerThan(length).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public BookInformation findFirstByTitle(String title) {
        BookInformation bookInformation = this.bookRepository.findFirstByTitle(title).orElseThrow(NoSuchElementException::new);

        System.out.println(bookInformation.toString());
        return bookInformation;
    }

    @Override
    public int increaseBookCopies(LocalDate date, int copies) {
        final int increase = this.bookRepository.increaseBookCopies(date, copies);
        System.out.println(increase * copies);
        return increase;
    }

    @Override
    public int deleteAllByCopiesLessThan(Integer copies) {
        return this.bookRepository.deleteAllByCopiesLessThan(copies);
    }
}
