package bookshop;

import bookshop.domain.entities.Author;
import bookshop.domain.entities.Book;
import bookshop.domain.enums.AgeRestriction;
import bookshop.domain.enums.EditionType;
import bookshop.services.author.AuthorService;
import bookshop.services.book.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

@Component
public class ConsoleRunner implements CommandLineRunner {

    private final BookService bookService;
    private final AuthorService authorService;

    private final Scanner scanner;

    @Autowired
    public ConsoleRunner(BookService bookService, AuthorService authorService) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void run(String... args) throws IOException {
//        final String year = scanner.nextLine().replaceAll(" ", "-");
//        final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");

        final int copies = scanner.nextInt();

//        final Integer length = scanner.nextInt();

//        this.bookService.increaseBookCopies(LocalDate.parse(year, dateTimeFormatter), copies);

        System.out.println(this.bookService.deleteAllByCopiesLessThan(copies));

    }


}


