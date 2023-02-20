package de.kozdemir.bibliothek;

import de.kozdemir.bibliothek.model.Book;
import de.kozdemir.bibliothek.model.Status;
import de.kozdemir.bibliothek.model.Genera;
import de.kozdemir.bibliothek.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class BibliothekApplication implements CommandLineRunner {

    @Autowired
    BookRepository bookRepository;

    @Value("${db.admin.user.reset}")
    private boolean bookReset;

    public static void main(String[] args) {
        SpringApplication.run(BibliothekApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        if (bookReset) {
            bookRepository.deleteAll();
            List<Book> bookList = new ArrayList<>();

            Book book = new Book();
            book.setTitle("Harry Potter");
            book.setDescription("Fantastisch....");
            book.setAuthor("J.K. Rowling");
            book.setIsbn("3456-2567-7643");
            book.setRentDate(LocalDateTime.of(2023, 02, 10, 11, 35));
            book.setGenera(Genera.ROMANE);
            book.setStatus(Status.RENTED);
            bookList.add(book);

            book = new Book();
            book.setTitle("Gladyator");
            book.setDescription("bala bla bla...... ");
            book.setAuthor("Tom Cruse");
            book.setIsbn("8997-4545-5555");
            book.setRentDate(null);
            book.setGenera(Genera.ENZYKLOPÄDIE);
            book.setStatus(Status.AVAILABLE);
            bookList.add(book);
            bookRepository.saveAll(bookList); // Speichert den Inhalt der Liste
        }
    }
}
