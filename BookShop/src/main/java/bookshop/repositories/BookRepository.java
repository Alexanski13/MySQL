package bookshop.repositories;

import bookshop.domain.dto.BookInformation;
import bookshop.domain.entities.Book;
import bookshop.domain.enums.AgeRestriction;
import bookshop.domain.enums.EditionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<List<Book>> findAllByReleaseDateAfter(LocalDate localDate);

    Optional<List<Book>> findAllByAuthorFirstNameAndAuthorLastNameOrderByReleaseDateDescTitleAsc(String firstName, String lastName);

    Optional<List<Book>> findAllByAgeRestriction(AgeRestriction ageRestriction);

    Optional<List<Book>> findAllByEditionTypeAndCopiesLessThan(EditionType type, Integer copiesCount);

    Optional<List<Book>> findAllByPriceLessThanOrPriceGreaterThan(BigDecimal low, BigDecimal high);

    Optional<List<Book>> findAllByReleaseDateStartingWith(String date);

    Optional<List<Book>> findAllByReleaseDateBefore(LocalDate date);

    Optional<List<Book>> findAllByTitleContaining(String contains);

    Optional<List<Book>> findAllByAuthorLastNameStartingWith(String prefix);

    @Query("select count(b.id) from Book as b where length(b.title) > :length")
    Optional<Integer> numberOfBooksTitleLongerThan(Integer length);

    Optional<BookInformation> findFirstByTitle(String title);

    @Modifying
    @Transactional
    @Query("update Book b set b.copies = b.copies + :copies where b.releaseDate > :date")
    int increaseBookCopies (LocalDate date, int copies);

    @Transactional
    int deleteAllByCopiesLessThan(Integer copies);
}
