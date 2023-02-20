package de.kozdemir.bibliothek.repository;

import de.kozdemir.bibliothek.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("SELECT b FROM Book b WHERE lower(b.title) LIKE %?1%")
    List<Book> searchTitle(String title);

    @Query("SELECT b FROM Book b WHERE lower(b.genera) LIKE %?1%")
    Optional<Book> searchGenera(String genera);

    @Query("SELECT b FROM Book b WHERE lower(b.author) LIKE %?1%")
    Optional<Book> searchAuthor(String author);

}
