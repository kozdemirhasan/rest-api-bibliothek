package de.kozdemir.bibliothek.service;

import de.kozdemir.bibliothek.model.Book;
import de.kozdemir.bibliothek.model.Status;
import de.kozdemir.bibliothek.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class StatusService {

    @Autowired
    private BookRepository bookRepository;

    //@Scheduled(cron = "@hourly") // @yearly, @daily, @midnight, @hourly
    @Scheduled(cron = "@midnight") // jede Mitternacht kontrolliert
    public void changeStatus() {
        List<Book> books = bookRepository.findByStatusAndRentDateBefore(Status.RENTED, LocalDateTime.now().minusDays(7));
        for (Book b : books)
            b.setStatus(Status.DELAYED);

        bookRepository.saveAll(books);
    }
}
