package de.kozdemir.bibliothek.service;

import de.kozdemir.bibliothek.model.Book;
import de.kozdemir.bibliothek.model.Genera;
import de.kozdemir.bibliothek.model.Status;
import de.kozdemir.bibliothek.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    public BookService() {

    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public Optional<Book> findById(Long id) {
        statusControlAndChange(bookRepository.findById(id).get()); // ob Available des Buches geprüft wird
        return bookRepository.findById(id);
    }

    public List<Book> searchByTitle(String title) {
        return bookRepository.findByTitleContainsIgnoreCase(title);
    }

    public List<Book> searchByAuthor(String author) {
        return bookRepository.findByAuthorContainsIgnoreCase(author);
    }

    public List<Book> searchByGenera(Genera genera) {
        return bookRepository.findByGenera(genera);
    }

    public List<Book> searchByTitleAndAuthor(String title, String author) {
        return bookRepository.findByTitleContainsIgnoreCaseAndAuthorContainsIgnoreCase(title, author);
    }

    public Book insert(Book book) {
        return bookRepository.save(book);
    }

    public Book update(Long id, Book book) {
        statusControlAndChange(findById(id).get());
        Optional<Book> opt = bookRepository.findById(id);

        if (opt.isPresent()) {
            Book m = opt.get();
            m.setTitle(book.getTitle());
            m.setDescription(book.getDescription());
            m.setAuthor(book.getAuthor());
            m.setIsbn(book.getIsbn());
            m.setRentDate(book.getRentDate());
            m.setGenera(book.getGenera());
            m.setStatus(book.getStatus());

            return bookRepository.save(m);
        }
        return new Book();
    }

    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    //Status von alle Bücher  werden kontrolliert, wenn alle Bücher ausruft werden
    /*
    public void statusControlAndChange(List<Book> books) {
        for (Book m : books) {
                statusControlAndChange(m);
        }
    }
    */

    public void statusControlAndChange(Book book) {
        if ((book.getStatus() == Status.RENTED) && (book.getRentDate().plusDays(7).isBefore(LocalDateTime.now()))) {
            book.setStatus(Status.DELAYED);
            bookRepository.save(book);
        }
    }


}
