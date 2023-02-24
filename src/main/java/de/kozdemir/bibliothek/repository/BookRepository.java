package de.kozdemir.bibliothek.repository;

import de.kozdemir.bibliothek.model.Book;
import de.kozdemir.bibliothek.model.Genera;
import de.kozdemir.bibliothek.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByTitleContainsIgnoreCase(String title);

    List<Book> findByAuthorContainsIgnoreCase(String author);

    List<Book> findByGenera(Genera genera); // Enum

    List<Book> findByStatus(Status status); // Enum

    List<Book> findByTitleContainsIgnoreCaseAndAuthorContainsIgnoreCase(String title, String author);

    List<Book> findByStatusAndRentDateBefore(Status status, LocalDateTime rentDate);



}
