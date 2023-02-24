package de.kozdemir.bibliothek.controller;

import de.kozdemir.bibliothek.model.Book;
import de.kozdemir.bibliothek.model.Genera;
import de.kozdemir.bibliothek.model.Status;
import de.kozdemir.bibliothek.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("")
    public List<Book> index() {
        return bookService.findAll();
    }

    @GetMapping("{id}")
    public Optional<Book> byId(@PathVariable("id") Long id) {
        return bookService.findById(id);
    }

    @GetMapping("find/title/{title}")
    public List<Book> searchByTitle(@PathVariable("title") String title) {
        return bookService.searchByTitle(title);
    }

    @GetMapping("find/author/{author}")
    public List<Book> searchByAuthor(@PathVariable("author") String author) {
        return bookService.searchByAuthor(author);
    }

    @GetMapping("find/genera/{genera}")
    public List<Book> searchByGenera(@PathVariable("genera") Genera genera) {
        return bookService.searchByGenera(genera);
    }

    @GetMapping("find/status/{status}")
    public List<Book> searchByStatus(@PathVariable("status") Status status) {
        return bookService.searchByStatus(status);
    }

    //mit Title und Author search (@RequestParam)
    @GetMapping("search")
    public List<Book> searchByTitleAuthor(@RequestParam("title") String title, @RequestParam("author") String author) {
        return bookService.searchByTitleAndAuthor(title, author);
    }

    @PostMapping("add") // Speichern (INSERT)
    public Book insert(@Valid @RequestBody Book book, BindingResult result, Model model) {
        return bookService.insert(book);
    }

    @PutMapping("{id}") // Speichern (UPDATE)
    public Book update(@Valid @RequestBody Book book, @PathVariable("id") Long id) {
        return bookService.update(id, book);
    }

    @DeleteMapping("{id}") // Löschen
    public void delete(@PathVariable("id") Long id) {
        bookService.deleteById(id);
    }

    //leihen
    @PutMapping("rent/{id}")
    public Book rent(@PathVariable("id") Long id, Model model) {
        try {
            Book b = bookService.findById(id).get();

            if (!b.getStatus().equals(Status.AVAILABLE)) {
                model.addAttribute("available", false);
                throw new RuntimeException("Buch ist nicht verfügbar");
            } else {
                b.setStatus(Status.RENTED);
                b.setRentDate(LocalDateTime.now());
                return bookService.update(id, b);
            }

        } catch (NoSuchElementException e) {
            throw new RuntimeException("Buch nicht gefunden.");
        }
    }

    //zurückzugeben
    @PutMapping("giveback/{id}")
    public Book giveBack(@Valid @PathVariable Long id, Model model) {
        Book b;
        try {
            b = bookService.findById(id).get();
        } catch (NoSuchElementException e) {
            model.addAttribute("error", true);
            throw new RuntimeException("Buch nicht gefunden.");
        }

        if (b.getStatus().equals(Status.RENTED) || b.getStatus().equals(Status.DELAYED)) {
            b.setStatus(Status.AVAILABLE);
            b.setRentDate(null);
            return bookService.update(id, b);
        }

        throw new RuntimeException("ungültige Abfrage");
    }

}
