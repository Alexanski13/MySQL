package bookshop.services.author;

import bookshop.domain.entities.Author;
import bookshop.repositories.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public void seedAuthors(List<Author> authors) {
        this.authorRepository.saveAll(authors);
    }

    @Override
    public boolean isDataSeeded() {
        return this.authorRepository.count() > 0;
    }

    @Override
    public Author getRandomAuthor() {
        final long count = this.authorRepository.count();
        if (count != 0) {
            final long randomAuthorId = new Random().nextLong(1L, count);
            return this.authorRepository.findAuthorById(randomAuthorId).orElseThrow(NoSuchElementException::new);
        }

        throw new RuntimeException();
    }

    @Override
    public List<Author> findDistinctByBooksReleaseDateBefore(LocalDate localDate) {
        return this.authorRepository.findDistinctByBooksReleaseDateBefore(localDate).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public List<Author> findAllOrderByBooks() {
        return this.authorRepository.findAllDistinctOrderByBooks().orElseThrow(NoSuchElementException::new);
    }
}
