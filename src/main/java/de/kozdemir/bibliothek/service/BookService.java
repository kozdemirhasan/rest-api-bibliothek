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
        statusControlAndChangeBook(bookRepository.findAll());
        return bookRepository.findAll();
    }

    public Optional<Book> findById(Long id) {
//        statusControlAndChangeBook(bookRepository.findById(id).get());
        return bookRepository.findById(id);
    }

    public List<Book> searchByTitle(String title) {
//        statusControlAndChangeBook(bookRepository.searchTitle(title).get());
        return bookRepository.findByTitleContains(title);
    }

    public List<Book> searchByAuthor(String author) {
//        statusControlAndChangeBook(bookRepository.searchAuthor(author).get());
        return bookRepository.findByAuthorContains(author);
    }

    public List<Book> searchByGenera(Genera genera) {
        return bookRepository.findByGenera(genera);
    }

    public List<Book> searchByTitleAndAuthor(String title, String author) {
        return bookRepository.findByTitleContainsAndAuthorContains(title, author);
    }



    public Book insert(Book book) {
        return bookRepository.save(book);
    }

    public Book update(Long id, Book book) {
        statusControlAndChangeBook(findById(id).get());
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

    public void statusControlAndChangeBook(List<Book> books) {
        for (Book m : books) {
            statusControlAndChangeBook(m);
        }
    }

    public void statusControlAndChangeBook(Book book) {
        if ((book.getStatus() == Status.RENTED) && (book.getRentDate().plusDays(7).isBefore(LocalDateTime.now()))) {
            book.setStatus(Status.DELAYED);
            bookRepository.save(book);
        }
    }


}
