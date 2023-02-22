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

    /*
    @GetMapping("search/{book}")
    public List<Book> searchBook(@RequestParam("book") Book book) {
        System.out.println(book.getTitle());
        System.out.println(book.getAuthor());
        System.out.println(book.getGenera());

        return bookService.searchBook(book);
    }
    */

/*
    @GetMapping("search/{title}")
    public List<Book> searchByTitle(@PathVariable String title) {
        return bookService.searchByTitle(title);
    }

    @GetMapping("search/{author}")
    public List<Book> searchByAuthor(@PathVariable("author") String author) {
        return bookService.searchByAuthor(author);
    }

    @GetMapping("search/{genera}")
    public List<Book> searchByGenera(@PathVariable("genera") Genera genera) {
        return bookService.searchByGenera(genera);
    }
*/

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
    public Book rent(@PathVariable("id") Long id, Model model) throws RuntimeException {
        Book b = bookService.findById(id).get();
        if (b.equals(null)) {
            model.addAttribute("error", "Book is not find");
        } else if (!b.getStatus().equals(Status.AVAILABLE)) {
            model.addAttribute("available", false);
            throw new RuntimeException("Book is not lending");
        } else {
            b.setStatus(Status.RENTED);
            b.setRentDate(LocalDateTime.now());
            return bookService.update(id, b);
        }

        throw new RuntimeException("Book nicht gefunden.");
    }

    //zurückzugeben
    @PutMapping("giveback/{id}")
    public Book giveBack(@PathVariable Long id, Model model) {
        Book b = null;
        try {
            b = bookService.findById(id).get();
        } catch (NoSuchElementException e) {
            System.out.println("Fehler...");
            model.addAttribute("error", true);
        }

        if (b.getStatus().equals(Status.RENTED) || b.getStatus().equals(Status.DELAYED)) {
            b.setStatus(Status.AVAILABLE);
            b.setRentDate(null);
            return bookService.update(id, b);
        }

        throw new RuntimeException("Book nicht gefunden.");
    }

}
