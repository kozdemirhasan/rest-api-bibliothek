package de.kozdemir.bibliothek.controller;

import de.kozdemir.bibliothek.model.Book;
import de.kozdemir.bibliothek.model.Genera;
import de.kozdemir.bibliothek.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    @GetMapping("st")
    public List<Book> searchByTitle(@RequestParam("title") String title) {
        return bookService.searchByTitle(title);
    }

    @GetMapping("sa")
    public List<Book> searchByAuthor(@RequestParam("author") String author) {
        return bookService.searchByAuthor(author);
    }

    @GetMapping("sg")
    public List<Book> searchByGenera(@RequestParam("genera") Genera genera) {
        return bookService.searchByGenera(genera);
    }

    //mit title oder Author search
    @GetMapping("search")
    public List<Book> searchByTitleAuthor(@RequestParam("title") String title, @RequestParam("author") String author) {
        return bookService.searchByTitleAndAuthor(title, author);
    }


    @PostMapping("") // Speichern (INSERT)
    public Book insert(@RequestBody Book book) {
        return bookService.insert(book);
    }

    @PutMapping("{id}") // Speichern (UPDATE)
    public Book update(@PathVariable("id") Long id, @RequestBody Book book) {
        return bookService.update(id, book);
    }

    @DeleteMapping("{id}") // Löschen
    public void delete(@PathVariable("id") Long id) {
        bookService.deleteById(id);
    }


}
